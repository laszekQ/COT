import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import frame.OCRFrame;
import ocr.Capturer;
import ocr.TesseractOCR;
import userinput.CaptureListener;
import userinput.KeyboardListener;
import userinput.Shortcut;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            OCRFrame frame = new OCRFrame();
            KeyboardListener keyListener = new KeyboardListener();
            keyListener.addShortcut(new Shortcut(true, true, false, NativeKeyEvent.VC_1),
                    () -> frame.setVisible(!frame.isVisible()));

            Capturer capturer;
            try {
                capturer = new Capturer();
            } catch (IOException e) {
                System.err.println("Failed to create a temporary file for captures");
                throw new RuntimeException(e);
            }

            CaptureListener mouseListener = new CaptureListener();
            mouseListener.setCapturer(capturer);
            TesseractOCR ocr = new TesseractOCR("jpn+eng");
            mouseListener.setOcr(ocr);
            mouseListener.setOverlay(frame.getOverlay());
            frame.addMouseListener(mouseListener);
            frame.addMouseMotionListener(mouseListener);
        });
    }
}
