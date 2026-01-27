package userinput;

import frame.AppTrayIcon;
import frame.CaptureOverlay;
import frame.OCRFrame;
import ocr.AvailableOCR;
import ocr.Capturer;
import translation.AvailableTranslators;
import translation.Language;
import translation.TranslationProcesser;
import userinput.event.CaptureEventListener;
import userinput.event.MouseDragEvent;
import userinput.event.ResetEvent;
import userinput.event.SelectionEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CaptureController implements CaptureEventListener {
    private CaptureListener mouseListener;
    private CaptureOverlay overlay;
    private final Capturer capturer;
    private final TranslationProcesser translationProcesser;
    private final AppTrayIcon trayIcon;
    private final OCRFrame frame;
    private int mode = 0; // 0 - notifications, 1 - textarea

    public CaptureController(OCRFrame frame) {
        this.frame = frame;

        if(!SystemTray.isSupported()) {
            System.err.println("System tray is not supported!");
            JOptionPane.showMessageDialog(null, "System tray is not supported!");
            System.exit(2);
        }

        try {
            capturer = new Capturer();
        } catch (IOException e) {
            System.err.println("Failed to create a temporary file for captures");
            JOptionPane.showMessageDialog(null, "Failed to create a temporary file for captures");
            throw new RuntimeException(e);
        }

        translationProcesser = new TranslationProcesser(
                new Language[]{Language.English},
                               Language.English);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/icon.png");
        trayIcon = new AppTrayIcon(icon, translationProcesser, this);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("COT");
        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Failed to add tray icon: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Failed to add a tray icon:\n" + e.getMessage());
            System.exit(3);
        }

        translationProcesser.setIcon(trayIcon);
        translationProcesser.setOCR(AvailableOCR.Tesseract);
        translationProcesser.setTranslator(AvailableTranslators.DeepL);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setCaptureListener(CaptureListener listener) {
        mouseListener = listener;
        mouseListener.setController(this);
    }

    public void setOverlay(CaptureOverlay overlay) {
        this.overlay = overlay;
    }

    @Override
    public void handleSelectionEvent(SelectionEvent e) {

        new Thread( () -> {
            File file = null;
            try {
                file = capturer.capture(mouseListener.getRect());
            } catch (AWTException ex) {
                System.err.println("Failed to get a screen capture");
                JOptionPane.showMessageDialog(null, "Failed to get a screen capture");
            } catch (IOException ex) {
                System.err.println("Failed to save a screen capture file");
                JOptionPane.showMessageDialog(null, "Failed to save a screen capture");
            }

            if (file != null) {

                String inputText = translationProcesser.read(file);
                System.out.println("Extracted text: " + inputText);

                String outputText = translationProcesser.translate(file);
                System.out.println("Translated text: " + outputText);

                if(mode == 0)
                    trayIcon.displayMessage(inputText + ":", outputText, TrayIcon.MessageType.INFO);
                else if(mode == 1) {
                    overlay.setText(outputText);
                    overlay.repaint();
                }
            }
        }).start();
        if(mode == 0) {
            frame.setVisible(false);
            overlay.updateSelection(null);
        }
    }

    @Override
    public void handleMouseDragEvent(MouseDragEvent e) {
        overlay.updateSelection(mouseListener.getRect());
        overlay.repaint();
    }

    @Override
    public void handleResetEvent(ResetEvent e) {
        overlay.setText("");
        overlay.repaint();
    }
}
