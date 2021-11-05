package view.component;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.HashMap;
import javafx.beans.value.*;
import javafx.scene.control.Slider;
import javafx.collections.ObservableList;

import resources.Resources;
import model.*;
import util.*;
import controller.Controller;
import view.component.toolbar.TableKeyValue;
import configuration.Configuration;
/**
 * Toolbar de l'application.
 * Création des buttons et mini composants.
 * @author Lucian Petic
 * @see Model
 * @see TableKeyValue
 * @see Resources
 */
public final class ComponentControls extends Group {

    /** Modèle principale */
    private Model model;
    /** Contôlleur principale */
    private Controller controller;
    /** Toolbar de l'application */
    private ToolBar toolBar;
    /** Information comme: comparaison, accès en memoire, et temps d'exécution */
    private Label label;
    /** Avancement du tri */
    private ProgressIndicator progress;
    /** Animation du tri */
    private Timeline timer;
    /** Les boutons */
    private HashMap<String, Button> buttons;
    /** Tableau repesentatif du tri */
    private TableKeyValue table;

    public ComponentControls(final Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        buttons = new HashMap<>();
        toolBar = new ToolBar();
        toolBar.setPrefWidth(ScreenHelper.getWidth());

        createButtons("undo","step", "start", "pause", "stats");
        createSelection();
        createLabelInfo();

        table = new TableKeyValue(model);
        toolBar.getItems().add(table.getHBox());

        progress = new ProgressIndicator(0);
        toolBar.getItems().add(progress);

        createTimer(Configuration.MIN_DELAY);

        sliderArraySize();
        createSpacing();
        sliderTimerDelay();
    }
    /**
     * Création des boutons.
     * @param names noms des bouttons
     */
    private void createButtons(String... names) {
        toolBar.setLayoutY(25);
        this.getChildren().add(toolBar);
        for (String name : names) {
            createButton(name);
        }
        updateButton();
    }
    /**
     * Création d'un boutton.
     * @param name nom d'un boutton
     */
    private void createButton(String name) {
        Button button = new Button();
        button.setAccessibleHelp(name);
        button.setTooltip(new Tooltip(Resources.get(name)));
        button.setGraphic(new ImageView(Configuration.getIconPath(name)));
        toolBar.getItems().add(button);
        buttons.put(name,button);
        button.setOnAction(e -> {
            controller.controllerButton(button);
        });
    }
    /**
     * Création de la choicebox pour sélectionner le tris qu'il faut utiliser.
     */
    private void createSelection() {
        toolBar.getItems().add(new Separator());
        final ChoiceBox<Object> cb = new ChoiceBox<>(
            FXCollections.observableArrayList(
                Resources.get("sel"),
                Resources.get("ins"), 
                Resources.get("bul"), 
                Resources.get("tim"), 
                Resources.get("mer"),
                Resources.get("heap"),
                Resources.get("qui")
            )
        );
        toolBar.getItems().add(cb);
        cb.setTooltip(new Tooltip(Resources.get("info_sort")));
        cb.getSelectionModel().selectFirst();
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue,
                    Number newValue) {
                controller.controllerChoiceSort((int)newValue);
            }
        });
    }

    /**
     * Choicebox pour le choix de l'espacement entre les blocs 
     */
    private void createSpacing() {
        toolBar.getItems().add(new Separator());
        ObservableList<Integer> options = FXCollections.observableArrayList();
        for (int i = 0; i < 5; i++) {
            options.add(i+1);
        }
        final ChoiceBox<Integer> cb = new ChoiceBox<>(options);
        toolBar.getItems().add(cb);
        cb.setTooltip(new Tooltip(Resources.get("info_sort")));
        cb.getSelectionModel().selectFirst();
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue,
                    Number newValue) {
                    controller.choiceSpacing(newValue.intValue());
            }
        });
        toolBar.getItems().add(new Separator());
    }

    /**
     * Création du slider pour le choix de la taille du tableau
     */
    private void sliderArraySize(){
        Slider slider = new Slider(Configuration.MIN_ARRAY_SIZE, Configuration.MAX_ARRAY_SIZE, (Configuration.MAX_ARRAY_SIZE+Configuration.MIN_ARRAY_SIZE/2));
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(100);
        slider.setBlockIncrement(50);
        toolBar.getItems().add(slider);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
            controller.controllerSlider(newValue.intValue());
        });
    }

    /**
     * Création du slider pour le choix de la vitesse de l'annimation
     */
    private void sliderTimerDelay(){
        Slider slider = new Slider(Configuration.MIN_DELAY, Configuration.MAX_DELAY, (Configuration.MAX_DELAY+Configuration.MIN_DELAY)/2);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1000);
        slider.setBlockIncrement(500);
        toolBar.getItems().add(slider);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
            controller.controllerTimer(newValue.intValue());
        });
    }
    /**
     * Création du label inforlatif avec le nombre de comparaisons, accès en memoire, et temps d'exécution
     */
    private void createLabelInfo() {
        toolBar.getItems().add(new Separator());
        label = new Label();
        updateLabelInfo();
        label.getStyleClass().add("label");
        toolBar.getItems().add(label);
        toolBar.getItems().add(new Separator());
    }
    /**
     * Mise à jour du label informatif.
     */
    public void updateLabelInfo() {
        label.setText(
            Resources.get("comp") + " " + model.getCurrent().comp + System.lineSeparator() + 
            Resources.get("memory") + " " + model.getCurrent().memory + System.lineSeparator() +
            Resources.get("time") + " " + model.tri.timeInMc((int)model.getCurrent().time) + 
            Resources.getSimple("mc")
        );
    }
    /**
     * Mise à jour des bouttons pour les activer ou les désactiver.
     */
    public void updateButton(){
        boolean forUndo = model.tri.arrayBloc.indexOf(model.getCurrent())==0;
        buttons.get("undo").setDisable(forUndo);
        boolean forStepStart = model.tri.arrayBloc.indexOf(model.getCurrent())==model.tri.arrayBloc.size()-1;
        buttons.get("step").setDisable(forStepStart);
        buttons.get("start").setDisable(forStepStart);
    }
    /**
     * Mise à jour de la progression du tri.
     */
    public void updateProgress(){
        progress.setProgress(
            (double) model.tri.arrayBloc.indexOf(model.getCurrent()) / (model.tri.arrayBloc.size()-1)
        );
        if(progress.getProgress()==1){
            controller.stopAnimation();
        }
    }
    /**
     * Création du timer pour l'animation
     * @param cycleDuration durée d'un cicle
     */
    public void createTimer(int cycleDuration) {
        timer = new Timeline(new KeyFrame(Duration.millis(cycleDuration), event -> {
            model.getNext();
            controller.updateView();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    /** Getters */
    public ProgressIndicator getProgress() {
        return progress;
    }

    public Timeline getTimer() {
        return timer;
    }

    public TableKeyValue getTableKeyValue(){
        return table;
    }

}