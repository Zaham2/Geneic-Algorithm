

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
    int[][] theBigArray;
    Map<Integer, Pair> map;


    class Pair {
        int weight;
        int benefit;

        public Pair(int l, int r) {

            weight = l;
            benefit = r;
        }
    }

    public knapsack() {
    }


    public void execute() {

        Scanner s = new Scanner(System.in);
        nTests = s.nextInt();

        for (int i = 0; i < nTests; i++) { //This loop for the number of test cases

            theBigArray = new int[nItems][popSize];
            nItems = s.nextInt();
            size = s.nextInt();
            popSize = (int) (Math.random() * 10 + 2);
            items = new Pair[nItems];
            map = new HashMap<Integer, Pair>();

            for (int j = 0; j < nItems; j++) { //This loop is for the current (randomly generated) popSize
                int x = s.nextInt();
                int y = s.nextInt();
                items[j] = new Pair(x, y);
                map.put(j, items[j]);

            }
            this.theBigArray = initializeBigArray();
            printArray(theBigArray);
            printMap(map);
            calculateFitness();
        }

    }

    public void generateRandomPopulation() {

        Scanner s = new Scanner(System.in);
        popSize = (int) (Math.random() * 100 + 1);
        for (int j = 0; j < nItems; j++) { //This loop is for the current (randomly generated) popSize
            int x = s.nextInt();
            int y = s.nextInt();
            items[j] = new Pair(x, y);
            map.put(j, items[j]);

        }
    }

    public void printArray(int[][] arr) {

        System.out.println("Printing population");
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
            System.out.println(i + " :  " + p.weight + " " + p.benefit);
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

    public boolean isValid(int num) {
        if (num <= size)
            return true;
        return false;
    }

    public int calculateFitness() {

        int[] fitness = new int[popSize];  //  --->TEST INITIAL VALUES IF ERROR HAPPENS!!
        int[] weights = new int[popSize];

        for (int i = 0; i < popSize; i++) {
            fitness[i] = 0;
            weights[i] = 0;
        }

        for (int i = 0; i < popSize; i++) {

            for (int j = 0; j < nItems; j++) {

                fitness[i] += this.map.get(j).benefit * this.theBigArray[i][j];
                weights[i] += map.get(j).weight * theBigArray[i][j];
            }
            if (!isValid(weights[i]))
                fitness[i] = -1;
        }

        this.printFitness(fitness);
        return 0;
    }

    public void printFitness(int[] arr) {

        System.out.println("Printing fitness");

        for (int i : arr)
            System.out.println(i);
    }

}

public class Home {

    public static void main(String[] args) {

        knapsack k = new knapsack();
        k.execute();
        //now we have initialized our data
        //and we want to start crossing over its members


    }
}