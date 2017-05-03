package algo6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Class DLXPentominoXYTM represents a matrix element of the cover matrix with value 1 links go to up down
 * left right neigbors, and column header can also be used as colm header or root of column headers
 * matrix is sparsely coded try to do all operations very efficiently see:
 * http://en.wikipedia.org/wiki/Dancing_Links http://arxiv.org/abs/cs/0011047
 *
 */
class DLXPentominoXYTM { // represents 1 element or header

    DLXPentominoXYTM C; // reference to column-header
    DLXPentominoXYTM L, R, U, D; // left, right, up, down references
    int posH;
    int posV;
    static int indexLength;
    static DLXPentominoXYTM[] headers;
    static int matrixLine;
    static int maxNumber;
    static int n;

    DLXPentominoXYTM() {
        C = L = R = U = D = this;
    } // supports circular lists


    /**
     * search tries to find and count all complete coverings of the DLX matrix. Is a recursive,
     * depth-first, backtracking algorithm that finds all solutions to the exact cover problem encoded
     * in the DLX matrix. each time all columns are covered, static long cnt is increased
     *
     * @param int k: number of level
     *
     */
    static BigInteger count = new BigInteger("0");
    static int cnt;
    static DLXPentominoXYTM h;

    public static void search(int k) { // finds & counts solutions
        if (h.R == h) {
            count = count.add(BigInteger.ONE);
            // cnt++;
            return;
        } // if empty: count & done
        DLXPentominoXYTM c = h.R; // choose next column c
        cover(c); // remove c from columns
        for (DLXPentominoXYTM r = c.D; r != c; r = r.D) { // forall rows with 1 in c
            for (DLXPentominoXYTM j = r.R; j != r; j = j.R) // forall 1-elements in row
                cover(j.C); // remove column
            search(k + 1); // recursion
            for (DLXPentominoXYTM j = r.L; j != r; j = j.L) // forall 1-elements in row
                uncover(j.C); // backtrack: un-remove
        }
        uncover(c); // un-remove c to columns
    }

    /**
     * cover "covers" a column c of the DLX matrix column c will no longer be found in the column list
     * rows i with 1 element in column c will no longer be found in other column lists than c so
     * column c and rows i are invisible after execution of cover
     *
     * @param c: header element of column that has to be covered
     *
     */
    public static void cover(DLXPentominoXYTM c) { // remove column c
        c.R.L = c.L; // remove header
        c.L.R = c.R; // .. from row list
        for (DLXPentominoXYTM i = c.D; i != c; i = i.D) // forall rows with 1
            for (DLXPentominoXYTM j = i.R; i != j; j = j.R) { // forall elem in row
                j.D.U = j.U; // remove row element
                j.U.D = j.D; // .. from column list
            }
    }

    /**
     * uncover "uncovers" a column c of the DLX matrix all operations of cover are undone so column c
     * and rows i are visible again after execution of uncover
     *
     * @param c: header element of column that has to be uncovered
     *
     */
    public static void uncover(DLXPentominoXYTM c) {// undo remove col c
        for (DLXPentominoXYTM i = c.U; i != c; i = i.U) // forall rows with 1
            for (DLXPentominoXYTM j = i.L; i != j; j = j.L) { // forall elem in row
                j.D.U = j; // un-remove row elem
                j.U.D = j; // .. to column list
            }
        c.R.L = c; // un-remove header
        c.L.R = c; // .. to row list
    }

    public static void main(String[] args) {
      System.out.println("Input n: ");
      Scanner s = new Scanner(System.in);
      int pufferN = s.nextInt();

      for (n = 1; n <= pufferN; n++) {
        cnt = 0; // set counter for solutions to zero
        h = new DLXPentominoXYTM(); // create header
        h.posH = 0;
        h.posV = 0;
        count = BigInteger.ZERO;
        matrixLine = 1;
        maxNumber = n * 6;

        addHeader(maxNumber);

        /**
         * calculate all figure positions
         */
        createMono();
        calcCross();
        calcT_UP();
        calcT_DOWN();
        calcT_LEFT();
        calcT_RIGHT();
        calcX();
        calcY_R1();
        calcY_R2();
        calcY_R3();
        calcY_R4();
        calcY_R5();
        calcY_R6();
        calcY_R7();
        calcY_R8();

        search(0);
        System.out.println("a(" + n + ") = " + count);
      }
    }

    /**
     * create headers
     */
    public static void addHeader(int n) {
        indexLength = n;
        headers = new DLXPentominoXYTM[n + 1];
        headers[0] = h;
        DLXPentominoXYTM tempNode;
        int x = 0;
        for (int i = 0; i < n; i++) {
            DLXPentominoXYTM node = new DLXPentominoXYTM();
            node.posH = ++x; // set index to header
            node.posV = 0; // set vertical position to 0 to signal header
            headers[x] = node;
            if (h.L == h) {
                // connect header to node
                h.L = node;
                h.R = node;
                // connect node to header
                node.L = h;
                node.R = h;
            } else {
                tempNode = h.L; // goto last header
                tempNode.R = node; // connect old last node to new node
                node.L = tempNode; // connect new node to old last node
                node.R = h; // connect last new node to header
                h.L = node; // connect header to last new node
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + posH;
        result = prime * result + posV;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DLXPentominoXYTM other = (DLXPentominoXYTM) obj;

        if (posH != other.posH) {
            return false;
        } else if (posV != other.posV) {
            return false;
        }
        return true;
    }

    /**
     * go to header position
     *
     * @param posH goto horizontal position
     * @return header node
     */
    private static DLXPentominoXYTM gotoHeaderIndex(int posH) {
        return headers[posH];
    }

    /**
     * here we are calculating all the figures
     */
    public static void calcCross() {
        int a = 2;
        int b = 7;
        int c = 8;
        int d = 9;
        int e = 14;

        List<Integer> figure = Arrays.asList(a, b, c, d, e);
        int width = 3;
        int height = 3;
        calculateFiguresPosition(figure, height, width);

    }

    public static void calcT_UP() {
        calculateFiguresPosition(Arrays.asList(1, 7, 8, 9, 13), 3, 3);
    }

    public static void calcT_DOWN() {
        calculateFiguresPosition(Arrays.asList(3, 7, 8, 9, 15), 3, 3);
    }

    public static void calcT_LEFT() {
        calculateFiguresPosition(Arrays.asList(1, 2, 3, 8, 14), 3, 3);
    }

    public static void calcT_RIGHT() {
        calculateFiguresPosition(Arrays.asList( 2, 8, 13, 14, 15), 3, 3);
    }


  /**
   *
   */
  public static void calcX() {
        calculateFiguresPosition(Arrays.asList(2, 7, 8, 9, 14), 3, 3);
    }

  /**
   *
   */
  public static void calcY_R1() {
        calculateFiguresPosition(Arrays.asList(1, 2, 3, 4, 8), 2, 2);
    }

    public static void calcY_R2() {
        calculateFiguresPosition(Arrays.asList(2, 7, 8, 9, 10), 2, 2);
    }

    public static void calcY_R3() {
        calculateFiguresPosition(Arrays.asList(1, 7, 13, 14, 19), 4, 4);
    }

    public static void calcY_R4() {
        calculateFiguresPosition(Arrays.asList(2, 7, 8, 14, 20), 4, 4);
    }

    public static void calcY_R5() {
        calculateFiguresPosition(Arrays.asList(1, 7, 8, 13, 19), 4, 4);
    }

    public static void calcY_R6() {
        calculateFiguresPosition(Arrays.asList(2, 8, 13, 14, 20), 4, 4);
    }

    public static void calcY_R7() {
      calculateFiguresPosition(Arrays.asList(1, 2, 3, 4, 9), 2, 2);
    }

    public static void calcY_R8() {
      calculateFiguresPosition(Arrays.asList(3, 7, 8, 9, 10), 2, 2);
    }

    /**
     * newer more robust mono calculation, but might be overkill
     */
    public static void createMono() {
        for (int i = 0; i < maxNumber; i++) {
            DLXPentominoXYTM node = new DLXPentominoXYTM();
            node.posH = i + 1;
            node.posV = matrixLine;
            // System.out.println("add node: posV=" + node.posV + " posH=" + node.posH);
            matrixLine++;

            node.C = gotoHeaderIndex(node.posH);
            node.U = gotoHeaderIndex(node.posH).U;
            gotoHeaderIndex(node.posH).U.D = node;
            gotoHeaderIndex(node.posH).U = node;
            node.D = node.C;
        }
    }

    /**
     * calculation algo for the figures
     *
     * @param figures
     * @param downShifts
     * @param width
     */
    private static void calculateFiguresPosition(List<Integer> figures, int downShifts, int width) {
        int shiftsRight = n - width;
        if (shiftsRight > n || shiftsRight < 0) {
          // System.out.println("no positions, cant fit figure");
          return;
        }
        insertFigure(figures);
        for (int i = 0; i < downShifts; i++) {
            List<Integer> plusSixFigures = new ArrayList<>(figures);
            shiftRight(shiftsRight, plusSixFigures);
            shiftDown(figures);
        }
        shiftRight(shiftsRight, figures);
    }

    private static void shiftRight(int shiftsRight, List<Integer> plusSixFigures) {
        for (int g = 0; g < shiftsRight; g++) {
            shiftOneRight(plusSixFigures);
        }
    }

    private static void insertFigure(List<Integer> figures) {
        DLXPentominoXYTM[] array = new DLXPentominoXYTM[5];
        int arrayIndex = 0;
        for (Integer integer : figures) {
            array[arrayIndex] = createNode(matrixLine, integer);
            arrayIndex++;
        }
        createLine(array);
        matrixLine++;
    }

    private static void shiftDown(List<Integer> figures) {
        DLXPentominoXYTM[] array = new DLXPentominoXYTM[5];
        int arrayIndex = 0;
        for (int j = 0; j < figures.size(); j++) {
            int elementPlusOne = figures.get(j) + 1;
            figures.set(j, elementPlusOne);
            // addNode(matrixLine, elementPlusOne);
            array[arrayIndex] = createNode(matrixLine, elementPlusOne);
            arrayIndex++;
        }
        createLine(array);
        matrixLine++;
    }

    private static void shiftOneRight(List<Integer> plusSixFigures) {
        DLXPentominoXYTM[] array = new DLXPentominoXYTM[5];
        int arrayIndex = 0;
        for (int j = 0; j < plusSixFigures.size(); j++) {
            Integer current = plusSixFigures.get(j);
            current += 6;
            plusSixFigures.set(j, current);

            // addNode(matrixLine, current);

            array[arrayIndex] = createNode(matrixLine, current);
            arrayIndex++;
        }
        createLine(array);
        matrixLine++;
    }

    /**
     * node is created with this method
     *
     * @param posV
     * @param posH
     * @return
     */
    private static DLXPentominoXYTM createNode(int posV, int posH) {
        // System.out.println("add node: posV=" + posV + " posH=" + posH);
        DLXPentominoXYTM node = new DLXPentominoXYTM();
        node.posV = posV;
        node.posH = posH;
        return node;
    }

    /**
     * creates a line for the five nodes and connects them.
     *
     * @param array
     */
    private static void createLine(DLXPentominoXYTM[] array) {
        for (int i = 0; i < array.length; i++) {
            /**
             * chain up down
             */
            array[i].C = gotoHeaderIndex(array[i].posH);
            array[i].U = gotoHeaderIndex(array[i].posH).U;
            gotoHeaderIndex(array[i].posH).U.D = array[i];
            array[i].D = gotoHeaderIndex(array[i].posH);
            gotoHeaderIndex(array[i].posH).U = array[i];
            if (i == 0) {
                /**
                 * chain left right for first element
                 */
                array[i].L = array[array.length - 1];
                array[i].R = array[i + 1];
            } else if (i == array.length - 1) {
                /**
                 * chain left right for last element
                 */
                array[i].L = array[i - 1];
                array[i].R = array[0];
            } else {
                /**
                 * chain left right for other elements
                 */
                array[i].L = array[i - 1];
                array[i].R = array[i + 1];
            }
        }
    }
}