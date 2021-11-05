package model;

import java.util.*;
import configuration.Configuration;
import model.sort.*;
import model.sort.TimSort;

public class StatMaker{

    //Execute tout les tris possibles x fois sur un bloc aléatoire de taille size et renvoie une HashMap contenant le nom et les blocs finaux de tout ces tris
    //Utilisé pour représenter des stats entre tout les tris sur un tableau donné
    public static HashMap<String, Bloc> createHashOfSortsXTimes(int x, int size){
        HashMap<String, Bloc> stats = new HashMap<>();

        stats.putAll(runSortStatXTimes(new Heap(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new Selection(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new Bulles(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new Insertion(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new Fusion(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new TimSort(new Bloc()), x, size));
        stats.putAll(runSortStatXTimes(new QuickSort(new Bloc()), x, size));

        //TODO Rajouter les autres tris ici selon la convention donnée
        return stats;
    }

    //Effectue la moyenne de x tri effectué sur des tableaux de taille size
    public static HashMap<String, Bloc> runSortStatXTimes(Tri t, int x,int size){
        
        Bloc b = new Bloc(t.bloc);

        for(int i = 0; i < x; i++){
            t.bloc = new Bloc(Configuration.defaultArray(size));
            t.execution();
            b.comp += t.bloc.comp;
            b.memory += t.bloc.memory;
            b.time += t.bloc.time;
            t.clear();
        }

        b.comp = b.comp/x;
        b.memory = b.memory/x;
        b.time = b.time/x;
        
        HashMap<String, Bloc> retour = new HashMap<>();
        retour.put(t.toString(), b);
        return retour;
    }

    //No idea what this does
    public static String save(HashMap<Integer, String []> hm){
        String mot = "";
        for (Map.Entry<Integer, String []> entry : hm.entrySet()) {
            for(int i = 0; i < entry.getValue().length; i++){
                if(i < entry.getValue().length-1){
                    mot += entry.getValue()[i]+",";
                } else {
                    mot += entry.getValue()[i];
                } 
            }
            mot += " \n ";
        }
        return mot;
    }
}
