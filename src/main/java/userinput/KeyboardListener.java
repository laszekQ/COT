package userinput;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;

public class KeyboardListener implements NativeKeyListener {

    HashMap<Shortcut, Runnable> shortcutMap;

    public KeyboardListener() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener());
    }

    public void addShortcut(Shortcut sequence, Runnable runnable) {
        shortcutMap.put(sequence, runnable);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        for (Shortcut shcut : shortcutMap.keySet()) {
            if ((e.getModifiers() & shcut.getMask()) != 0 &&
                    e.getKeyCode() == shcut.getKey()) {
                Runnable toRun = shortcutMap.get(shcut);
                new Thread(toRun).start();
            }
        }
    }
}

