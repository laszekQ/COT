package frame;

import javax.swing.*;
import java.awt.*;

public class OCRFrame extends JFrame {
    private final CaptureOverlay overlay;

    public OCRFrame() {
        super("");
        setUndecorated(true);
        setBackground(new Color(255, 255, 255, 0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        overlay = new CaptureOverlay();
        add(overlay);

        setVisible(false);
    }

    public CaptureOverlay getOverlay() {
        return overlay;
    }
}
