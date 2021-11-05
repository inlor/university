package util;

import util.RandomHelper;

/**
 * Classe qui regroupe toutes les méthodes sur les tableaux.
 *
 * @author Lucian Petic
 * @author Joris Idjellidaine
 * @see RandomHelper
 */
public class ArrayHelper {
    /**
     * Genere alétoirement un tableau. Avec des valeures comprises entre le min et
     * le max.
     * 
     * @param length
     * @param min
     * @param max
     * @return int[]
     */
    public static int[] randomArrayInt(int length, int min, int max) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = RandomHelper.number(min, max);
        }
        return array;
    }

    /**
     * Retourne vrai dans le cas ou la tableau contien l'elements specifié.
     * 
     * @param elem
     * @param elements
     * @return boolean
     */
    public static <E> boolean contains(E elem, E elements[]) {
        for (E element : elements) {
            if (elem.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne la valeur maximale d'un tableau
     * 
     * @param array
     * @return
     */
    public static int getMax(int array[]) {
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    /**
     * Retourne la valeur minimale d'un tableau
     * 
     * @param array
     * @return int
     */
    public static int getMin(int array[]) {
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    /**
     * Retourne un tableau à deux cases ou la chaine de caracteres est divisé par 2.
     * C'est à dire que la premiere case contient la premiere moitié de la chaine.
     * Et la deuxième case contient la deuxième moitié de la chaine.
     * 
     * @param array
     * @return String []
     */
    public static String[] splitHalf(String str) {
        String array[] = str.split(" ");
        int middle = array.length / 2;
        String space = " ";
        String[] parts = new String[2];
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                parts[0] = space;
                parts[1] = space;
            }
            if (i < middle) {
                parts[0] += array[i] + space;
            } else {
                parts[1] += array[i] + space;
            }
        }
        return parts;
    }

    /**
     * Permet d'echanger deux elements d'un tableau.
     */
    public static void exchange(int array[], int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Affichage en mode textuel d'un tableau.
     * 
     * @param array
     */
    public static void printArray(int array[]) {
        for (int element : array)
            System.out.print(element + ", ");
    }
}