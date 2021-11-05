
package model.sort;

import java.util.LinkedList;
import model.sort.Fusion;
import resources.Resources;
import util.ArrayHelper;

//Tri TimSort
//Complexité en calcul de l'ordre de n log n en moyenne
// Java program to perform TimSort.  
public class TimSort extends Tri {

    static int RUN = 32;
    Fusion f = new Fusion(new Bloc());
    LinkedList<int[]> tabs = new LinkedList<>();

    public TimSort(Bloc b) {
        super(b);
    }

    public void execution() {
        //Divisions par RUN
        startTimer();
        for (int i = 0; i < bloc.tab.length; i += RUN) {
            bloc.comp++;
            if (i + RUN - 1 >= bloc.tab.length)
                tabs.add(getTab(i, bloc.tab.length - 1));
            else
                tabs.add(getTab(i, i + RUN - 1));
        }

        //Tri par insertion de chaque division
        for (int i = 0; i < tabs.size(); i++){
            InsertionSort(tabs.get(i), i);
        }
            
        //Regroupement par fusion
        endTimer();
        while (tabs.size() > 1) {

            LinkedList<int[]> temp = new LinkedList<>();

            while (tabs.size() != 0){
                bloc.comp++;
                if (tabs.size() == 1) {
                    int[] x = temp.getLast();
                    temp.removeLast();
                    temp.add(f.fusion(tabs.get(0), x));
                    tabs.remove(0);
                } else {
                    temp.add(f.fusion(tabs.get(0), tabs.get(1)));
                    tabs.remove(0);
                    tabs.remove(0);
                }
                
            }
            tabs = temp;
            bloc.tab = assembleFusionTabs();
            addToStock();
            endTimer();
        }
    }

    // Left inclu, right inclu
    public int [] getTab(int left, int right){
        int [] retour = new int [right-left+1];

        for(int i = 0; left + i <= right; i++){
            retour[i] = bloc.tab[i+left];
        }

        return retour;
    }

    public void InsertionSort(int [] t, int x){
        startTimer();
        
        int i, j;
        for(i = 1; i < t.length; i++){
            int y = t[i];

            for(j = i; j > 0 && t[j-1] > y; j--){
                bloc.comp++;
                t[j] = t[j-1];
                bloc.memory++;
            }

            bloc.memory++;
            t[j] = y;
            bloc.tab = assembleInsertion(t, x);
            addToStock();
        }

        endTimer();
    }

    //Regroupe tout les tableaux de tabs en un seul
    public int [] assembleFusionTabs(){
        endTimer();
        int [] finalTab = new int [bloc.tab.length];
        int pos = 0;
        for(int [] e : tabs){
            for(int i = 0; i < e.length; i++){
                finalTab[pos] = e[i];
                pos++;
            }
        }
        startTimer();
        return finalTab;
    }

    //Regroupe tout les tableaux de tabs, excepté pour celui à l'index x, correspondant au tableau modifié par insertion, qui est ajouté a partir de t
    public int [] assembleInsertion(int [] t , int x){
        endTimer();
        int [] total = new int [0];
        for(int i = 0; i < tabs.size(); i++){
            if(i == x)
                total = f.addTabToTab(total, t);
            else
                total = f.addTabToTab(total, tabs.get(i));
        }
        startTimer();
        return total;
    }


    public String toString(){
        return Resources.get("tim");
    }

    //Clear TimSort
    public void clear(){
        super.clear();
        tabs.clear();
        f.clear();
    }
}