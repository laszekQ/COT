package frame;

import translation.TranslationCache;
import userinput.CaptureController;
import userinput.CaptureListener;
import userinput.KeyboardListener;
import userinput.Shortcut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class OCRFrame extends JFrame {
    public OCRFrame() {
        super("");
        setUndecorated(true);
        setBackground(new Color(255, 255, 255, 0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        CaptureOverlay overlay = new CaptureOverlay();
        add(overlay);

        CaptureListener mouseListener = new CaptureListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        KeyboardListener keyListener = new KeyboardListener();
        keyListener.addShortcut(new Shortcut(new File("assets/shortcut.txt")),
                () -> {
                    setVisible(!isVisible());
                    overlay.setText("");
                });

        CaptureController controller = new CaptureController(this);
        controller.setCaptureListener(mouseListener);
        controller.setOverlay(overlay);


        setVisible(false);

        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }
}
