package userinput;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;

public class Shortcut {
    boolean altPressed;
    boolean shiftPressed;
    boolean ctrlPressed;
    int key;

    public Shortcut(boolean ctrlPressed, boolean shiftPressed, boolean altPressed, int key) {
        this.ctrlPressed = ctrlPressed;
        this.shiftPressed = shiftPressed;
        this.altPressed = altPressed;
        this.key = key;
    }

    public int getMask() {
        int mask = 0;
        mask += ctrlPressed ? KeyEvent.CTRL_DOWN_MASK : 0;
        mask += shiftPressed ? KeyEvent.SHIFT_DOWN_MASK : 0;
        mask += altPressed ? KeyEvent.ALT_DOWN_MASK : 0;
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
