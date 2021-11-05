package model.sort;

import resources.Resources;

//Tri par insertion
//Complexité en calcul de l'ordre de n² en moyenne
public class Insertion extends Tri {

    public Insertion(Bloc b){
        super(b);
    }

    @Override
    public void execution() {
        startTimer();

        int i, j;
        for(i = 1; i < bloc.tab.length; i++){
            int x = bloc.tab[i];

            for(j = i; j > 0 && bloc.tab[j-1] > x; j--){
                    bloc.comp++;
                    bloc.tab[j] = bloc.tab[j-1];
            }

            bloc.memory++;
            bloc.tab[j] = x;
            addToStock();
        }

        endTimer();
    }

    @Override
    public String toString() {
        return Resources.get("ins");
    }
}
