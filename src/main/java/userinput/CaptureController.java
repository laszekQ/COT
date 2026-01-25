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
import userinput.event.SelectionEvent;

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

    public CaptureController(OCRFrame frame) {
        this.frame = frame;

        if(!SystemTray.isSupported()) {
            System.err.println("System tray is not supported!");
            System.exit(2);
        }

        try {
            capturer = new Capturer();
        } catch (IOException e) {
            System.err.println("Failed to create a temporary file for captures");
            throw new RuntimeException(e);
        }

        translationProcesser = new TranslationProcesser(
                new Language[]{Language.Japanese, Language.English},
                               Language.English);
        translationProcesser.setOCR(AvailableOCR.Tesseract);
        translationProcesser.setTranslator(AvailableTranslators.DeepL);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/icon.png");
        trayIcon = new AppTrayIcon(icon, translationProcesser);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("COT");
        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Failed to add tray icon: " + e.getMessage());
            System.exit(3);
        }
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
            } catch (IOException ex) {
                System.err.println("Failed to save a screen capture file");
            }

            if (file != null) {
                System.out.println("Extracted text: " + translationProcesser.read(file));

                String text = translationProcesser.translate(file);
                System.out.println("Translated text: " + text);
                trayIcon.displayMessage("Translation:", text, TrayIcon.MessageType.INFO);
            }
        }).start();
        frame.setVisible(false);
    }

    @Override
    public void handleMouseDragEvent(MouseDragEvent e) {
        overlay.updateSelection(mouseListener.getRect());
        overlay.repaint();
    }
}
