package algo2;

import java.util.ArrayList;

/**
 * Author: Michael Herdt(191618), Constantin Küssner(191619), Thomas Stang(191712)
 * Date: 02.04.2017 (last changes)
 *
 * Algorithmen - Aufgabe 2.2 - MaxProduct
 *
 * This program is looking for the biggest product of a row of numbers
 */

public class MaxProduct {

    //Initialize an ArrayList for the row of numbers and the variables
    ArrayList<Double>  numbs = new ArrayList<>();
    double MaxProduct = 0;
    double oldMaxProduct = 0;
    int firstNumb = 1;

    //the main method create an object and start the methods addNumbs() to add the numbers to the ArrayList
    //and getMaxProduct() to calculate the max product of the row of numbers.
    //With a println its show the result
    public static void main(String[] args){
        MaxProduct MaxProduct = new MaxProduct();
        MaxProduct.addNumbs();
        MaxProduct.getMaxProduct();
        System.out.println("The maximal Product is " + MaxProduct.getMaxProduct());
    }

    //adding the numbers to the ArrayList "numbs"
    public void addNumbs() {
        numbs.add(0,10.0);
        numbs.add(1,1.0);
        numbs.add(2,10.0);
        numbs.add(3,0.0001);
        numbs.add(4,2000000.0);
    }

    //algorithm to look for the maxProduct of a row of numbers
    public double getMaxProduct() {
        MaxProduct = numbs.get(0);
        for (int i = firstNumb; i < numbs.size(); i++){
            for (int ii = i; ii < numbs.size();ii++ ){
                MaxProduct = MaxProduct * numbs.get(ii);
                if(MaxProduct > oldMaxProduct){
                    oldMaxProduct = MaxProduct;
                }
                else if (MaxProduct > oldMaxProduct){
                    System.out.println(numbs.get(ii));
                    oldMaxProduct = MaxProduct;
                }
            }
            MaxProduct = numbs.get(i);
        }
        return oldMaxProduct;
    }

}