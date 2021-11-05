package util;

import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

/**
 * Classe qui regroupe toutes les méthodes sur les tailles de l'écran de
 * l'utilisateur. Son utilisation est importante pour une interface utilisateur
 * adaptative.
 *
 * @author Lucian Petic
 */
public class ScreenHelper {
    /** Variable principale qui stoke les informations. */
    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    /**
     * Retourne la largeur de l'écran.
     * 
     * @return int
     */
    public static int getWidth() {
        return (int) primaryScreenBounds.getWidth();
    }

    /**
     * Retourne la hauteur de l'écran.
     * 
     * @return int
     */
    public static int getHeight() {
        return (int) primaryScreenBounds.getHeight();
    }
}