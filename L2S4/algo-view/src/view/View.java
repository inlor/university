package view;

import javafx.scene.Group;
import javafx.stage.Stage;

import view.component.*;
import controller.Controller;
import model.Model;

/**
 * Vue principale qui regroupe tous les composants.
 *
 * @author Lucian Petic
 * @see ComponentSort
 * @see ComponentMenuBar
 * @see ComponentControls
 * @see Controller
 * @see Model
 */
public class View{
    /** La fenêtre principale */
    private Stage stage;
    /** Modèle principale */
    private Model model;
    /** Composant du tri qui affiche l'animation */
    private ComponentSort sort;
    /** Barre de menu */
    private ComponentMenuBar menuBar;
    /** Barre d'outils */
    private ComponentControls controls;

    public View(Stage stage, Group root){
        this.stage = stage;

        // Initialisation du modèle
        model = new Model();

        // Initialisation du controleur
        Controller controller = new Controller(model, this);

        // Initialisation des composants
        sort = new ComponentSort(model, controller);
        menuBar = new ComponentMenuBar(model, controller);
        controls = new ComponentControls(model, controller);

        // Ajout des composants dans la fenêtre
        root.getChildren().addAll(sort, menuBar, controls);
    }

    /** Getters */
    public Stage getStage(){
        return stage;
    }

    public Model getModel(){
        return model;
    }

    public ComponentSort getSort(){
        return sort;
    }

    public ComponentMenuBar getMenuBar(){
        return menuBar;
    }

    public ComponentControls getControls(){
        return controls;
    }

}