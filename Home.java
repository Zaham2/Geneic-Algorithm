

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class knapsack {

    int nTests;
    int nItems;
    int size;
    Pair[] items;
    int popSize;


    class Pair {
        int weight;
        int benefit;

        public Pair(int l, int r) {

            weight = l;
            benefit = r;
        }
    }

    public knapsack() {

        Scanner s = new Scanner(System.in);
        nTests = s.nextInt();
        execute();
    }

    public void execute() {

        Scanner s = new Scanner(System.in);
        for (int i = 0; i < nTests; i++) { //This loop for the number of test cases

            int[][] theBigArray = new int[nItems][popSize];
            nItems = s.nextInt();
            size = s.nextInt();
            popSize = (int) (Math.random() * 10 + 1);
            items = new Pair[nItems];
            Map<Integer, Pair> map = new HashMap<Integer, Pair>();

            for (int j = 0; j < nItems; j++) { //This loop is for the current (randomly generated) popSize
                int x = s.nextInt();
                int y = s.nextInt();
                items[j] = new Pair(x, y);
                map.put(j, items[j]);

            }
            theBigArray = initializeBigArray();
            initPop(theBigArray);
            printArray(theBigArray);
            printMap(map);
        }

    }

    public void printArray(int[][] arr) {

        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < nItems; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();


        }
    }

    public void printMap(Map m) {
        System.out.println("Printing map");
        for (int i = 0; i < m.size(); i++) {
            Pair p = (Pair) m.get(i);
            System.out.println(p.weight + " " + p.benefit);
        }

    }


    public int[][] initializeBigArray() {

        int[][] theArray = new int[popSize][nItems];
        for (int i = 0; i < popSize; i++) {

            for (int j = 0; j < nItems; j++) {

                theArray[i][j] = (int) (Math.random() * 100 + 1) % 2;

            }
        }

        return theArray;
    }

    // initlize the array contains pop size and number of items --> number of genes in chroms
    public void initPop(int[][] theArray) {

    }


}

public class Home {

    public static void main(String[] args) {

        knapsack k = new knapsack();
        //now we have initialized our data
        //we have a population (theBigArray[popSize][nItems])
        //  ||===> CORRECT THE ABOVE ARRAY BEFORE CROSSING
        //and we want to start crossing over its members

    }
}