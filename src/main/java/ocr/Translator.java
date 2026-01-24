package ocr;

import java.io.File;

public class Translator {
    private final OCR ocr;
    private final String lang;

    public Translator(String lang) {
        this.lang = lang;
        ocr = new TesseractOCR(lang);
    }

    public String translate(File img) {
        String inputText = ocr.read(img);
        System.out.println("Extracted text: " + inputText);
        return inputText;
    }
}
