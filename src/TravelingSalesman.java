import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;


public class TravelingSalesman {
    private final double[][] cities;
    private final double[][] distances;
    private int[] solution, randomTour;
    private double optimalCost, randomCost;
    private boolean solved;
    private int seed;


    public TravelingSalesman(int seed){
        this.solution = new int[6];
        this.cities = new double[7][2];
        this.distances = new double[7][7];
        this.solved = false;
        this.optimalCost = Double.MAX_VALUE;
        this.seed = seed;

        // Set points
        Random r = new Random(seed);
        double[][] tab = Stream.generate(() -> r.doubles(7).toArray())
                .limit(7)
                .toArray(double[][]::new);

        for (int i=0; i < 7; i++){
            this.cities[i][0] = r.nextDouble();
            this.cities[i][1] = r.nextDouble();
        }

        // Set distances
        for (int i=0; i < 7; i++){
            for(int j=0; j < 7; j++){
                this.distances[i][j] = Math.sqrt( Math.pow(cities[i][0] - cities[j][0], 2)
                        + Math.pow(cities[i][1] - cities[j][1], 2) );
            }
        }
    }

    public TravelingSalesman(){
        this(1000);
    }

    public double[][] getCities() {
        return cities;
    }

    public double[][] getDistances() {
        return distances;
    }

    public int[] getSolution() {
        int[] tour = new int[7];
        System.arraycopy(solution, 0, tour, 1, solution.length);
        return tour;
    }

    public boolean isSolved() {
        return solved;
    }

    public double getDistanceBetween(int city1, int city2){
        return distances[city1][city2];
    }

    public double getOptimalCost() {
        return optimalCost;
    }

    public double getRandomCost() {
        return randomCost;
    }

    public void findSolutionByForce(){
        bruteForceRecursive(6, new int[]{1,2,3,4,5,6});
        solved = true;
    }

    public void bruteForceRecursive(int n, int[] path) {
        if(n == 1) {
            double costOfPath = findCost(path);
            if (costOfPath < this.optimalCost){
                this.optimalCost = costOfPath;
                this.solution = Arrays.copyOf(path, 6);
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

    private double findCost(int[] path){
        double sum = distances[0][path[0]];

        for (int i=0; i < path.length - 1; i++){
            sum += distances[path[i]][path[(i+1)]];
        }
        sum +=  distances[path[path.length - 1]][0];

        return sum;
    }

    public void generateRandomTour(){
        this.randomTour = new int[7];
        ArrayList<Integer> tour = new ArrayList<>();

        for (int i = 1; i < 7; i++) {
            tour.add(i);
        }

        java.util.Collections.shuffle(tour, new Random(seed));
        tour.add(0, 0);

        for(int i = 0; i < tour.size(); i++)
            randomTour[i] = tour.get(i);

        randomCost = findCost(randomTour);
    }

    public static void main(String[] args) {
        TravelingSalesman ts = new TravelingSalesman();

        Utils.print2DArray(ts.getCities());
        Utils.print2DArray(ts.getDistances());

        ts.findSolutionByForce();
        Utils.printArray(ts.getSolution());
    }



}
