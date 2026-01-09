import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import frame.OCRFrame;
import userinput.KeyboardListener;
import userinput.Shortcut;

public class Main {
    public static void main(String[] args) {
        KeyboardListener keyListener = new KeyboardListener();
        keyListener.addShortcut(new Shortcut(true, true, false, NativeKeyEvent.VC_1), () -> {System.out.println("Shortcut"); System.exit(0);});


        OCRFrame frame = new OCRFrame();
        frame.setVisible(true);
    }
}
