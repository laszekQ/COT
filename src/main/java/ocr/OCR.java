package ocr;

import translation.Language;

import java.io.File;

public interface OCR {
    void setLanguages(Language[] languages);
    String read(File img);
}
