package ocr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Capturer {
    BufferedImage capture;

    public Capturer() {
        capture = null;
    }

    public void capture(Rectangle rect) throws AWTException {
        capture = new Robot().createScreenCapture(rect);
    }

    public BufferedImage getCapture() {
        return capture;
    }

    /*
        File imageFile = new File("single-screen.bmp");
        ImageIO.write(capture, "bmp", imageFile );
        assertTrue(imageFile .exists());
    * */
}
