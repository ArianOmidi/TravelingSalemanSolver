import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TSP {
    private final int numOfCities, seed;
    private final double[][] cities, distances;

    private int[] optimalTour, randomTour, greedyTour;
    private double optimalCost, randomCost, greedyCost;
    private boolean solved;

    public TSP(int numOfCities, int seed){
        this.numOfCities = numOfCities;
        this.seed = seed;

        this.cities = new double[numOfCities][2];
        this.distances = new double[numOfCities][numOfCities];
        this.solved = false;
        this.optimalCost = Double.MAX_VALUE;


        // Set points
        Random r = new Random(seed);
        for (int i=0; i < numOfCities; i++){
            this.cities[i][0] = r.nextDouble();
            this.cities[i][1] = r.nextDouble();
        }

        // Set distances
        for (int i=0; i < numOfCities; i++){
            for(int j=0; j < numOfCities; j++){
                this.distances[i][j] = Math.sqrt( Math.pow(cities[i][0] - cities[j][0], 2)
                        + Math.pow(cities[i][1] - cities[j][1], 2) );
            }
        }
    }


    /* --- Getters --- */

    public double[][] getCities() {
        return cities;
    }

    public double[][] getDistances() {
        return distances;
    }

    public int[] getOptimalSolution() {
        return optimalTour;
    }

    public double getOptimalCost() {
        return optimalCost;
    }

    public double getRandomCost() {
        return randomCost;
    }

    public double getGreedyCost() {
        return greedyCost;
    }

    public boolean isSolved() {
        return solved;
    }


    /* --- Optimal Solution --- */

    public void findSolutionByForce(){
        int[] initTour = new int[numOfCities];

        for (int i = 0; i < numOfCities; i++) {
            initTour[i] = i;
        }

        bruteForceRecursive(numOfCities, initTour);
        solved = true;
    }

    public void bruteForceRecursive(int n, int[] path) {
        if(n == 1) {
            double costOfPath = findCost(path);
            if (costOfPath < this.optimalCost){
                this.optimalCost = costOfPath;
                this.optimalTour = Arrays.copyOf(path, path.length);
            }
        } else {
            for(int i = 0; i < n-1; i++) {
                bruteForceRecursive(n - 1, path);
                if(n % 2 == 0) {
                    Utils.swap(path, i, n-1);
                } else {
                    Utils.swap(path, 0, n-1);
                }
            }
            bruteForceRecursive(n - 1, path);
        }
    }


    /* --- Random Tours --- */

    public void generateRandomTour(){
        ArrayList<Integer> tour = new ArrayList<>();
        this.randomTour = new int[numOfCities];

        for (int i = 0; i < numOfCities; i++)
            tour.add(i);
        java.util.Collections.shuffle(tour, new Random(seed));

        for(int i = 0; i < tour.size(); i++)
            randomTour[i] = tour.get(i);

        randomCost = findCost(randomTour);
    }


    /* --- Greedy Solution --- */

    public void findGreedySolution(){
        if (randomTour == null){
            generateRandomTour();
        }

        greedyTour = findGreedySolutionRec(randomTour, randomCost);
        greedyCost = findCost(greedyTour);
    }

    private int[] findGreedySolutionRec(int[] tour, double minCost){
        int[][] neighbors = get2OptNeighbors(tour);
        double minNeighborCost = Double.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < neighbors.length; i++) {
            double cost = findCost(neighbors[i]);
            if (cost < minNeighborCost){
                minNeighborCost = cost;
                index = i;
            }
        }

        if (minNeighborCost < minCost){
            return findGreedySolutionRec(neighbors[index], minNeighborCost);
        }

        return tour;
    }


    /* --- Helper Methods --- */

    private double findCost(int[] path){
        double sum = 0;

        for (int i=0; i < path.length - 1; i++){
            sum += distances[path[i]][path[(i+1)]];
        }

        sum += distances[path[path.length - 1]][path[0]];

        return sum;
    }

    private int[][] get2OptNeighbors(int[] tour){
        int[][] neighbors = new int[Utils.nChoose2(numOfCities)][tour.length];
        int index = 0;

        for (int i = 0; i < numOfCities - 1; i++) {
            for (int k = i + 1; k < numOfCities; k++) {
                neighbors[index++] = get2Opt(tour, i, k);
            }
        }

        return neighbors;
    }

    private int[] get2Opt(int[] tour, int i, int k){
        int[] neighbor = new int[tour.length];

        System.arraycopy(tour, 0, neighbor, 0, i);

        for (int j = i; j <= k; j++) {
            neighbor[j] = tour[k + i - j];
        }

        System.arraycopy(tour, k + 1, neighbor, k + 1, tour.length - k - 1);

        return neighbor;
    }

}
