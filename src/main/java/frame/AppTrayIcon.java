package frame;

import translation.Language;
import translation.TranslationProcesser;

import java.awt.*;
import java.awt.event.ActionListener;

public class AppTrayIcon extends TrayIcon {
    private PopupMenu menu;
    private TranslationProcesser processer;

    public AppTrayIcon(Image image) {
        super(image);
    }

    public AppTrayIcon(Image image, TranslationProcesser processer) {
        super(image);
        this.processer = processer;
        initMenu();
        setPopupMenu(menu);
    }

    private void initMenu() {
        menu = new PopupMenu();

        addPopupMenu("Input Language");
        addPopupMenu("Output Language");

        PopupMenu inputLangMenu = (PopupMenu) menu.getItem(0);
        for(Language language : Language.values()) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {processer.setLanguagesSource(new Language[]{language}); System.out.println(language.name());});
            inputLangMenu.add(languageItem);
        }
        PopupMenu outputLangMenu = (PopupMenu) menu.getItem(1);
        for(Language language : Language.values()) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {processer.setLanguageTarget(language);  System.out.println(language.name());});
            outputLangMenu.add(languageItem);
        }

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
