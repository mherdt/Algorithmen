package algo4;

import java.util.Scanner;

    /**
     * Created by michael on 11.04.17.
     */
    public class PermOdds {

        private static int[] a;
        private static int[] firstArray;
        static int n;
        static int maxIndex;
        static int counter;

        public static void main(String[] args) {
            PermOdds permOdds = new PermOdds();
            Scanner s = new Scanner(System.in);
            System.out.println("Input (n):");
            n = s.nextInt();
            if (n <= 0) {
                System.out.println("(n) muss Positiv sein.");
            }
            permOdds.createArray(n);
            permOdds.createPerms(0);
            System.out.println("Es gab genau "+ counter +" Permutationen der verlangten Art.");
        }

        void createFirstArray(int n) {
            firstArray = new int[n];
            maxIndex = n - 1;
            for (int i = 0; i <= maxIndex; i++) firstArray[i] = i + 1;
        }

        void createArray(int n) {
            createFirstArray(n);
            a = new int[n];
            maxIndex = n - 1;
            for (int i = 0; i <= maxIndex; i++) a[i] = i + 1;
        }

        void createPerms(int i) {
            if (i >= maxIndex) {
                checkPerm();
            } else {
                for (int j = i; j <= maxIndex; j++) {
                    changeNumbs(i, j);
                    createPerms(i + 1);
                }
                int puffer = a[i];
                System.arraycopy(a, i + 1, a, i, maxIndex - i);
                a[maxIndex] = puffer;
            }
        }

        void changeNumbs(int i, int j) {
            if (i != j) {
                int puffer = a[i];
                a[i] = a[j];
                a[j] = puffer;
            }
        }

        void checkPerm() {
            boolean out = true;
                for (int i = 1; i < a.length; i++) {
                    if ((((a[i - 1] + firstArray[i - 1]) % 2) != 0) && ((a[i] + firstArray[i] % 2) == 0) || ((((a[i -1 ] + firstArray[i - 1]) % 2) == 0) && (((a[i] + firstArray[i]) % 2) != 0)) || ((a[i] + firstArray[i]) % 2) == 0) {
                    } else {
                        out = false;
                        i = a.length;
                    }
                }
            if (out) {
                    System.out.print("[ ");
                for (int index = 0; index < a.length; index++) {
                    System.out.print(a[index] + " ");
                }
                System.out.println("]");
                counter++;
            }
        }
    }


