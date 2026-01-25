package userinput;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.util.HashMap;

public class KeyboardListener implements NativeKeyListener {
    private final HashMap<Shortcut, Runnable> shortcutMap;

    public KeyboardListener() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        shortcutMap = new HashMap<>();
        GlobalScreen.addNativeKeyListener(this);
    }

    public void addShortcut(Shortcut sequence, Runnable runnable) {
        shortcutMap.put(sequence, runnable);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        for (Shortcut shcut : shortcutMap.keySet()) {
            if ((e.getModifiers() & Shortcut.modifiersMask ^ shcut.getMask()) == 0 &&
                    e.getKeyCode() == shcut.getKey()) {
                Runnable toRun = shortcutMap.get(shcut);
                if(!shcut.pressed) {
                    shcut.pressed = true;
                    new Thread(toRun).start();
                }
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        for (Shortcut shcut : shortcutMap.keySet()) {
            if ((e.getModifiers() & Shortcut.modifiersMask ^ shcut.getMask()) == 0  &&
                    e.getKeyCode() == shcut.getKey()) {
                shcut.pressed = false;
            }
        }
    }
}

