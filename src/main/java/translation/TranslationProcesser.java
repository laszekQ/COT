package translation;

import frame.AppTrayIcon;
import ocr.AvailableOCR;
import ocr.OCR;
import ocr.TesseractOCR;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TranslationProcesser {
    private OCR ocr;
    private Language[] langSource;
    private Language langTarget;
    private Translator translator;
    private AvailableTranslators translatorEnum;
    private final HashMap<AvailableTranslators, String> apiKeys = new HashMap<>();
    private AppTrayIcon trayIcon;

    public TranslationProcesser(Language[] languagesSource, Language languageTarget) {
        scanAPIKeys();
        langSource = languagesSource;
        langTarget = languageTarget;
    }

    public void setIcon(AppTrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    private void scanAPIKeys() {
        String path = "api";
        File dir = new File(path);
        File[] apiDirs = dir.listFiles();

        if (apiDirs != null) {
            for (File subDir : apiDirs) { // for each folder in api/
                File[] apiFiles = subDir.listFiles();
                if(apiFiles != null) {
                    for (File file : apiFiles) { // for each file in api/*name*/
                        if (file.canRead() && file.getName().equalsIgnoreCase("key.txt")) {
                            try (Scanner scanner = new Scanner(file)) {
                                if (scanner.hasNextLine()) {
                                    AvailableTranslators translator = AvailableTranslators.valueOf(subDir.getName());
                                    String key = scanner.nextLine();
                                    apiKeys.put(translator, key);
                                }
                            } catch (FileNotFoundException e) {
                                System.err.println("File not found:" + e.getMessage());
                                JOptionPane.showMessageDialog(null, "API key file not found for " + subDir.getName());
                            }
                        }
                    }
                }
            }
        }
        else {
            System.err.println("API keys files not found!");
            JOptionPane.showMessageDialog(null, "API keys files not found!");
        }
    }

    public void setOCR(AvailableOCR ocr) {
        switch (ocr) {
            case AvailableOCR.Tesseract -> this.ocr = new TesseractOCR(langSource);
        }
        trayIcon.updateInputLanguages(this.ocr.getAvailableLanguages());
    }

    public void setTranslator(AvailableTranslators translator) {
        String apiKey = apiKeys.get(translator);
        translatorEnum = translator;
        if(apiKey.isEmpty() || apiKey.equals("*YOUR API KEY HERE*")) {
            JOptionPane.showMessageDialog(null,
                    "You haven't provided your API key for " + translator +"!\n" +
                    "Translation will not be possible!");
        }

        switch (translator) {
            case AvailableTranslators.DeepL -> this.translator = new DeepLTranslator(apiKey);
            case AvailableTranslators.Libre -> this.translator = new LibreTranslator(apiKey);
        }
        trayIcon.updateOutputLanguages(this.translator.getSupportedLanguages());
    }

    public AvailableTranslators getTranslator() {
        return translatorEnum;
    }

    public void setLanguagesSource(Language[] languagesSource) {
        langSource = languagesSource;
        ocr.setLanguages(langSource);
    }
    public Language[] getLanguagesSource() {
        return langSource;
    }

    public void setLanguageTarget(Language languageTarget) {
        langTarget = languageTarget;
    }
    public Language getLanguageTarget() {
        return langTarget;
    }

    public String read(File img) {
        return ocr.read(img);
    }

    public String translate(File img) {
        String inputText = ocr.read(img);
        if(inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "OCR failed to read the text, please try again.");
        }

        String cachedTranslation = TranslationCache.getFromCache(inputText, langSource[0], langTarget);
        if(cachedTranslation != null) {
            System.out.println("Cache victua!!!");
            return cachedTranslation;
        }
        else {
            String translation = translator.translate(inputText, langSource[0], langTarget);
            new Thread(() -> {
                TranslationCache.cache(inputText, translation, langSource[0], langTarget);
            }).start();
            System.out.println("Not in cache...");
            return translation;
        }
    }
}
