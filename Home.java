


import java.util.*;


class Pair {
    int weight;
    int benefit;

    public Pair(int l, int r) {

        weight = l;
        benefit = r;
    }
}

class knapsack {

    //    int nTests;
    int nItems;
    int size;
    Pair[] items;
    int popSize;
    int[][] theBigArray;
    Map<Integer, Pair> map;
    int[] fitness;
    int biggest1, biggest2;  //biggest indices in bigarray
    int[] chrom1, chrom2;   //the offsprings

    int[][][] theBigArrayIeration;
    int nIterations;


    public knapsack() {
    }

    public knapsack(int n) {
        nIterations = n;
    }


    public void execute() {

        Scanner s = new Scanner(System.in);


        theBigArray = new int[nItems][popSize];
        nItems = s.nextInt();
        size = s.nextInt();
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


    public void printArray() {

        System.out.println("Printing population");
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < nItems; j++) {
                System.out.print(theBigArray[i][j] + " ");
            }
            System.out.println();


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


    public int getRandomNumber(int n) {
        return (int) (Math.random() * n);
    }

    public void selection() {

        biggest1 = 0;
        biggest2 = 1;

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

    }

    public void swap(int a, int b) {
        int temp = a;
        a = b;
        b = a;
    }


    public void crossing() {


        int randomGene = getRandomNumber(nItems);

        chrom1 = new int[nItems];
        chrom2 = new int[nItems];

        for (int i = 0; i < nItems; i++) {
            chrom1[i] = theBigArray[biggest1][i];
            chrom2[i] = theBigArray[biggest2][i];
        }

//          This reduces solution accuracy... so it was commented!
//        double doCrossing = Math.random();
//        if(doCrossing<0.4)
//            return;

        int c = randomGene;
        while (c < nItems) { //swapping
            int temp = chrom1[c];
            chrom1[c] = chrom2[c];
            chrom2[c] = temp;
            c++;
        }

    }

    public void mutate() {

//          This reduces solution accuracy... so it was commented!
//        double doCrossing = Math.random();
//        if(doCrossing<0.4)
//            return;

        int geneToFlip = getRandomNumber(nItems);

        if (chrom1[geneToFlip] == 0)
            chrom1[geneToFlip] = 1;
        else chrom1[geneToFlip] = 0;

        if (chrom2[geneToFlip] == 0)
            chrom2[geneToFlip] = 1;
        else chrom2[geneToFlip] = 0;
    }

    public void reInsert() {

        int randomReplace = getRandomNumber(popSize);
        int randomReplace2 = getRandomNumber(popSize);

        while(randomReplace2 == randomReplace){
            randomReplace2 = getRandomNumber(popSize);
        }

        for (int i = 0; i < nItems; i++) {
            theBigArray[randomReplace][i] = chrom1[i];
            theBigArray[randomReplace2][i] = chrom2[i];
        }


    }


    public void initializeIterationHistory(int nTests) {

        iterationResults = new Vector<Integer>();
    }

    public void addToHistory(int index, int highestVal) {

        iterationResults.add(highestVal);
    }

    Vector<Integer> iterationResults;

}

class FitnessPair {

    int[] bigArray;
    int fitness;

    public FitnessPair(int[] arr, int f) {
        bigArray = arr;
        fitness = f;
    }

    public void print() {
        System.out.print("(");
        for (int i = 0; i < bigArray.length; i++) {
            System.out.print(bigArray[i]+" ");
        }
        System.out.print(", " +fitness);
        System.out.println(")");

    }

}

public class Home {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int nTests = s.nextInt();
        int nIterations = 250000;
        int[] results = new int[nTests];
        knapsack[] k = new knapsack[nTests];

        for (int i = 0; i < nTests; i++) {
            int maxFitnessForThisTest = 0;
            k[i] = new knapsack(nIterations);
            k[i].initializeIterationHistory(nTests);  //redundant but works
            k[i].execute(); //initialize-- initial population
            k[i].calculateFitness();



            for (int j = 0; j < nIterations; j++) {
                k[i].selection();
                k[i].crossing();
                k[i].mutate();
                k[i].reInsert();
                k[i].calculateFitness();
                k[i].addToHistory(j, k[i].fitness[k[i].biggest1]);

            }

            maxFitnessForThisTest = 0;
            for (int t : k[i].iterationResults) //This loop gets the highest fitness for this test
            {
                if (t > maxFitnessForThisTest)
                    maxFitnessForThisTest = t;
            }
            results[i] = maxFitnessForThisTest;

            System.out.println("Case " + i + " :  " + results[i]);

        }


    }


}