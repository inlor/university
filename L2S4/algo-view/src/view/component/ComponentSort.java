package view.component;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;

import resources.Resources;
import model.Model;
import util.ScreenHelper;
import controller.Controller;

/**
 * Classe qui sert pour l'affichage du tableau à trier.
 * 
 * @author Lucian Petic
 * @see Resources
 * @see Model
 * @see SreenHelper
 */
public class ComponentSort extends Group {
    /** Modèle principale */
    private Model model;
    /** Contôlleur principale */
    private Controller controller;
    /** Taille d'un bloc */
    private int widthBloc;
    /** Hbox pour les blocs */
    private final HBox hbox = new HBox(1);
    /** ScrollBar pour le déplacement des blocs */
    private final ScrollBar scroll = new ScrollBar();

    public ComponentSort(Model model, Controller controller) {
        this.model=model;
        this.controller = controller;
        widthBloc = ScreenHelper.getWidth()/model.getCurrent().tab.length;
        
        createScroll();
        this.getChildren().addAll(hbox, scroll);
        hbox.setLayoutY(0);
        hbox.setRotate(180);
        hbox.setPrefHeight(ScreenHelper.getHeight());
        hbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        reprint();
    }

    /**
     * Affichage à l'écran du tableau à trier.
     */
    private void print() {
        for (int i = 0; i < model.getCurrent().tab.length; i++) {
            createRectangle(i);
        }
    }

    /**
     * Reaffichage à l'écran du tableau en cours de tri.
     */
    public void reprint() {
        hbox.getChildren().clear();
        print();
    }
    /**
     * Mise de l'espacement entre les blocs
     * @param value
     */
    public void setSpacing(int value){
        hbox.setSpacing(value);
        scroll.setMax(model.getCurrent().tab.length*(value*2));
        widthBloc = value;
    }
    /**
     * Création d'un bloc en function de sa taille.
     * @param index taille du bloc
     */
    private void createRectangle(int index){
        Rectangle rectangle = new Rectangle();
        setMessage(rectangle,index);
        rectangle.setNodeOrientation(NodeOrientation.INHERIT);
        rectangle.setFill(getColor(index));
        rectangle.setHeight(model.getCurrent().tab[index]); 
        rectangle.setWidth(widthBloc);
        rectangle.setOnTouchPressed(e -> {
            controller.controllerRectangle(index);
        });
        hbox.getChildren().add(rectangle);
    }

    /**
     * Génération d'une couleur en function de la valuer d'une case.
     * 
     * @param position
     * @return Color
     */
    private Color getColor(int position) {
        if(position==model.getCurrent().valSelected){
            return Color.rgb(0, 0, 0);
        }else if(position==model.getCurrent().previousVal){
            return Color.rgb(255, 255, 255);
        }
        int value = model.getCurrent().tab[position];
        int coef = 3;
        return Color.web("hsl("+value/coef+",100%,100%)");
    }
    /**
     * Mise d'un message sur un bloc.
     * @param rectangle bloc qui porte un message
     * @param index indice du bloc
     */
    private void setMessage(Rectangle rectangle, int index){
        String hash = "";
        if(model.tri.hashMap!=null){
            for (String element : model.tri.hashMap.get(index)) {
                hash = element+System.lineSeparator();
            }
        }
        Tooltip message = new Tooltip(
                Resources.get("key")+": "+Integer.toString(index)+System.lineSeparator()+
                Resources.get("value")+": "+Integer.toString(model.getCurrent().tab[index])+System.lineSeparator()+
                hash
            );
        Tooltip.install(rectangle, message);
    }

    /**
     * Creation de la scrollbar
     */
    private void createScroll(){
        scroll.getStyleClass().add("scroll");
        scroll.setPrefWidth(ScreenHelper.getWidth());
        scroll.setLayoutY(ScreenHelper.getHeight());
        scroll.setMin(0);
        scroll.setMax(model.getCurrent().tab.length);
        scroll.setValue(0);
        scroll.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> value,
                Number oldValue, Number newValue) {
                    hbox.setLayoutX(-oldValue.doubleValue());
            }
        });
    }
}