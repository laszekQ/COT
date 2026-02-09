package translation;

import com.deepl.api.*;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DeepLTranslator implements Translator {
    private final HashMap<Language, String> langMap = new HashMap<>();
    private final String apiKey;

    public DeepLTranslator(String key) {
        apiKey = key;
        Translator.scanMap(new File("api/DeepL/LanguageMap.txt"), langMap);
    }

    @Override
    public String translate(String input, Language langFrom, Language langTo) {
        DeepLClient client = new DeepLClient(apiKey);

        String sourceLang = langMap.get(langFrom);
        String targetLang = langMap.get(langTo);
        if(targetLang.equals("en")) {
            targetLang = "en-us";
        }

        TextResult result = null;
        try {
            result = client.translateText(input, sourceLang, targetLang);
        } catch (DeepLException e) {
            System.err.println("Failed to perform translation");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Translation failed:\n" + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Translation thread was interrupted:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Translation failed:\n" + e.getMessage());
        }

        if (result != null) {
            return result.getText();
        }
        return "Unknown translation failure";
    }

    @Override
    public List<Language> getSupportedLanguages() {
        return langMap.keySet().stream().sorted().toList();
    }
}
