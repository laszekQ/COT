package frame;

import translation.AvailableTranslators;
import translation.Language;
import translation.TranslationProcesser;
import userinput.CaptureController;

import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.List;

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

        addPopupMenu("Input Language");
        addPopupMenu("Output Language");

        addPopupMenu("Mode(Notification)");
        PopupMenu modeMenu = (PopupMenu) menu.getItem(2);

        MenuItem notifMode = new MenuItem("Notification");
        notifMode.addActionListener(actionEvent -> {
            controller.setMode(0);
            modeMenu.setLabel("Mode(Notification)");
        });
        modeMenu.add(notifMode);

        MenuItem textBoxMode = new MenuItem("TextBox");
        textBoxMode.addActionListener(actionEvent -> {
            controller.setMode(1);
            modeMenu.setLabel("Mode(TextBox)");
        });
        modeMenu.add(textBoxMode);

        addPopupMenu("Translator(DeepL)");
        PopupMenu translatorMenu = (PopupMenu) menu.getItem(3);

        MenuItem deeplTrans = new MenuItem("DeepL");
        deeplTrans.addActionListener(actionEvent -> {
            processer.setTranslator(AvailableTranslators.DeepL);
            deeplTrans.setLabel("Mode(DeepL)");
        });
        translatorMenu.add(deeplTrans);

        MenuItem libreTrans = new MenuItem("Libre (online)");
        libreTrans.addActionListener(actionEvent -> {
            processer.setTranslator(AvailableTranslators.Libre);
            libreTrans.setLabel("Mode(Libre (online))");
        });
        translatorMenu.add(libreTrans);

        addMenuItem("Exit", actionEvent -> System.exit(0));
    }

    public void updateInputLanguages(List<Language> langList) {
        PopupMenu inputLangMenu = (PopupMenu) menu.getItem(0);
        for(Language language : langList) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {
                processer.setLanguagesSource(new Language[]{language});
                inputLangMenu.setLabel("Input Language(" + language + ")");
            });
            inputLangMenu.add(languageItem);
        }
    }

    public void updateOutputLanguages(List<Language> langList) {
        PopupMenu inputLangMenu = (PopupMenu) menu.getItem(1);
        for(Language language : langList) {
            MenuItem languageItem = new MenuItem(language.name());
            languageItem.addActionListener(actionEvent -> {
                processer.setLanguageTarget(language);
                inputLangMenu.setLabel("Output Language(" + language + ")");
            });
            inputLangMenu.add(languageItem);
        }
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
