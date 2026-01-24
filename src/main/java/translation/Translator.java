package translation;

import java.util.HashMap;

public abstract class Translator {
    protected final String apiKey;

    public Translator(String key) {
        apiKey = key;
    }

    public abstract String translate(String input, Language langFrom, Language langTo);
}
