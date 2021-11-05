package util;

/**
 * Classe qui regroupe toutes les méthodes sur le chaines de caracteres.
 *
 * @author Lucian Petic
 */
public class Util {
    /**
     * Retourne la chaîne de caracteres fournie, avec la premiere lettre en
     * majuscule. Elle s'assure que le premier caracter n'est un underscore, car il
     * est utilisé pour les raccourcis du clavier.
     * 
     * @param str
     * @return String
     */
    public static String cap(String str) {
        String underscore = "_";
        if (str.substring(0, 1).equals(underscore)) {
            return underscore + str.substring(1, 2).toUpperCase() + str.substring(2);
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}