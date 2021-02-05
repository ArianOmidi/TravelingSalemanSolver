import java.util.Arrays;
import java.util.Random;

public class TravelingSalesman {
    private final double[][] cities;
    private final double[][] distances;
    private int[] solution;
    private boolean solved;
    private double cost;

    public TravelingSalesman(){
        this.solution = new int[6];
        this.cities = new double[7][2];
        this.distances = new double[7][7];
        this.solved = false;
        this.cost = Double.MAX_VALUE;

        // Set points
        Random r = new Random();
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


    public void findSolutionByForce(){
        bruteForceRecursive(6, new int[]{1,2,3,4,5,6});
        solved = true;
    }

    public void bruteForceRecursive(int n, int[] path) {
        if(n == 1) {
            double costOfPath = findCost(path);
            if (costOfPath < this.cost){
                this.cost = costOfPath;
                this.solution = Arrays.copyOf(path, 6);
            }
        } else {
            for(int i = 0; i < n-1; i++) {
                bruteForceRecursive(n - 1, path);
                if(n % 2 == 0) {
                    swap(path, i, n-1);
                } else {
                    swap(path, 0, n-1);
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

    private static  void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private static void printArray(int[] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i] + ", ");
        }
    }

    private static void printArray(double[] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i] + ", ");
        }
    }

    private static void print2DArray(double[][] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            printArray(input[i]);
        }
    }

    public static void main(String[] args) {
        TravelingSalesman ts = new TravelingSalesman();

        ts.findSolutionByForce();
        ts.getSolution();
    }



}
