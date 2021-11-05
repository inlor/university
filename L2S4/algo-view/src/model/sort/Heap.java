package model.sort;

import resources.Resources;
import model.sort.Bloc;
import util.ArrayHelper;
/**
 * Tri par tas
 * @author Lucian Petic 
 */

//Tri par tas
//Complexité en calcul de l'ordre de n log n en moyenne
public class Heap extends Tri {

    public Heap(Bloc b) {
        super(b);
    }

    /**
     * Exection complète du tri
     */
    public void execution() {
        heapSort();
    }
    /**
     * Etape intermediaire du tri
     */
    private void sieve(int[] tree, int node, int n) {
        int k = node;
        int j = 2 * k;

        while (j <= n) {
            
            bloc.comp++;
            if (j < n){
                bloc.comp++;
                if(tree[j] < tree[j + 1])
                    j++;
            }

            bloc.comp++;
            if (tree[k] < tree[j]) {
                bloc.memory += 2;
                ArrayHelper.exchange(tree, k, j);
                addToStock();
                k = j;
                j = 2 * k;
            } else {
                break;
            }
        }
    }
    /**
     * Function principale du tri
     */
    public void heapSort() {
        startTimer();
        for (int i = bloc.tab.length >> 1; i >= 0; i--) {
            sieve(bloc.tab, i, bloc.tab.length - 1);
        }

        for (int i = bloc.tab.length - 1; i >= 1; i--) {
            bloc.memory += 2;
            ArrayHelper.exchange(bloc.tab, i, 0);
            sieve(bloc.tab, 0, i - 1);
        }
        endTimer();
    }

    public String toString(){
        return Resources.get("heap");
    }
}