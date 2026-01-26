package ocr;

import translation.Language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public interface OCR {
    void setLanguages(Language[] languages);
    String read(File img);
}
