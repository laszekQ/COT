package userinput;

import userinput.event.MouseDragEvent;
import userinput.event.ResetEvent;
import userinput.event.SelectionEvent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CaptureListener extends MouseAdapter {
    private Point firstPoint, secondPoint;
    private boolean gotRect;

    private CaptureController controller;

    public CaptureListener() {
        gotRect = false;
    }

    public void setController(CaptureController controller) {
        this.controller = controller;
    }

    public Rectangle getRect() {
        int width = Math.abs(firstPoint.x - secondPoint.x);
        int height = Math.abs(firstPoint.y - secondPoint.y);
        return new Rectangle(firstPoint.x, firstPoint.y, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        firstPoint = mouseEvent.getLocationOnScreen();
        gotRect = false;

        controller.handleResetEvent(new ResetEvent(this));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        secondPoint = mouseEvent.getLocationOnScreen();
        gotRect = true;

        controller.handleSelectionEvent(new SelectionEvent(this));
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(!gotRect) {
            secondPoint = mouseEvent.getLocationOnScreen();
            controller.handleMouseDragEvent(new MouseDragEvent(this));
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
