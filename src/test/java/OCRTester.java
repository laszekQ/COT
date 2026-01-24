import ocr.OCR;
import ocr.TesseractOCR;
import org.junit.jupiter.api.Test;
import translation.Language;

import java.io.File;

public class OCRTester {
    @Test
    public void testReadingTesseractClearDarkEng() {
        OCR ocr = new TesseractOCR(new Language[]{Language.English});
        String expected = "Hello, World!";
        String output = ocr.read(new File("assets/tests/dark_eng.png"));
        assert(output.equals(expected));
    }

    @Test
    public void testReadingTesseractClearLightEng() {
        OCR ocr = new TesseractOCR(new Language[]{Language.English});
        String expected = "Hello, World!";
        String output = ocr.read(new File("assets/tests/light_eng.png"));
        assert(output.equals(expected));
    }

    @Test
    public void testReadingTesseractClearDarkJap() {
        OCR ocr = new TesseractOCR(new Language[]{Language.Japanese});
        String expected = "世界、こんにちは";
        String output = ocr.read(new File("assets/tests/dark_jpn.png"));
        assert(output.equals(expected));
    }

    @Test
    public void testReadingTesseractClearLightJap() {
        OCR ocr = new TesseractOCR(new Language[]{Language.Japanese});
        String expected = "世界、こんにちは";
        String output = ocr.read(new File("assets/tests/light_jpn.png"));
        assert(output.equals(expected));
    }

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
}
