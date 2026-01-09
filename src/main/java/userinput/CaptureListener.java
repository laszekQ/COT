package userinput;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CaptureListener implements MouseListener {

    private Point firstPoint, secondPoint;
    private boolean gotRect;

    public CaptureListener() {
        gotRect = false;
    }

    public Rectangle getRect() {
        if(gotRect) {
            int width = firstPoint.x - secondPoint.x;
            int height = firstPoint.y - secondPoint.y;
            gotRect = false;
            return new Rectangle(firstPoint.x, firstPoint.y, width, height);
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        firstPoint = mouseEvent.getLocationOnScreen();
        gotRect = false;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        secondPoint = mouseEvent.getLocationOnScreen();
        gotRect = true;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
