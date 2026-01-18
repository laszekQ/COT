package userinput;

import frame.CaptureOverlay;
import ocr.Capturer;
import ocr.OCR;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CaptureListener extends MouseAdapter {

    private Point firstPoint, secondPoint;
    private boolean gotRect;

    private Capturer capturer;
    private CaptureOverlay overlay;
    private OCR ocr;

    public CaptureListener() {
        gotRect = false;
    }

    public void setCapturer(Capturer capturer) {
        this.capturer = capturer;
    }

    public void setOcr(OCR ocr) {
        this.ocr = ocr;
    }

    public void setOverlay(CaptureOverlay overlay) {
        this.overlay = overlay;
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
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        secondPoint = mouseEvent.getLocationOnScreen();
        gotRect = true;

        System.out.println(firstPoint);
        System.out.println(secondPoint);
        try {
            capturer.capture(getRect());
        } catch (AWTException e) {
            System.err.println("Failed to get a screen capture");
        } catch (IOException e) {
            System.err.println("Failed to save the capture file");
        }

        ocr.setLanguage("eng");
        ocr.read(capturer.getFile());
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(!gotRect) {
            secondPoint = mouseEvent.getLocationOnScreen();
            overlay.updateSelection(getRect());
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
