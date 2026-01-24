package translation;

import com.deepl.api.*;

import java.util.HashMap;

public class DeepLTranslator extends Translator {

    // https://developers.deepl.com/docs/getting-started/supported-languages
    private final HashMap<Language, String> langMap = new HashMap<>() {{
        put(Language.English, "en-US");
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

        TextResult result = null;
        try {
            result = client.translateText(input, sourceLang, targetLang);
        } catch (DeepLException e) {
            System.err.println("Failed to perform translation");
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
