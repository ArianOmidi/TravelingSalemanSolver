import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPSolver {
    private final List<TSP> TSPList;
    private final int numOfTSP, numOfCities;
    private final double[] optimalStatistics, randomStatistics, greedyStatistics;
    private boolean solvedTSP;

    public TSPSolver(int numOfTSP, int numOfCities, int seed){
        this.numOfTSP = numOfTSP;
        this.numOfCities = numOfCities;
        this.solvedTSP = false;

        this.optimalStatistics = new double[]{0,0,Double.MAX_VALUE,0};
        this.randomStatistics = new double[]{0,0,Double.MAX_VALUE,0,0};
        this.greedyStatistics = new double[]{0,0,Double.MAX_VALUE,0,0};
        this.TSPList = new ArrayList<>();

        // Random Number Generator
        Random r = new Random(seed);
        int[] randomSeeds = r.ints(numOfTSP).toArray();
        for (int i = 0; i < numOfTSP; i++) {
            TSPList.add(new TSP(numOfCities, randomSeeds[i]));
        }
    }

    public TSPSolver(int numOfTSP, int numOfCities){
        this(numOfTSP, numOfCities, 260835976);
    }

    public void solveTSPs(boolean solveTSP){
        for (TSP tsp: TSPList){
            if (solveTSP)
                tsp.findSolutionByForce();
            tsp.generateRandomTour();
            tsp.findGreedySolution();
        }

        calculateStatistics(solveTSP);
        solvedTSP = solveTSP;
    }

    private void calculateStatistics(boolean solveTSP){
        if (solveTSP)
            calculateOptimalStatistics();
        calculateRandomStatistics();
        calculateGreedyStatistics();
   }

    private void calculateOptimalStatistics(){
        double mean = 0;
        double var = 0;

        for (TSP ts : TSPList){
            if (ts.getOptimalCost() < optimalStatistics[2]) {
                optimalStatistics[2] = ts.getOptimalCost();
            } else if (ts.getOptimalCost() > optimalStatistics[3]){
                optimalStatistics[3] = ts.getOptimalCost();
            }
            mean += ts.getOptimalCost();
        }

        mean /= numOfTSP;

        for (TSP ts : TSPList){
            var += Math.pow(ts.getOptimalCost() - mean, 2);
        }

        var /= numOfTSP;

        optimalStatistics[0] = mean;
        optimalStatistics[1] = Math.sqrt(var);
    }

    private void calculateRandomStatistics(){
        double mean = 0;
        double var = 0;

        for (TSP ts : TSPList){
            if (ts.getRandomCost() == ts.getOptimalCost()){
                randomStatistics[4]++;
            }
            if (ts.getRandomCost() < randomStatistics[2]) {
                randomStatistics[2] = ts.getRandomCost();
            } else if (ts.getRandomCost() > randomStatistics[3]){
                randomStatistics[3] = ts.getRandomCost();
            }
            mean += ts.getRandomCost();
        }

        mean /= numOfTSP;

        for (TSP ts : TSPList){
            var += Math.pow(ts.getRandomCost() - mean, 2);
        }

        var /= numOfTSP;

        randomStatistics[0] = mean;
        randomStatistics[1] = Math.sqrt(var);
    }

    private void calculateGreedyStatistics(){
        double mean = 0;
        double var = 0;

        for (TSP ts : TSPList){
            if (ts.getGreedyCost() == ts.getOptimalCost()){
                greedyStatistics[4]++;
            }
            if (ts.getGreedyCost() < greedyStatistics[2]) {
                greedyStatistics[2] = ts.getGreedyCost();
            } else if (ts.getGreedyCost() > greedyStatistics[3]){
                greedyStatistics[3] = ts.getGreedyCost();
            }
            mean += ts.getGreedyCost();
        }

        mean /= numOfTSP;

        for (TSP ts : TSPList){
            var += Math.pow(ts.getGreedyCost() - mean, 2);
        }

        var /= numOfTSP;

        greedyStatistics[0] = mean;
        greedyStatistics[1] = Math.sqrt(var);
    }

    public void printStatistics(){
        System.out.println("TSP Solver:");
        System.out.println("\tNum of TSP Instances: " + numOfTSP);
        System.out.println("\tNum of Cities: " + numOfCities);

        if (solvedTSP) {
            System.out.println("\n\tOptimal Solutions:");
            System.out.println("\t\tMean: " + optimalStatistics[0]);
            System.out.println("\t\tStd Dev: " + optimalStatistics[1]);
            System.out.println("\t\tMin Cost: " + optimalStatistics[2]);
            System.out.println("\t\tMax Cost: " + optimalStatistics[3]);
        }

        System.out.println("\n\tRandom Solutions:");
        System.out.println("\t\tMean: " + randomStatistics[0]);
        System.out.println("\t\tStd Dev: " + randomStatistics[1]);
        System.out.println("\t\tMin Cost: " + randomStatistics[2]);
        System.out.println("\t\tMax Cost: " + randomStatistics[3]);
        if (solvedTSP)
            System.out.println("\t\tOptimal Solutions Found: " + (int) randomStatistics[4]);

        System.out.println("\n\tGreedy Solutions:");
        System.out.println("\t\tMean: " + greedyStatistics[0]);
        System.out.println("\t\tStd Dev: " + greedyStatistics[1]);
        System.out.println("\t\tMin Cost: " + greedyStatistics[2]);
        System.out.println("\t\tMax Cost: " + greedyStatistics[3]);
        if (solvedTSP)
            System.out.println("\t\tOptimal Solutions Found: " + (int) greedyStatistics[4]);

        System.out.println();
    }

    public static void main(String[] args) {
        TSPSolver tspSolver1 = new TSPSolver(100, 7);
        tspSolver1.solveTSPs(true);
        tspSolver1.printStatistics();

        TSPSolver tspSolver2 = new TSPSolver(100, 100);
        tspSolver2.solveTSPs(false);
        tspSolver2.printStatistics();
    }
}
