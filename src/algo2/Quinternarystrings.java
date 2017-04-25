package algo2;

import java.io.InputStreamReader;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Author: Michael Herdt(191618), Constantin Küssner(191619), Thomas Stang(191712)
 * Date: 02.04.2017 (last changes)
 *
 * Algorithmen - Aufgabe 2.1 - Quinternarystrings
 *
 * This program calculate the number of Quinternarystrings
 * with the characters (0, 1, 2, 3, 4)
 */

public class Quinternarystrings {

    //initialize variable and Big Integer numbs with an value of 1
    static int n;
    BigInteger a[] = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };

    //create an object and askUser() for an number n.
    //initialize an variable i in a for-loop which will counted until it have the same value as n
    //start method counter() and adding()
    public static void main(String[] args) throws IOException{
        Quinternarystrings Quinternarystrings = new Quinternarystrings();
        Quinternarystrings.askUser();
        for (int i = 1; i <= n; i++) {
            System.out.println("a(" + (i) + ") = " + Quinternarystrings.counter());
            Quinternarystrings.adding();
        }
    }

    //ask User for the number
    public int askUser() throws IOException {
        System.out.println("Input a number: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        n = Integer.parseInt(input);
        return n;
    }

    //adding the numbers to the BigInteger
    public void adding() {
        BigInteger a0 = a[0], a1 = a[1], a2 = a[2], a3 = a[3], a4 = a[4];
        a[0] = a0.add(a2.add(a3));
        a[1] = a0.add(a1.add(a3));
        a[2] = a0.add(a1.add(a2));
        a[3] = a0.add(a1.add(a2.add(a3)));
        a[4] = a0.add(a1.add(a2.add(a3.add(a4))));
    }

    //count the number of numbers in BigInteger a[]
    public BigInteger counter() {
        BigInteger counter = BigInteger.ZERO;
        for (int i = 0; i < a.length; i++) {
            counter = counter.add(a[i]);
        }
        return counter;
    }
}
