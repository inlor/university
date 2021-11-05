package resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;

import configuration.Configuration;
import util.ArrayHelper;
import util.Util;

/**
 * Classe principale qui est chargé de l'affichage des chaînes de caracteres en
 * function de la langue de l'utilisateur.
 *
 * @author Lucian Petic
 * @see ArrayHelper
 * @see Util
 */
public final class Resources {
    /** Langue courrante */
    public static String lang = readConfig();
    /** Langues disponibles */
    private static ArrayList<String> langs;
    /** Ressources charges depuis le fichier .csv */
    private static Map<String, String[]> resources = Resources.initResources();
    /** Chemin pour accéder aux ressources */
    private static final String CONFIG = "../resources/lang/config";
    /** Chemin pour accéder aux ressources */
    private static final String FILE = "../resources/lang/app.lang.resources";
    /** Les types d'encodage à utiliser pour l'affichage des caractères spéciaux */
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO = Charset.forName("ISO-8859-1");

    /**
     * Retourne le text correspondant à la clé fournie 
     * avec une première lettre en majuscule obligatoirement.
     * 
     * @param key Clé qui correspond à une valeur
     * @return Valeur correspondante à la clé fournie
     */
    public static final String get(String key) {
        return Util.cap(getSimple(key));
    }

    /**
     * Retourne le text correspondant à la clé fournie.
     * 
     * @param key Clé qui correspond à une valeur
     * @return Valeur correspondante à la clé fournie
     */
    public static final String getSimple(String key){
        return new String(resources.get(key)[getLanguageId()].getBytes(ISO), UTF_8);
    }

    /**
     * Retourne le raccourci correspondant à la clé fournie.
     * 
     * @param key Clé qui correspond à une valeur
     * @return Valeur correspondante à la clé fournie
     */
    public static final String getShortcut(String key) {
        return resources.get(key)[1].toUpperCase();
    }

    /**
     * Permet de changer la langue de l'application
     * 
     * @param language Nouvelle langue choisie
     * @return vrai (true) si la langue a pû être changé
     */
    public static final boolean changeLanguage(String language) {
        if (langs.contains(language) && !language.equals(lang)) {
            List<String> lines = Arrays.asList("lang," + language);
            Path file = Paths.get(CONFIG);
            try {
                Files.write(file, lines, UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * Initialisation des ressources, en allant cherchant les données depuis le
     * fichier .csv à l'aide du système clé - valeur on introduit dans un objet de
     * type Map les données.
     */
    public static final HashMap<String, String[]> initResources() {
        initLanguages();
        HashMap<String, String[]> resources = new HashMap<>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(FILE+Configuration.EXTENTION_DATA));
            while ((line = br.readLine()) != null) {
                String[] arrExploded = line.split(Configuration.SPLIT);
                resources.put(arrExploded[0], line.split(Configuration.SPLIT));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
    /**
     * Getter pour les langues
     * @return Toutes les langues disponibles 
     */
    public static final ArrayList<String> getLangs() {
        return langs;
    }

    /**
     * Initialisation du tableau langs avec les langues disponibles depuis le
     * fichier des ressources.
     */
    private static final void initLanguages() {
        langs = new ArrayList<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(FILE+Configuration.EXTENTION_DATA));
            line = br.readLine();
            for (String str : line.split(Configuration.SPLIT)) {
                langs.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeNoLangs();
    }

    /**
     * Fonction qui donne l'id de la langue à utiliser pour l'initialisation des
     * données . En cas d'une demande d'une langue inéxistante c'est l'identifiant
     * anglais qui est retourné.
     * 
     * @return Id de la langue en function du fichier des ressources
     */
    private static final int getLanguageId() {
        int indexDefault = 2;
        for (int i = 0; i < langs.size(); i++) {
            if (langs.get(i).equals(lang)) {
                return i + indexDefault;
            }
        }
        return indexDefault;
    }

    /**
     * Lecture du fichier de configuration, pour l'initialisation de la langue de
     * l'utilisatateur, ce fichier est particulierement imporant dans le cas ou la
     * langue a été changé.
     * 
     * @return La langue du fichier de configuration
     */
    private static final String readConfig() {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(CONFIG));
            while ((line = br.readLine()) != null) {
                line = line.split(Configuration.SPLIT)[1];
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * Supprimer les colonnes qui ne sont pas des langues.
     */
    private static final void removeNoLangs() {
        langs.remove("");
        langs.remove("shutcut");
    }
}