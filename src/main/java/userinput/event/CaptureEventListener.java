package userinput.event;

import java.util.EventListener;

public interface CaptureEventListener extends EventListener {
    public abstract void handleSelectionEvent(SelectionEvent e);
    public abstract void handleMouseDragEvent(MouseDragEvent e);
}
