package userinput;

import frame.CaptureOverlay;
import frame.OCRFrame;
import ocr.Capturer;
import ocr.Translator;
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
    private final Translator translator;
    private final TrayIcon trayIcon;
    private final OCRFrame frame;

    public CaptureController(OCRFrame frame) {
        this.frame = frame;

        if(!SystemTray.isSupported()) {
            System.err.println("System tray is not supported!");
            System.exit(2);
        }

        Image icon = Toolkit.getDefaultToolkit().getImage("path/to/tray-icon.png");
        trayIcon = new TrayIcon(icon);
        trayIcon.setImageAutoSize(true);
        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Failed to add tray icon: " + e.getMessage());
            System.exit(3);
        }

        try {
            capturer = new Capturer();
        } catch (IOException e) {
            System.err.println("Failed to create a temporary file for captures");
            throw new RuntimeException(e);
        }

        translator = new Translator("jpn+eng");

        new Thread(() -> {
            while(true) {
                overlay.repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
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
        File file = null;
        try {
            file = capturer.capture(mouseListener.getRect());
        } catch (AWTException ex) {
            System.err.println("Failed to get a screen capture");
        } catch (IOException ex) {
            System.err.println("Failed to save a screen capture file");
        }

        if(file != null) {
            String text = translator.translate(file);
            System.out.println("Translated text: " + text);
            trayIcon.displayMessage("Text:", text, TrayIcon.MessageType.INFO);
            frame.setVisible(false);
        }
    }

    @Override
    public void handleMouseDragEvent(MouseDragEvent e) {
        overlay.updateSelection(mouseListener.getRect());
        //overlay.repaint();
    }
}
