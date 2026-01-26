package userinput;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import translation.AvailableTranslators;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Native;
import java.util.Scanner;

public class Shortcut {
    private boolean altPressed;
    private boolean shiftPressed;
    private boolean ctrlPressed;
    private int key;

    boolean pressed = false;

    public static int modifiersMask =
            NativeKeyEvent.CTRL_MASK | NativeKeyEvent.SHIFT_L_MASK | NativeKeyEvent.ALT_L_MASK;

    public Shortcut(File file) {
        String shortcut = "";
        if(file.canRead()) {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextLine()) {
                    shortcut = scanner.nextLine();
                }
            } catch (FileNotFoundException e) {
                System.err.println("Shortcut file not found");
                JOptionPane.showMessageDialog(null, "Shortcut file not found");
                System.exit(3);
            }
        }
        else {
            System.err.println("Shortcut file is not readable");
            JOptionPane.showMessageDialog(null, "Shortcut file is not readable");
            System.exit(4);
        }

        String[] params = shortcut.split(" ");
        for(String param : params) {
            switch(param.toUpperCase()) {
                case "CTRL":
                    ctrlPressed = true;
                    break;
                case "SHIFT":
                    shiftPressed = true;
                    break;
                case "ALT":
                    altPressed = true;
                    break;
                default:
                    key = Integer.parseInt(param);
                    break;
            }
        }
    }

    public Shortcut(boolean ctrlPressed, boolean shiftPressed, boolean altPressed, int key) {
        this.ctrlPressed = ctrlPressed;
        this.shiftPressed = shiftPressed;
        this.altPressed = altPressed;
        this.key = key;
    }

    public int getMask() {
        int mask = 0;
        mask += ctrlPressed ? NativeKeyEvent.CTRL_L_MASK : 0;
        mask += shiftPressed ? NativeKeyEvent.SHIFT_L_MASK : 0;
        mask += altPressed ? NativeKeyEvent.ALT_L_MASK : 0;
        return mask;
    }

    public int getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        sum += altPressed ? 1 : 0;
        sum += shiftPressed ? 1 : 0;
        sum += ctrlPressed ? 1 : 0;
        sum += key;
        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Shortcut)
            return this.hashCode() == obj.hashCode();
        return false;
    }
}
