import ocr.TesseractOCR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import translation.Language;

import java.io.File;

public class OCRTests {
    private final TesseractOCR ocrTessEng = new TesseractOCR(new Language[]{Language.English});
    private final TesseractOCR ocrTessJpn = new TesseractOCR(new Language[]{Language.Japanese});

    @Test
    public void testReadingTesseractClearDarkEng() {
        String expected = "Hello, World!";
        String output = ocrTessEng.read(new File("assets/tests/dark_eng.png"));
        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testReadingTesseractClearLightEng() {
        String expected = "Hello, World!";
        String output = ocrTessEng.read(new File("assets/tests/light_eng.png"));
        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testReadingTesseractClearDarkJap() {
        String expected = "世界、こんにちは";
        String output = ocrTessJpn.read(new File("assets/tests/dark_jpn.png"));
        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testReadingTesseractClearLightJap() {
        String expected = "世界、こんにちは";
        String output = ocrTessJpn.read(new File("assets/tests/light_jpn.png"));
        Assertions.assertEquals(expected, output);
    }

    /*
    @Test
    public void testReadingTesseractGameSmallJap() {
        OCR ocr = new TesseractOCR(new Language[]{Language.Japanese});
        String expected = "装備";
        String output = ocr.read(new File("assets/tests/jpn_game_small.png"));
        System.out.println(output);
        assert(output.equals(expected));
    }

    @Test
    public void testReadingTesseractGameMediumJap() {
        OCR ocr = new TesseractOCR(new Language[]{Language.Japanese});
        String expected = "機れた火防女の魂を渡す";
        String output = ocr.read(new File("assets/tests/jpn_game_medium.png"));
        assert(output.equals(expected));
    }
     */
}
