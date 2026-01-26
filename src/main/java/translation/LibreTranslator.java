package translation;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LibreTranslator implements Translator{
    private final HashMap<Language, String> langMap = new HashMap<>();
    private final String apiKey;

    public LibreTranslator(String apiKey) {
        this.apiKey = apiKey;
        Translator.scanMap(new File("api/Libre/LanguageMap.txt"), langMap);
    }

    @Override // https://docs.libretranslate.com/guides/api_usage/
    public String translate(String input, Language langFrom, Language langTo) {

        String output = "";
        try {
            URL url = new URI("https://libretranslate.com/translate").toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{"
                    + "\"q\": " + input
                    + "\"source\": " + langMap.get(langFrom)
                    + "\"target\": " + langMap.get(langTo)
                    + "\"api_key\": " + apiKey
                    + "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] bytes = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(bytes, 0, bytes.length);
            }

            int code = con.getResponseCode();
            if(code != 200) {
                System.err.println("HTTP Error: " + con.getResponseMessage());
                return "Unknown Translation failure";
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                output = response.substring(1, 2);
                con.disconnect();
                return output;
            }
        } catch (URISyntaxException e) {
            System.err.println("Malformed URL for libre translator");
        } catch (IOException e) {
            System.err.println("IO Exception. Possibly caused by a connection problem");
        }

        return "Unknown translation failure";
    }
}
