package util;
import java.io.File;
import java.util.LinkedList;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import configuration.Configuration;
import resources.Resources;
/**
 * Manipulation des fichiers, leur lecture et écriture.
 * @author Lucian Petic
 * @author Joris Idjellidaine
 */
public class FileHelper {
    /** Données réelles sous forme de clé valeur */
    private HashMap<Integer,String[]> realData;
    public static LinkedList<String> getFilesPath(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        LinkedList<String> names = new LinkedList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                names.add(listOfFiles[i].getName());
            }
        }
        return names;
    }

    /**
     * Lecture du fichier qui a été choisie.
     * @param path
     */
    public void readFile(String path){
        realData = new HashMap<>();
        BufferedReader br = null;
        String line = "";
        try {
            int index = 0;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                realData.put(index++, line.split(Configuration.SPLIT));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Tri du en fonction d'une colonne.
     * @param hm
     * @param x
     * @return
     */
    public static HashMap<Integer, String[]> sortByValue(HashMap<Integer, String[]> hm, int x){ 
        List<Map.Entry<Integer, String[]> > list = new LinkedList<Map.Entry<Integer, String[]> >(hm.entrySet()); 
  
        Collections.sort(list, new Comparator<Map.Entry<Integer, String[]> >() { 
            public int compare(Map.Entry<Integer, String[]> o1, Map.Entry<Integer, String[]> o2){ 
                return (o1.getValue()[x]).compareTo(o2.getValue()[x]); 
            } 
        }); 
          
        HashMap<Integer, String[]> temp = new LinkedHashMap<Integer, String[]>(); 

        int index = 0;
        for (Map.Entry<Integer, String[]> aa : list){
            temp.put(index, aa.getValue());
            index++; 
        }

        return temp; 
    }
    /**
     * Exriture d'un fichier
     * @param content
     * @param file
     */
    public static void writeFile(String content, File file){
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Ouverture de la fenêtre pour sauvegarder un fichier.
     * @param stage
     * @param content
     */
    public static void saveFile(Stage stage, String content){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Excel", "*.csv")
        );
        File file = fileChooser.showSaveDialog(stage);
        FileHelper.writeFile(content, file);
    }

    public void importFile(Stage stage){
        // Choix du fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Resources.get("open"));
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Excel", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Lecture du fichier
            readFile(selectedFile.getAbsolutePath());
            selectedFile.delete();
        }
    }

    /** Getter */
    public HashMap<Integer, String[]> getReadData(){
        return realData;
    }
}