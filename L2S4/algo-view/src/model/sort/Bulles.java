package model.sort;

import resources.Resources;
import util.ArrayHelper;

//Tri à bulle
//Complexité en calcul de l'ordre de n² en moyenne
public class Bulles extends Tri{

    public Bulles(Bloc b){
        super(b);
    }

    public void execution() {
        startTimer();
        for(int i = bloc.tab.length-1;i>0;i--){

            for(int j = 0;j<i;j++){

                if(bloc.tab[j+1] < bloc.tab[j]){ 
                    bloc.memory += 2;
                    ArrayHelper.exchange(bloc.tab,j + 1, j);
                }
                bloc.comp++;
            }
            addToStock();
        }
        endTimer();
    }

    public String toString(){
        return Resources.get("bul");
    }
}
