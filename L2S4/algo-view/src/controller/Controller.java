package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import configuration.Configuration;
import model.Model;
import model.StatMaker;
import model.sort.*;
import view.View;
import view.component.notification.Action;
import view.component.notification.Notification;
import view.component.notification.Choice;
import view.component.chart.ChartSort;
import util.ArrayHelper;
import util.FileHelper;
/**
 * Classe principale pour le contrôle le l'application.
 *
 * @author Lucian Petic
 * @see Model
 * @see View
 * @see ArrayHelper
 * @see SortChange
 */
public class Controller {

    /**Modèle principale */
    private Model model;
    /**Vue principale */
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Contrôle pour la barre du menu.
     * @param side le contenu du boutton
     */
    public void controllerMenuBar(String side) {
        stopAnimation();
        switch (side) {
            case "open":
                readFile();
                break;
            case "save":
                saveFile();
                break;
            case "exit":
                exit();
                break;
            case "undo":
                model.getPrevious();
                break;
            case "step":
                model.getNext();
                break;
            case "langage":
                getView().getMenuBar().changeLanguage();
                break;
            case "best":
                createStatsRealData();
                break;
            default:
                break;
        }
        updateView();
    }

    /**
     * Contrôle pour les boutons de la toolbar.
     * @param button
     */
    public void controllerButton(Button button) {
        stopAnimation();
        switch (button.getAccessibleHelp()) {
            case "undo":
                model.getPrevious();
                break;
            case "step":
                model.getNext();
                break;
            case "start":
                startAnimation();
                break;
            case "pause":
                stopAnimation();
                break;
            case "stats":
                createStats();
                break;
            default:
                stopAnimation();
                break;
        }
        updateView();
    }

    /**
     * Contrôle pour la choicebox pour le tri à utiliser.
     * @param index
     */
    public void controllerChoiceSort(int index) {
        //TODO Update names of sorts 
        Bloc tmp = model.tri.arrayBloc.get(0);
        switch (index) {
            case 1:
                model.setSort(new Insertion(tmp));
                break;
            case 2:
                model.setSort(new Bulles(tmp));
                break;
            case 3:
                model.setSort(new TimSort(tmp));
                break;
            case 4:
                model.setSort(new Fusion(tmp));
                break;
            case 5:
                model.setSort(new Heap(tmp));
                break;
            case 6:
                model.setSort(new QuickSort(tmp));
                break;
            default:
                model.setSort(new Selection(tmp));
                break;
        }
        updateView();
    }
    /**
     * Choix de la taille du tableau
     * @param value taille du tableau
     */
    public void controllerSlider(int value){
        model.setArrayFromSize(value);
        model.setSort(model.tri);
        updateView();
    }
    /**
     * Changement de la vitesse l'animation
     * @param value timer pour l'animation
     */
    public void controllerTimer(int value){
        stopAnimation();
        getView().getControls().createTimer(value);
        startAnimation();
    }

    /**
     * Contrôle pour la choicebox.
     * @param index
     */
    public void choiceSpacing(int index) {
        getView().getSort().setSpacing(index+1);
        updateView();
    }

    public void controllerRectangle(int index){
        getView().getControls().getTableKeyValue().updateTableSelected(index);
    }

    /**
     * Mise à jour de la vue.
     * De façon généralle pour: le tableau, les labels, la bar de progression, l'animation et les boutons.
     */
    public void updateView() {
        getView().getSort().reprint();
        getView().getMenuBar().updateMenu();
        getView().getControls().updateLabelInfo();
        getView().getControls().updateButton();
        getView().getControls().getTableKeyValue().updateTable();
        getView().getControls().updateProgress();
    }

    /**
     * Importation et lecture des donées réelles.
     */
    private void readFile(){
        FileHelper importFile = new FileHelper();
        importFile.importFile(getView().getStage());
        if(importFile.getReadData()!=null){
            HashMap<Integer, String[]> realData = importFile.getReadData();
            if(realData.size()<Configuration.MAX_REAL_DATA){
                choiceColumnToSort(realData);
                updateView();
            }else{
                new Notification("warning", "info_warning_max_value", AlertType.WARNING, null);
            }
        }
    }
    /**
     * Sauvegarde d'un fichier après avoir été trié.
     */
    private void saveFile(){
        if(model.tri.hashMap!=null){
            String content = StatMaker.save(model.tri.hashMap);
            System.out.println(content);
            FileHelper.saveFile(getView().getStage(), content);
        }
    }
    /**
     * Choix de la colone en function de laquelle il faut trier les données réelles.
     */
    private void choiceColumnToSort(HashMap<Integer, String[]> realData){
        List<String> choices = new ArrayList<>();
        for (String item : realData.get(0)) {
            choices.add(item);
        }
        Choice sortBy = new Choice("open", "choice_column", realData.get(0)[0], choices);
        sortBy.setAction(new Action(){
            @Override
            public void execute() {
                if(sortBy.getResult().isPresent()){
                    sortBy.setResponse(Integer.toString(choices.indexOf(sortBy.getResult().get())));
                }
            }
        });
        if(realData!=null && sortBy.getResult().isPresent()){
            model.setHash(FileHelper.sortByValue(realData, Integer.valueOf(sortBy.getResponse())));
        }
    }
    /**
     * Sortie de l'application
     */
    private void exit(){
        new Notification("exit", "info_exit", AlertType.CONFIRMATION, new Action(){
            @Override 
            public void execute() {
                Platform.exit();
            }
        });
    }
    /**
     * Création des statistiques pour un tableau donné.
     */
    private void createStats(){
        new ChartSort(getView().getStage(), model);
    }

    /**
     * Statistiques sur les données réelles.
     */
    private void createStatsRealData(){
        System.out.println("ok");
    }

    /**
     * Démarrer l'animation.
     */
    public void startAnimation() {
        getView().getControls().getTimer().play();
    }

    /**
     * Arrêter l'animation.
     */
    public void stopAnimation() {
        getView().getControls().getTimer().stop();
    }

    /**
     * Getters pour la vue
     * @return view
     */
    public View getView() {
        return view;
    }
}