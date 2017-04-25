package algo3;
import java.math.*;


public class CointsCounter {


    static int betrag[] = new int[]{2,3,10,77};

    static BigInteger tab[][] = new BigInteger[77][7];
    // Ergebnistabelle fest, mit [G%127][i] durch die Tabelle

    public static BigInteger w(int G,int i){

        return  ((G < 0) ? BigInteger.ZERO : // keine negativen Werte fuer G, null
                (i == 0) ? BigInteger.ONE : // gibt fuer 1er-muenzen 1 zurueck
                        (tab[G%77][i]!=null) ? tab[G%77][i] : // Wert bereits berechnet, gib Feld zurueck
                                (tab[G%77][i] = w(G,i-1).add(w(G - betrag[i], i)))); // zwei rekursive Aufrufe

    }

    // zeigt Inhalt der Ergebnistabelle an
    public static void listTab(){
        for(int z=0; z<7; z++){
            for(int s=0; s<127; s++){
                System.out.print("["+tab[s][z]+"]");
            }
            System.out.print("\n");
        }
    }


    public static void main(String[] args) {

        int G=5;

        System.out.println("Der Betrag von "+G+" kann auf "+w(G,6)+" verschiedene Arten ausgegeben werden.\n");



    }

}