package frame;

import javax.swing.*;
import java.awt.*;

public class OCRFrame extends JFrame {
    Rectangle selectionRect;
    public OCRFrame() {
        super("");
        setUndecorated(true);
        setOpacity(0.2f);
        setSize(getMaximumSize());
    }
}
