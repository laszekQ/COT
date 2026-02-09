import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import translation.Language;
import translation.TranslationCache;

public class CacheTests {
    @Test
    public void testCacheEngFr() {
        String expected = "Bonjour, à tous !";
        TranslationCache.cache("Hello, World!", "Bonjour, à tous !", Language.English, Language.French);
        String output = TranslationCache.getFromCache("Hello, World!", Language.English, Language.French);
        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testGetCacheDirtyEngFr() {
        String output = TranslationCache.getFromCache("   Hello  ,    World  !   ", Language.English, Language.French);
        String expected = "Bonjour, à tous !";
        Assertions.assertEquals(expected, output);
    }
}
