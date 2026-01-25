package translation;

import ocr.AvailableOCR;
import ocr.OCR;
import ocr.TesseractOCR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TranslationProcesser {
    private OCR ocr;
    private Language[] langSource;
    private Language langTarget;
    private Translator translator;
    private HashMap<AvailableTranslators, String> apiKeys = new HashMap<>();

    public TranslationProcesser() {
        scanAPIKeys();
    }

    public TranslationProcesser(Language[] languagesSource, Language languageTarget) {
        scanAPIKeys();
        langSource = languagesSource;
        langTarget = languageTarget;
    }

    private void scanAPIKeys() {
        String path = "api_keys";
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if(file.canRead()) {
                    try (Scanner scanner = new Scanner(file)) {
                        while (scanner.hasNextLine()) {
                            AvailableTranslators translator = AvailableTranslators.valueOf(file.getName());
                            String key = scanner.nextLine();
                            apiKeys.put(translator, key);
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("File not found:");
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            System.err.println("No API keys found!");
        }
    }

    public void setOCR(AvailableOCR ocr) {
        switch (ocr) {
            case AvailableOCR.Tesseract -> this.ocr = new TesseractOCR(langSource);
        }
    }

    public void setTranslator(AvailableTranslators translator) {
        switch (translator) {
            case AvailableTranslators.DeepL -> this.translator = new DeepLTranslator(apiKeys.get(translator));
        }
    }

    public void setLanguagesSource(Language[] languagesSource) {
        langSource = languagesSource;
    }

    public void setLanguageTarget(Language languageTarget) {
        langTarget = languageTarget;
    }

    public String read(File img) {
        String inputText = ocr.read(img);
        System.out.println("Extracted text: " + inputText);
        return inputText;
    }

    public String translate(File img) {
        String inputText = ocr.read(img);
        return translator.translate(inputText, langSource[0], langTarget);
    }
}
