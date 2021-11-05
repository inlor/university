package configuration;

import util.ArrayHelper;
import util.ScreenHelper;

/**
 * Classe qui regroupe les constantes et les methodes liées à la configuration
 * de l'application.
 * 
 * @author Lucian Petic
 * @see ArrayHelper
 * @see ScreenHelper
 */
public class Configuration {
    /** Nom général de l'application */
    public static final String NAME_APP = "app";
    /** Chemin rélatif pour le style */
    public static final String PATH_STYLE = "style/main.css";
    /** Chemin rélatif du dossier des icônes */
    public static final String PATH_REAL_DATA = "resources/data";
    /** Chemin rélatif du dossier des icônes */
    public static final String PATH_ICONS = "file:../resources/img/";
    /** Extention des icônes */
    public static final String EXTENTION_ICONS = ".png";
    /** Extention des données réelles et des ressources */
    public static final String EXTENTION_DATA = ".csv";
    /** Mode de separation des données réelles et des ressources  */
    public static final String SPLIT = ",";
    /** Valeur maximale présente dans un tableau */
    public static final int MAX_VALUE = (int) (ScreenHelper.getHeight() / 1.5);
    /** Valeur minimale présente dans un tableau */
    public static final int MIN_VALUE = 10;
    /** Taille maximale d'un tableau */
    public static final int MAX_ARRAY_SIZE = ScreenHelper.getWidth() / 2;
    /** Taille minimale d'un tableau */
    public static final int MIN_ARRAY_SIZE = 1;
    /** Temps maximale en millisecondes pour une étape de l'animation */
    public static final int MIN_DELAY = 150;
    /** Temps maximale en millisecondes pour une étape de l'animation */
    public static final int MAX_DELAY = 5000;
    /** Maximum de valeurs pour les données réelles */
    public static final int MAX_REAL_DATA = 1000;

    /**
     * Retourne le chemin rélatif d'une icône.
     * 
     * @param name Nom du fichier
     * @return Chemin rélatif du fichier
     */
    public static final String getIconPath(String name) {
        return PATH_ICONS + name + EXTENTION_ICONS;
    }

    /**
     * Retourne le tableau par défaut en fonction de la taille de l'écran.
     * 
     * @param size Taille du tableau
     * @return Un tableau aléatoire de taille size
     */
    public static final int[] defaultArray(int size) {
        return ArrayHelper.randomArrayInt(size, MIN_VALUE, MAX_VALUE);
    }
}