package frame;

import javax.swing.*;
import java.awt.*;

public class CaptureOverlay extends JPanel {
    private Rectangle selection;
    private String translatedText;
    private final JTextArea textArea;
    private final int FONT_SIZE = 12;

    public CaptureOverlay() {
        setOpaque(false);
        textArea = new JTextArea(0, 0);
        textArea.setText("");
        add(textArea);
    }

    public void updateSelection(Rectangle selection) {
        this.selection = selection;
    }

    public void setText(String s) {
        translatedText = s;
        if(translatedText.isEmpty())
            textArea.setEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if(selection != null) {
            drawSelection(g2d);
            if(!translatedText.isEmpty()) {
                drawTranslatedText();
            }
        }
    }

    private void drawSelection(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(selection.x, selection.y, selection.width, selection.height);

        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(Color.black);
        g2d.drawRect(selection.x, selection.y, selection.width, selection.height);
    }

    private void drawTranslatedText() {
        textArea.setText(translatedText);

        textArea.setRows(selection.height / FONT_SIZE / 2);
        textArea.setColumns(selection.width / FONT_SIZE);

        int locX = selection.x;
        int locY = selection.y;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if(selection.width > selection.height) { /// "horizontal" selection
            if(locY + selection.height < screenSize.height - selection.height)
                locY += selection.height; // place text below if possible
            else if(locY - selection.height > selection.height)
                locY -= selection.height; // place text above if possible
        }
        else { /// "vertical" selection
            if(locX + selection.width < screenSize.width - selection.width)
                locX += selection.width; // place text to the right if possible
            else if(locX - selection.width > selection.width)
                locX -= selection.width; // place text to the left if possible
        }

        textArea.setLocation(locX, locY);
        textArea.setEnabled(true);
    }
}
