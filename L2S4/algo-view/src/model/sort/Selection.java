package model.sort;

import resources.Resources;
import util.ArrayHelper;

//Tri par Selection
//Complexité en calcul de l'ordre de n² en moyenne
public class Selection extends Tri{

    public Selection(Bloc b){
        super(b);
    }

    public void execution(){
        startTimer();
        int min;

        for(int i = 0; i < bloc.tab.length-1; i++){
            startTimer();
            min = i;

            for(int j = i+1; j < bloc.tab.length; j++){
                bloc.valSelected = j;
                if(bloc.tab[j] < bloc.tab[min]){
                    bloc.newVal(j);
                    min = j;
                }
                    
                bloc.comp ++;
            }

            ArrayHelper.exchange(bloc.tab,i,min);
            bloc.memory += 2;
            addToStock();
        }
        endTimer();
    }
    
    public String toString(){
        return Resources.get("sel");
    }
}

