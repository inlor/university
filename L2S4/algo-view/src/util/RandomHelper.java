package util;

/**
 * Classe qui regroupe toutes les méthodes sur le génération aléatoire.
 *
 * @author Lucian Petic
 */
public class RandomHelper {
    /**
     * Retourne une valeur aléatoire entre min et max.
     * 
     * @param min
     * @param max
     * @return int
     */
    public static int number(int min, int max) {
        return (int) (Math.random() * max) + min;
    }
}