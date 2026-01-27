package ocr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import translation.Language;
import translation.Translator;

import javax.swing.*;

public class TesseractOCR implements OCR {
    private final Tesseract tesseract;
    private final HashMap<Language, String> langMap = new HashMap<>();
    private final List<Language> availableLanguages = new ArrayList<>();

    public TesseractOCR(Language[] languages) {
        tesseract = new Tesseract();
        tesseract.setDatapath("ocrdata/tessdata");
        tesseract.setVariable("user_defined_dpi", "300");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO);

        Translator.scanMap(new File("ocrdata/tessdata/LanguageMap.txt"), langMap);
        scanAvailableLanguages();
        setLanguages(languages);
    }

    private void scanAvailableLanguages() {
        availableLanguages.clear();
        File dir = new File("ocrdata/tessdata/");
        File[] files = dir.listFiles();
        for(File file : files) {
            String name = file.getName();
            if(name.endsWith(".traineddata")) {
                String lang = name.split("\\.")[0];
                for(Map.Entry<Language, String> entry : langMap.entrySet()) {
                    if(entry.getValue().equals(lang)) {
                        availableLanguages.add(entry.getKey());
                    }
                }
            }
        }
    }

    public List<Language> getAvailableLanguages() {
        return availableLanguages;
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
            JOptionPane.showMessageDialog(null, "Tesseract failed to perform OCR:\n" + e.getMessage());
        }
        return text;
    }
}
