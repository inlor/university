package model.sort;

import model.sort.Bloc;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public abstract class Tri{

    public LinkedList<Bloc> arrayBloc = new LinkedList<Bloc>(); //Liste de blocs contenant les informations du tri effectué pour l'affichage de l'interface graphique
    public Bloc pointeur; //Bloc pointeur représentant un bloc dans arrayBloc
    public Bloc bloc; //Bloc utilisé comme base pour le tri
    public HashMap<Integer, String[]> hashMap = null; //HashMap utilisée pour les stats et donnée réelles
    long timer = 0; //Timer utilisé pour le calcul du temps d'exécution

    public Tri(Bloc bloc){
        this.bloc = bloc; 
        arrayBloc.add(new Bloc(bloc));
        pointeur = arrayBloc.get(0);
    }

    public Tri(Tri t){
        this.bloc = new Bloc(t.bloc);
        arrayBloc.add(new Bloc(bloc));
        pointeur = arrayBloc.get(0);
    }

    //Execute le tri a partir de Bloc en ajoutant chaque changement à arrayBloc
    public abstract void execution();

    //Renvoie le nom du tri
    public abstract String toString();

    //Ajoute le Bloc bloc actuel à arrayBloc
    public void addToStock(){
        endTimer();
        arrayBloc.add(new Bloc(bloc));
        startTimer();
    }
    
    //Ajoute le Bloc b à arrayBloc
    public void addToStock(Bloc b){
        endTimer();
        arrayBloc.add(new Bloc(b));
        startTimer();
    }

    //Renvoie true si le pointeur n'est pas au début de arrayBloc
    public boolean canPrevious(){
        if(arrayBloc.indexOf(pointeur) > 0)
            return true;
        return false;
    } 

    //Renvoie true si le pointeur n'est pas à la fin de arrayBloc
    public boolean canForward(){
        if(arrayBloc.indexOf(pointeur) < arrayBloc.size()-1)
            return true;
        return false;
    }

    //Bouge le pointeur en arrière dans arrayBloc
    public void previous(){
        if(canPrevious())
            pointeur = arrayBloc.get(arrayBloc.indexOf(pointeur)-1);
    }

    //Bouge le pointeur en avant dans arrayBloc
    public void forward(){
        if(canForward())
            pointeur = arrayBloc.get(arrayBloc.indexOf(pointeur)+1);
    }

    //Remet le pointeur au début de la liste
    public void reset(){
        while (canPrevious()){
            previous();
        }
    }

    //Lance un timer pour le calcul de la complexité en temps
    public void startTimer(){
        if(timer != 0)
            endTimer();
        timer = System.nanoTime();
    }

    //Stoppe le timer et ajoute la valeur obtenue à time
    public void endTimer(){
        if(timer != 0)
            bloc.time += System.nanoTime() - timer;
        timer = 0;
    }

    //Convertit time en millisecondes
    public float timeInMs(int value){
        return (float)TimeUnit.NANOSECONDS.toMicros(value)/1000;
    }

    //Convertit time en microsecondes
    public float timeInMc(int value){
        return (float)TimeUnit.NANOSECONDS.toMicros(value);
    }

    //Clear arrayBloc et remet le pointeur a 0
    public void clear(){
        arrayBloc.clear();
        arrayBloc.add(new Bloc(bloc));
        pointeur = arrayBloc.get(0);
    }
}
