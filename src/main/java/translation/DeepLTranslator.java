package translation;

import com.deepl.api.*;

import java.util.HashMap;

public class DeepLTranslator extends Translator {

    // https://developers.deepl.com/docs/getting-started/supported-languages
    private final HashMap<Language, String> langMap = new HashMap<>() {{
        put(Language.Belarusian, "be");
        put(Language.English, "en");
        put(Language.French, "fr");
        put(Language.German, "de");
        put(Language.Italian, "it");
        put(Language.Russian, "ru");
        put(Language.Japanese, "ja");
    }};

    public DeepLTranslator(String key) {
        super(key);
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
            System.out.println(sourceLang + " <- source language ");
            result = client.translateText(input, sourceLang, targetLang);
        } catch (DeepLException e) {
            System.err.println("Failed to perform translation");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Translation thread was interrupted:");
            e.printStackTrace();
        }

        if (result != null) {
            return result.getText();
        }
        return "Unknown translation failure";
    }
}
