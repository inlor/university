import java.util.*;
import model.StatMaker;
import model.sort.*;
import resources.Resources;

public class Test{

    public static void main(String[] args) {
        Resources.initResources();

        

        //Tests for runSortStat & runSortStatXTimes (model.StatMaker.java) | OK
        HashMap<String,Bloc> hm = model.StatMaker.createHashOfSortsXTimes(5,2784);
        Iterator<String> keys = hm.keySet().iterator();
        Iterator<Bloc> vals = hm.values().iterator();

        HashMap<String,Bloc> hm2 = StatMaker.runSortStat(new Heap(new Bloc()));
        Iterator<String> keys2 = hm2.keySet().iterator();
        Iterator<Bloc> vals2 = hm2.values().iterator();
        
        System.out.println();
        while(keys2.hasNext() && vals2.hasNext()){
            System.out.println("Nom : "+keys2.next()+" -> Value : "+vals2.next().comp);
        }

        System.out.println("\nHashmap size : "+hm.size()+"\n---------------");
        while(keys.hasNext() && vals.hasNext()){
            System.out.println("Nom : "+keys.next()+" -> Value : "+vals.next().comp);
        }

        //-----------------------------------------------------------------------
    
    }
}
