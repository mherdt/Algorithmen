package algo4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Author: Michael Herdt(191618), Jannis Scholz(191481), Markus Moser(191598)
 * Date: 23.04.2017 (last changes)
 *
 * Algorithmen - Aufgabe 4.2 - PathNodes
 *
 */

public class PathNodes {
    private static boolean allPossiblePathsUpToN;
    private static ArrayList<String> final_paths;

    private static void get_next_paths(int x, int y, int n, int max_x, int max_y, String p, String last_direction) {

        //  pass if the x and y value is not the smaller than 0 or bigger than n and not that high that the point (n, 0) can not be reached and not over the diagonale
        if (x >= 0 && y >= 0 && x + y <= n) {

            //System.out.println("x:"+x+" y:"+y+" n:"+n+" p:"+p);

            if (x == max_x && y == max_y && p.length() > 0){
                final_paths.add(p);
                return;
            }

            // get the two last directions of the path
            String last_dir = "";
            if (last_direction.length() >= 2){
                last_dir = last_direction.substring(last_direction.length() - 2);
            }

            // go top if the current point is on the main diagonal or above
            if (y >= x) {
                String p_new = "" + p + x + "," + (y + 1) + " ";
                get_next_paths(x, (y+1), n, max_x, max_y, p_new, last_direction+"_t");
            }

            //  go right if the current point is on the main diagonal or below
            if (x >= y) {
                String p_new = "" + p + (x+1) + "," + y + " ";
                get_next_paths((x+1), y, n, max_x, max_y, p_new, last_direction+"_r");
            }

            // go top right
            {
                String p_new = "" + p + (x+1) + "," + (y+1) + " ";
                get_next_paths((x+1), (y+1), n, max_x, max_y, p_new, last_direction+"tr");
            }

            //  go top left if last direction was not down right
            if (!last_dir.equals("dr")) {
                String p_new = "" + p + (x-1) + "," + (y+1) + " ";
                get_next_paths((x-1), (y+1), n, max_x, max_y, p_new, last_direction+"tl");
            }

            // go down right if last direction was not top left
            if (!last_dir.equals("tl")) {
                String p_new = "" + p + (x+1) + "," + (y-1) + " ";
                get_next_paths((x+1), (y-1), n, max_x, max_y, p_new, last_direction+"dr");
            }
        }
    }

    public static void main(String[] args){
        // init global variables
        final_paths = new ArrayList<String>();
        allPossiblePathsUpToN = false;

        // read in target coordinates form cmd input
        Coordinate target = readTargetvalueFromCmd();

        if(allPossiblePathsUpToN){

            for(int i = 0; i <= (target.x + 1); i++)
            {
                final_paths = new ArrayList<String>();
                get_next_paths(0, 0, i, target.x, target.y, "", "");
                System.out.println("x/n=" + i +": " + final_paths.size());
            }

        }
        else{

            get_next_paths(0, 0, target.x + target.y, target.x, target.y, "", "");
            System.out.println("Anzahl der Pfade: "+final_paths.size());
            int counter=0;
            for(String path: final_paths)
            {
                counter += (path.length()/4)+1;
                //System.out.println("Length " + ((path.length()/4) +1) + ": " + path);
            }
            System.out.println("Anzahl der Knotenpunkte: "+counter);
        }


    }

    private static Coordinate readTargetvalueFromCmd(){
        try{
            Scanner s = new Scanner(System.in);
            System.out.print("Please enter (n) for the point on the diagonale (n/n): ");
            int cmdInput = s.nextInt();
            System.out.println("");

            if (cmdInput < 0) throw new NumberFormatException();

            return new Coordinate(cmdInput, cmdInput);

        }catch(NumberFormatException nfe){
            System.err.println("You've entered invalid coordinates..");
            System.out.println("Please try again..");

            // recurse if user is to dumb to enter values correctly
            return readTargetvalueFromCmd();
        }
    }
}


//simple object that holds a X and Y coordinate (e.g. point)
class Coordinate {
    public int x;
    public int y;

    public Coordinate(int a, int b) {
        x = a;
        y = b;
    }
}