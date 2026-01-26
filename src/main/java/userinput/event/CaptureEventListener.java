package userinput.event;

import java.util.EventListener;

public interface CaptureEventListener extends EventListener {
    void handleSelectionEvent(SelectionEvent e);
    void handleMouseDragEvent(MouseDragEvent e);
    void handleResetEvent(ResetEvent e);
}
