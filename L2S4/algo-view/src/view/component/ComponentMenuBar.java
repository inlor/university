package view.component;

import java.util.HashMap;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import java.util.List;
import java.util.ArrayList;

import util.*;
import model.Model;
import resources.Resources;
import view.component.notification.Choice;
import view.component.notification.Action;
import controller.Controller;
import configuration.Configuration;

public class ComponentMenuBar extends Group {

    private Model model;
    /** Contrôlleur principale */
    private Controller controller;
    /** Les items du menubar */
    private HashMap<String, MenuItem> menuItem;

    public ComponentMenuBar(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;

        this.menuItem = new HashMap<>();

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(ScreenHelper.getWidth());

        Menu file = new Menu(Resources.get("file"));
        createItems(file, "open", "save", "exit");

        Menu edit = new Menu(Resources.get("edit"));

        createItems(edit, "undo", "step");

        Menu opt = new Menu(Resources.get("opt"));
        createItems(opt, "best");

        Menu help = new Menu(Resources.get("help"));
        createItems(help, "langage");

        menuBar.getMenus().addAll(file, edit, opt, help);

        this.getChildren().addAll(menuBar);

        updateMenu();
    }
    /**
     * Création des itemes
     * @param menu le menu parent
     * @param names les noms des items
     */
    private void createItems(Menu menu, String... names) {
        for (String name : names) {
            MenuItem item = new MenuItem(Resources.get(name));
            item.setGraphic(new ImageView(Configuration.getIconPath(name)));
            String shortcut = Resources.getShortcut(name);
            if (!shortcut.equals("")) {
                KeyCodeCombination comb = new KeyCodeCombination(KeyCode.getKeyCode(shortcut),
                        KeyCombination.CONTROL_DOWN);
                item.setAccelerator(comb);
            }
            menu.getItems().add(item);
            menuItem.put(name, item);
            item.setOnAction(e -> {
                controller.controllerMenuBar(name);
            });
        }
    }

    /**
     * Changement de la langue de l'application.
     */
    public void changeLanguage(){

        List<String> choices = new ArrayList<>();
        for (String lang : Resources.getLangs()) {
            choices.add(Resources.get(lang));
        }
        Choice languageChange = new Choice("dialog_lang", "langage_changement", Resources.get(Resources.lang), choices);
        languageChange.setAction(new Action(){
            @Override
            public void execute() {
                for (String key : Resources.getLangs()) {
                    String value = Resources.get(key);
                    if (value.equals(languageChange.getResult().get())){
                        languageChange.setResponse(key);
                        break;
                    }
                }
            }
        });
        if(Resources.changeLanguage(languageChange.getResponse())){
            try {
                Runtime.getRuntime().exec("java Main");
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mise à jour du menu
     */
    public void updateMenu(){
        boolean forUndo = model.tri.arrayBloc.indexOf(model.getCurrent())==0;
        menuItem.get("undo").setDisable(forUndo);
        boolean forStepStart = model.tri.arrayBloc.indexOf(model.getCurrent())==model.tri.arrayBloc.size()-1;
        menuItem.get("step").setDisable(forStepStart);
        menuItem.get("save").setDisable(model.tri.hashMap==null);
    }
}