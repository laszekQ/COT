package translation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public interface Translator {
    String translate(String input, Language langFrom, Language langTo);

    static void scanMap(File file, HashMap<Language, String> map) {
        System.out.println("Scanning " + file.getName());
        if(file.canRead()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    Language lang = Language.valueOf(line[0]);
                    String value = line[1];

                    System.out.println(line[0] + " " + line[1] + " -> " + lang + " " + value);

                    map.put(lang, value);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Language map file not found");
            }
        }
        else {
            System.err.println("Language map file not readable");
        }
    }
}
