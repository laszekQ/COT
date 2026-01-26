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
    private final HashMap<AvailableTranslators, String> apiKeys = new HashMap<>();

    public TranslationProcesser(Language[] languagesSource, Language languageTarget) {
        scanAPIKeys();
        langSource = languagesSource;
        langTarget = languageTarget;
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
                                System.err.println("File not found:");
                            }
                        }
                    }
                }
            }
        }
        else {
            System.err.println("API keys files not found!");
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
        return ocr.read(img);
    }

    public String translate(File img) {
        String inputText = ocr.read(img);
        return translator.translate(inputText, langSource[0], langTarget);
    }
}
