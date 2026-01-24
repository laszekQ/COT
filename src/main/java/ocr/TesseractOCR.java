package ocr;

import java.io.File;
import java.util.HashMap;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import translation.Language;

public class TesseractOCR implements OCR {
    private final Tesseract tesseract;

    private final HashMap<Language, String> langMap = new HashMap<>() {{
       put(Language.English, "eng");
       put(Language.Japanese, "jpn");
    }};

    public TesseractOCR(Language[] languages) {
        tesseract = new Tesseract();
        tesseract.setDatapath("tessdata");
        setLanguages(languages);
        tesseract.setVariable("user_defined_dpi", "300");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_LINE);
    }

    @Override
    public void setLanguages(Language[] languages) {
        StringBuilder sb = new StringBuilder();
        for(Language s : languages) {
            sb.append(langMap.get(s));
            sb.append("+");
        }
        sb.deleteCharAt(sb.length() - 1);
        tesseract.setLanguage(sb.toString());
    }

    @Override
    public String read(File img) {
        String text = null;

        try {
            text = tesseract.doOCR(img).trim();
        } catch (TesseractException e) {
            System.err.println("Tesseract failed to perform OCR:");
            e.printStackTrace();
        }
        return text;
    }
}
