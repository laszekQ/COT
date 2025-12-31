package userinput;

import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import java.awt.event.MouseAdapter;

public class Capture implements Runnable {
    private static Capture instance;

    private Capture() {

    }

    public static Capture getCapture() {
        if(instance == null)
            return new Capture();
        return instance;
    }

    @Override
    public void run() {

    }

    void beginCapture() {

    }
}
