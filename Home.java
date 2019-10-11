

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

class knapsack {

    int nTests;
    int nItems;
    int size;
    Pair[] items;
    int popSize;
    int[][] theBigArray;
    Map<Integer, Pair> map;
    int[] fitness;
    int biggest1, biggest2;


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
//            popSize = (int) (Math.random() * 10 + 2);  //This is the initial population size
            popSize = 10;
            items = new Pair[nItems];
            map = new HashMap<Integer, Pair>();

            for (int j = 0; j < nItems; j++) { //This loop is for the current (randomly generated) popSize
                int x = s.nextInt();
                int y = s.nextInt();
                items[j] = new Pair(x, y);
                map.put(j, items[j]);

            }
            this.theBigArray = initializeBigArray();

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

    public void printArray() {

        System.out.println("Printing population");
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < nItems; j++) {
                System.out.print(theBigArray[i][j] + " ");
            }
            System.out.println();


        }
    }

    public void printMap() {
        System.out.println("Printing map");
        for (int i = 0; i < map.size(); i++) {
            Pair p = (Pair) map.get(i);
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

        fitness = new int[popSize];  //  --->TEST INITIAL VALUES IF ERROR HAPPENS!!
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

        return 0;
    }

    public void printFitness() {

        System.out.println("Printing fitness");

        for (int i : fitness)
            System.out.println(i);
    }

    //for swapping 2 rows in theBigArray
    public void swapBigArray(int x1, int x2) {

        int[] temp = new int[nItems];
        for (int j = 0; j < temp.length; j++) {     //this loop is to set theBigArr[i] into temp
            temp[j] = theBigArray[x1][j];
        }


        //arr[j-1] == theBigArray[x1][j]
        //arr[j] ==   theBigArray[x2][j]

        //now for the swap
        for (int j = 0; j < nItems; j++) {

            theBigArray[x1][j] = theBigArray[x2][j];
            theBigArray[x2][j] = temp[j];
        }


    }

    public int getRandomNumber(int n) {
        return (int) (Math.random() * n);
    }

    public void selection() {

        biggest1 = 0;
        biggest2 = 0;

        for (int i = 0; i < fitness.length; i++) {

            if (fitness[i] > fitness[biggest1]) {
                biggest1 = i;
            }
        }

        for (int i = 0; i < fitness.length; i++) {
            if (fitness[i] > fitness[biggest2] && (i != biggest1)) {
                biggest2 = i;
            }
        }

        System.out.println("Biggest 1 value = " + fitness[biggest1] + " at " + biggest1);
        System.out.println("Biggest 2 value = " + fitness[biggest2] + " at " + biggest2);
        System.out.println();

        //now I have the 2 biggest fitness indices
    }

    public int calculateFitnessChromosome(int[] arr) {

        int chromFitness = 0;
        int chromWeight = 0;
        for (int i = 0; i < arr.length; i++) {

            chromFitness += (map.get(i).benefit) * arr[i];
            chromWeight += (map.get(i).weight) * arr[i];
        }

        if (chromWeight > size)
            return -1;

        return chromFitness;
    }

    public void swap(int a, int b) {
        int temp = a;
        a = b;
        b = a;
    }


    public void crossing() {

        int randomGene = getRandomNumber(nItems);

        int[] chrom1 = new int[nItems];
        int[] chrom2 = new int[nItems];

        for (int i = 0; i < nItems; i++) {
            chrom1[i] = theBigArray[biggest1][i];
            chrom2[i] = theBigArray[biggest2][i];
        }


        System.out.println("Chrom1 before swapping ");
        for (int i : chrom1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("Chrom2 before swapping ");
        for (int i : chrom2) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("RandomGene : "+randomGene);
        System.out.println();
        int c = randomGene;
        while (c < nItems) {
            System.out.println(c);
//            swap(chrom1[c], chrom2[c]);
            int temp = chrom1[c];
            chrom1[c] = chrom2[c];
            chrom2[c] = temp;
            c++;
        }

        System.out.println();
        System.out.println("Chrom1 after swapping ");
        for (int i : chrom1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("Chrom2 after swapping ");
        for (int i : chrom2) {
            System.out.print(i + " ");
        }
    }

    public void mutate() {

    }

    public void reInsert() {

    }

}


public class Home {

    public static void main(String[] args) {

        knapsack k = new knapsack();
        k.execute();
        k.printArray();
        k.printMap();

        //now we have initialized our data
        //and we want to start crossing over its members
        k.calculateFitness();
        k.printFitness();
        System.out.println();
        System.out.println("NOW FOR SELECTION");
        System.out.println();

        int numberOfIterations = 1;
        int c = 0;
        while (c < numberOfIterations) {
            k.selection();
            k.crossing();
            c++;

        }

    }
}