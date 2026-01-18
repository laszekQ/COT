package userinput;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class Shortcut {
    private final boolean altPressed;
    private final boolean shiftPressed;
    private final boolean ctrlPressed;
    private final int key;

    boolean pressed = false;

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
