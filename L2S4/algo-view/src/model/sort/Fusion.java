package model.sort;

import resources.Resources;
import java.util.LinkedList;

//Tri fusion
//Complexité en calcul de l'ordre de n log n en moyenne
public class Fusion extends Tri{

    LinkedList<int []> fusionListe = new LinkedList<int []>();//Liste de tab contenant les fusions

    public Fusion(Bloc b){
        super(b);
    }

    //Effectue une etape du tri | Methode principale
    public void execution(){
        triFusion(bloc.tab);
    }

    public int [] totalSizeFusionListe(){
        int m = 0;
        for(int [] e : fusionListe){
            m += e.length; 
        }
        int [] tab = new int [m];
        int k = 0;
        for(int [] e : fusionListe){
            for(int x : e){
                tab[k] = x;
                k++;
            }
        }
        return tab;
    }

    boolean isTheSame(int [] a, int [] b){
        for(int i = 0; i < a.length;i++){
            if(a[i] != b[i])
                return false;
        }
        return true;
    }

    boolean existInListe(int [] a){
        for(int [] e : fusionListe)
            if(isTheSame(e,a))
                return true;
        return false;
    }

    public void fusionToTab(int [] gauche, int [] droite, int [] fusion){
        endTimer();
        if(!existInListe(fusion))
            fusionListe.add(fusion);
        
        if(fusionListe.contains(gauche))
            fusionListe.remove(gauche);
        if(fusionListe.contains(droite))
            fusionListe.remove(droite);
        
        int [] max = totalSizeFusionListe();
        bloc.tab = addTabToTab(max,getTab(bloc.tab,max.length,bloc.tab.length));
        startTimer();
    }

    public int [] triFusion (int [] tab){
        startTimer();
        if(tab.length <= 1)
            return tab;
        bloc.comp ++;
        
        int mid = tab.length/2;

        int [] gauche = triFusion(getTab(tab, 0, mid));
        int [] droite = triFusion(getTab(tab, mid, tab.length));
        int [] fusion = fusion(gauche,droite);

        fusionToTab(gauche,droite,fusion);
        addToStock();
        endTimer();

        return fusion;
    }

    public int [] fusion(int [] tab1, int [] tab2){
        if(tab1.length == 0){
            bloc.comp++;
            return tab2;
        }
            
        else if(tab2.length == 0){
            bloc.comp += 2;
            return tab1;
        }
        else if(tab1[0] < tab2[0]){
            bloc.comp += 3;            
            return addIntToTab(tab1[0],fusion(getTab(tab1, 1, tab1.length), tab2));

        }
        else{
            bloc.comp += 3;
            return addIntToTab(tab2[0],fusion(getTab(tab2, 1, tab2.length), tab1));  
        }
    }

    public int [] addIntToTab(int x, int [] tab){
        int [] tabFinal = new int[tab.length+1]; 
        tabFinal[0] = x;
        bloc.memory ++;
        for(int i = 1; i < tabFinal.length;i++)
            tabFinal[i] = tab[i-1];
        return tabFinal;
    }

    public int [] addTabToTab(int [] tab1, int [] tab2){
        int [] tabFinal = new int[tab1.length+tab2.length];
        for(int i = 0; i < tab1.length;i++)
            tabFinal[i] = tab1[i];
        for(int i = 0; i < tab2.length;i++)
            tabFinal[tab1.length+i] = tab2[i];
        return tabFinal;
    }

    public int [] getTab(int [] tab, int a, int b){    
        if(b-a <= 0)
            return new int [0];

        int [] tabFinal = new int[b-a]; 

        for(int i = 0; i < b-a;i++){
            tabFinal[i] = tab[i+a];
        }
        return tabFinal;
    }
    
    public String toString(){
        return Resources.get("mer");
    }

    public void clear(){
        super.clear();
        fusionListe.clear();
    }
}
