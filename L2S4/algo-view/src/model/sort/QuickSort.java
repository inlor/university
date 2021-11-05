package model.sort;

import resources.Resources;
import util.ArrayHelper;

public class QuickSort extends Tri {

    public QuickSort(Bloc b){
        super(b);
    }

    public void execution() {
        sort(0,bloc.tab.length-1);
    }

    int partition(int low, int high) { 
        startTimer();
        int pivot = bloc.tab[high];  
        int i = low-1;  
        for(int j = low; j < high; j++) { 
           
            if(bloc.tab[j] <= pivot) { 
                i++; 
                endTimer();
                int [] temp = bloc.tab.clone();

                ArrayHelper.exchange(bloc.tab,i,j);
                bloc.memory += 2;
                if(isDifferent(temp, bloc.tab))
                    addToStock();
            }
            startTimer();
            bloc.comp ++; 
        } 
  
        endTimer();
        int [] temp = bloc.tab.clone();
        startTimer();
        
        ArrayHelper.exchange(bloc.tab,i+1,high);
        bloc.memory += 2;
        if(isDifferent(temp, bloc.tab))
            addToStock();
        
        endTimer();
        return i+1; 
    } 

    public boolean isDifferent(int [] x, int [] y){
        endTimer();
        for(int val = 0; val < x.length && val < bloc.tab.length; val++){
            if(x[val] != y[val]){
                startTimer();
                return true;
            }
        }
        startTimer();
        return false;
    }
  
  
    public void sort(int low, int high){ 
        startTimer();
        if (low < high) { 
            bloc.comp++;
            int pi = partition(low, high); 
  
            sort(low, pi-1); 
            sort(pi+1, high); 
        } 
        endTimer();
    } 

    public String toString() {
        return Resources.get("qui");
    }
}
