import ocr.AvailableOCR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import translation.DeepLTranslator;
import translation.Language;
import translation.TranslationProcesser;

import java.io.File;

public class TranslatorTests {
    private final DeepLTranslator deeplTrans = new DeepLTranslator("*YOUR API KEY HERE"); // *YOUR API KEY HERE*
    private final DeepLTranslator libreTrans = new DeepLTranslator("*YOUR API KEY HERE"); // *YOUR API KEY HERE*

    @Test
    public void testTranslationDeepL() {
        String text = "Hello, World!";
        String expected = "Bonjour à tous !";

        String output = deeplTrans.translate(text, Language.English, Language.French);

        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testTranslationLibre() {
        String text = "Hello, World!";
        String expected = "Bonjour, Monde !";

        String output = libreTrans.translate(text, Language.English, Language.French);

        Assertions.assertEquals(expected, output);
    }
}
