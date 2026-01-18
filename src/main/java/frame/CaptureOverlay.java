package frame;

import javax.swing.*;
import java.awt.*;

public class CaptureOverlay extends JPanel {
    private Rectangle selection;

    public CaptureOverlay() {
        setOpaque(false);
    }

    public void updateSelection(Rectangle selection) {
        this.selection = selection;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if(selection != null) {
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(selection.x, selection.y, selection.width, selection.height);

            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.setColor(Color.black);
            g2d.drawRect(selection.x, selection.y, selection.width, selection.height);
        }
    }
}
