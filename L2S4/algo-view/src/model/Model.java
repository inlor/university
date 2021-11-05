package model;

import java.util.HashMap;
import configuration.Configuration;
import java.util.LinkedList;
import model.sort.*;
import util.RandomHelper;

public class Model{

    public Bloc bloc;//Objet contenant toutes les valeurs importantes sur le tri (tableau,complexite,acces memoire,valeur selectionne)
    public Tri tri;//Object abstrait representant une classe de tri, contient un Bloc egal a Model.Bloc pour plus de facilite dans le code
    
    public Model(final int size){//Toutes les valeurs donnees sont arbitraires et peuvent etre modifies
      bloc = new Bloc(Configuration.defaultArray(size));
      tri = new Selection(bloc);
      tri.execution();
    }

    public Model(){//Toutes les valeurs donnees sont arbitraires et peuvent etre modifies
        this(Configuration.MAX_ARRAY_SIZE);    
    }


    //Set un nouveau tri
    public void setSort(Tri t){
      // recuperation des données réelles
      HashMap<Integer, String[]> tmp = null;
      if(tri.hashMap!=null){
        tmp = tri.hashMap;
      }
      tri = t;
      bloc = new Bloc(tri.arrayBloc.get(0));
      tri.bloc = bloc;
      tri.clear();
      tri.execution();
      tri.hashMap = tmp;
    }

    //Set la HashMap du tri
    public void setHash(HashMap<Integer, String[]> hm){
      tri.hashMap = hm;
      bloc = new Bloc(randomPerfectTab(tri.hashMap.size()));
      tri.bloc = bloc;
      tri.clear();
      tri.execution();
    }

    //Renvoie un tableau de taille size peuplé de valeur allant de 0 à size-1 triées aléatoirement
    private int [] randomPerfectTab(int size){
      int [] tab = new int [size];
      LinkedList<Integer> slots = new LinkedList<Integer>();
      for(int i = 0; i < size; i++)
        slots.add(i);
      for(int i = 0; i < size; i++){
        int r = RandomHelper.number(0, slots.size()); //new Random().nextInt(slots.size());
        tab[i] = slots.get(r);
        slots.remove(r);
      }

      return tab;
    }

    //Set un nouveau tableau depuis une taille size
    public void setArrayFromSize(int size){
      bloc = new Bloc(Configuration.defaultArray(size));
      tri.bloc = bloc;
      tri.clear();
      tri.execution();
      tri.hashMap = null;
    }

    public Bloc getCurrent(){
      return tri.pointeur;
    }

    public Bloc getNext(){
      tri.forward();
      return tri.pointeur;
    }

    public Bloc getPrevious(){
      tri.previous();
      return tri.pointeur;
    }
}

