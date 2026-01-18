package ocr;

import java.awt.image.BufferedImage;
import java.io.File;

public interface OCR {
    public abstract void setLanguage(String lang);
    public abstract String read(File img);
}
