import frame.OCRFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OCRFrame frame = new OCRFrame();
        });
    }
}
