package model.sort;
import util.ArrayHelper;

//La classe Bloc contient toutes les données necessaires pour la vue, les stats et le tableau
//Chaque Bloc corresponds a une étape d'un tri donné
public class Bloc{

    public int previousVal;
    public int valSelected;
    public int memory;//Complexité en mémoire du bloc
    public int comp;//Complexité en calcul du bloc
    public long time;//Complexité en temps du bloc
    public int [] tab;//Tableau contenu dans le bloc

    public Bloc(){
        valSelected = 0;
        memory = 0;
        comp = 0;
        time = 0;
        tab = ArrayHelper.randomArrayInt(50,0,100);//Valeurs de base
    }

    public Bloc(int [] tab){
        valSelected = 0;
        memory = 0;
        comp = 0;
        time = 0;
        this.tab = tab;
    }

    public Bloc(Bloc b){
        valSelected = b.valSelected;
        memory = b.memory;
        comp = b.comp;
        time = b.time;
        tab = b.tab.clone();
    }

    public void newVal(int x){
        previousVal = valSelected;
        valSelected = x;
    }

    //Echange x et y
    public void echanger(int x,int y){
        int temp = x;
        x = y;
        y = temp;
    }
}
