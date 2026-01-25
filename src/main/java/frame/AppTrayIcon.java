package frame;

import userinput.CaptureController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppTrayIcon extends TrayIcon {
    private PopupMenu menu;
    private CaptureController controller;

    public AppTrayIcon(Image image) {
        super(image);
        initMenu();
        setPopupMenu(menu);
    }

    public AppTrayIcon(Image image, CaptureController controller) {
        super(image);
        this.controller = controller;
        initMenu();
        setPopupMenu(menu);
    }

    private void initMenu() {
        menu = new PopupMenu();
        addMenuItem("Exit", actionEvent -> System.exit(0));
    }

    private void addMenuItem(String label, ActionListener listener) {
        MenuItem item = new MenuItem(label);
        item.addActionListener(listener);
        menu.add(item);
    }
}
