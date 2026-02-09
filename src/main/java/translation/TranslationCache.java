package translation;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import userinput.CaptureController;

public class TranslationCache {

    public static String getFromCache(String text, Language langSource, Language langTarget) {
        String output = null;

        File file = new File("cache/" + langSource.toString() + "-" + langTarget.toString());
        if (file.exists() && file.canRead()) {
            StringBuilder cachedText = new StringBuilder();
            StringBuilder cachedTranslation = new StringBuilder();
            boolean finishedText = false;
            boolean finishedTranslation = false;

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String segment = scanner.nextLine();
                    switch (segment) {
                        case "Text:":
                            finishedText = false;
                            finishedTranslation = false;
                            cachedText = new StringBuilder();
                            cachedTranslation = new StringBuilder();
                            break;
                        case "Translation:":
                            finishedText = areAlike(text, cachedText.toString());
                            finishedTranslation = false;
                            break;
                        case "EOT":
                            finishedTranslation = true;

                            if(finishedText && finishedTranslation) {
                                output = cachedTranslation.toString().trim();
                                return output;
                            }

                            break;
                        default:
                            if(!finishedText) {
                                cachedText.append(segment).append("\n");
                            }
                            else if(!finishedTranslation) {
                                cachedTranslation.append(segment).append("\n");
                            }
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found:" + e.getMessage());
            }
        }
        else {
            return null;
        }

        return output;
    }

    public static void cache(String text, String translation, Language langSource, Language langTarget) {
        File file = new File("cache/" + langSource.toString() + "-" + langTarget.toString());
        if (file.exists()) {
            addRecord(file, text, translation);
        }
        else {
            try {
                FileUtils.createParentDirectories(file);
                file.createNewFile();
                addRecord(file, text, translation);
            } catch (IOException e) {
                System.err.println("Cache file could not be created:" + e.getMessage());
            }
        }
    }

    public static boolean setSettingsFromCache(TranslationProcesser processer, CaptureController controller) {

        File file = new File("cache/" + "preferences");
        if(file.exists() && file.canRead()) {
            try (Scanner scanner = new Scanner(file)) {
                String[] args = scanner.nextLine().split(" ");

                processer.setLanguagesSource(new Language[]{Language.valueOf(args[0])});
                processer.setLanguageTarget(Language.valueOf(args[1]));
                controller.setMode(Integer.parseInt(args[2]));
                processer.setTranslator(AvailableTranslators.valueOf(args[3]));
            } catch (FileNotFoundException e) {
                System.err.println("File not found:" + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public static void cacheSettings(Language langSource, Language langTarget, int mode, AvailableTranslators translator) {

        File file = new File("cache/" + "preferences");
        if (!file.exists()) {
            try {
                FileUtils.createParentDirectories(file);
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cache file could not be created:" + e.getMessage());
                return;
            }
        }

        try (FileWriter fw = new FileWriter(file, false)) {
            String sb =
                    langSource.toString() + " " +
                    langTarget.toString() + " " +
                    mode + " " +
                    translator.toString() + "\n";
            fw.write(sb);
        } catch (IOException e) {
            System.err.println("Couldn't write to the preferences cache file:" + e.getMessage());
        }

    }

    public static void clearCache() {
        File file = new File("cache");
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Couldn't clear cache:" + e.getMessage());
            }
        }
    }

    private static void addRecord(File file, String text, String translation) {
        if (file.canWrite()) {
            try (FileWriter fw = new FileWriter(file, true)) {
                StringBuilder newCache = new StringBuilder();
                newCache.append("Text:\n").append(text).append("\n").
                        append("Translation:\n").append(translation).append("\n").
                        append("EOT\n");
                fw.write(newCache.toString());
            } catch (IOException e) {
                System.err.println("Couldn't write to the cache file:" + e.getMessage());
            }
        }
    }

    private static boolean areAlike(String text1, String text2) {
        StringBuilder text1Copy = new StringBuilder(text1.trim().toLowerCase().replaceAll("\\p{Punct}", ""));
        StringBuilder text2Copy = new StringBuilder(text2.trim().toLowerCase().replaceAll("\\p{Punct}", ""));

        for(int i = 0; i < text1Copy.length(); i++) {
            if(i != text1Copy.length() - 1 && text1Copy.charAt(i) == ' ' && text1Copy.charAt(i + 1) == ' ') {
                text1Copy.deleteCharAt(i + 1);
                i--;
            }
        }

        for(int i = 0; i < text2Copy.length(); i++) {
            if(i != text2Copy.length() - 1 && text2Copy.charAt(i) == ' ' && text2Copy.charAt(i + 1) == ' ') {
                text2Copy.deleteCharAt(i + 1);
                i--;
            }
        }

        String s1 = text1Copy.toString().trim();
        String s2 = text2Copy.toString().trim();

        return s1.equals(s2);
    }
}
