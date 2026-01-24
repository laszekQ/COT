package ocr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Capturer {
    private final File imgFile;

    public Capturer() throws IOException {
        imgFile = File.createTempFile("COTCapture", ".png");
    }

    public File capture(Rectangle rect) throws AWTException, IOException {
        BufferedImage capture = new Robot().createScreenCapture(rect);
        ImageIO.write(capture, "png", imgFile);
        assert(imgFile.exists());
        return imgFile;
    }
}
