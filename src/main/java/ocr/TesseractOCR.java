package ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;

public class TesseractOCR implements OCR {
    private final Tesseract tesseract;

    public TesseractOCR(String lang) {
        tesseract = new Tesseract();
        tesseract.setDatapath("tessdata");
        tesseract.setLanguage(lang);
        tesseract.setVariable("user_defined_dpi", "300");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_LINE);
    }

    @Override
    public void setLanguage(String lang) {
        tesseract.setLanguage(lang);
    }

    @Override
    public String read(File img) {
        String text = null;

        try {
            text = tesseract.doOCR(img);
        } catch (TesseractException e) {
            System.err.println("Tesseract failed to perform OCR:");
            e.printStackTrace();
        }
        return text;
    }
}
