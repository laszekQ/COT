package frame;

import translation.Language;
import translation.TranslationProcesser;
import userinput.CaptureController;

import java.awt.*;
import java.awt.event.ActionListener;

public class AppTrayIcon extends TrayIcon {
    private PopupMenu menu;
    private final TranslationProcesser processer;
    private final CaptureController controller;

    public AppTrayIcon(Image image, TranslationProcesser processer, CaptureController controller) {
        super(image);
        this.processer = processer;
        this.controller = controller;
        initMenu();
        setPopupMenu(menu);
    }

    private void initMenu() {
        menu = new PopupMenu();

        addPopupMenu("Input Language(English)");
        addPopupMenu("Output Language(English)");

        PopupMenu inputLangMenu = (PopupMenu) menu.getItem(0);
        for(Language language : Language.values()) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {
                processer.setLanguagesSource(new Language[]{language});
                inputLangMenu.setLabel("Input Language(" + language + ")");
            });
            inputLangMenu.add(languageItem);
        }
        PopupMenu outputLangMenu = (PopupMenu) menu.getItem(1);
        for(Language language : Language.values()) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {
                processer.setLanguageTarget(language);
                outputLangMenu.setLabel("Output Language(" + language + ")");
            });
            outputLangMenu.add(languageItem);
        }

        addPopupMenu("Mode(Notification)");
        PopupMenu modeMenu = (PopupMenu) menu.getItem(2);

        MenuItem notifMode = new MenuItem("Notification");
        notifMode.addActionListener(actionEvent -> controller.setMode(0));
        modeMenu.add(notifMode);

        MenuItem textBoxMode = new MenuItem("TextBox");
        textBoxMode.addActionListener(actionEvent -> controller.setMode(1));
        modeMenu.add(textBoxMode);

        addMenuItem("Exit", actionEvent -> System.exit(0));
    }

    private void addMenuItem(String label, ActionListener listener) {
        MenuItem item = new MenuItem(label);
        item.addActionListener(listener);
        menu.add(item);
    }

    private void addPopupMenu(String label) {
        PopupMenu popupMenu = new PopupMenu(label);
        menu.add(popupMenu);
    }
}
