import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IDK {
    private final List<TravelingSalesman> TSPList;
    private final int numOfTSP, numOfCities;
    private final boolean solveTSP;
    private double[] optimalStatistics, randomStatistics, greedyStatistics;

    public IDK(int numOfTSP, int numOfCities, int seed, boolean solveTSP){
        this.numOfTSP = numOfTSP;
        this.numOfCities = numOfCities;
        this.solveTSP = solveTSP;

        this.optimalStatistics = new double[]{0,0,Double.MAX_VALUE,0};
        this.randomStatistics = new double[]{0,0,Double.MAX_VALUE,0,0};
        this.greedyStatistics = new double[]{0,0,Double.MAX_VALUE,0,0};
        this.TSPList = new ArrayList<>();

        // Random Number Generator
        Random r = new Random(seed);
        int[] randomSeeds = r.ints(numOfTSP).toArray();
        for (int i = 0; i < numOfTSP; i++) {
            TSPList.add(new TravelingSalesman(numOfCities, randomSeeds[i]));
            solveTSP(TSPList.get(i));
        }

        calculateStatistics();
    }

    public IDK(int numOfTSP, int numOfCities, int seed){
        this(numOfTSP, numOfCities, seed, false);
    }

    private void solveTSP(TravelingSalesman tsp){
        if (solveTSP)
            tsp.findSolutionByForce();
        tsp.generateRandomTour();
        tsp.findGreedySolution();
    }

    private void calculateStatistics(){
        if (solveTSP)
            calculateOptimalStatistics();
        calculateRandomStatistics();
        calculateGreedyStatistics();
   }

    private void calculateOptimalStatistics(){
        double mean = 0;
        double var = 0;

        for (TravelingSalesman ts : TSPList){
            if (ts.getOptimalCost() < optimalStatistics[2]) {
                optimalStatistics[2] = ts.getOptimalCost();
            } else if (ts.getOptimalCost() > optimalStatistics[3]){
                optimalStatistics[3] = ts.getOptimalCost();
            }
            mean += ts.getOptimalCost();
        }

        mean /= numOfTSP;

        for (TravelingSalesman ts : TSPList){
            var += Math.pow(ts.getOptimalCost() - mean, 2);
        }

        var /= numOfTSP;

        optimalStatistics[0] = mean;
        optimalStatistics[1] = Math.sqrt(var);
    }

    private void calculateRandomStatistics(){
        double mean = 0;
        double var = 0;

        for (TravelingSalesman ts : TSPList){
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

        for (TravelingSalesman ts : TSPList){
            var += Math.pow(ts.getRandomCost() - mean, 2);
        }

        var /= numOfTSP;

        randomStatistics[0] = mean;
        randomStatistics[1] = Math.sqrt(var);
    }

    private void calculateGreedyStatistics(){
        double mean = 0;
        double var = 0;

        for (TravelingSalesman ts : TSPList){
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

        for (TravelingSalesman ts : TSPList){
            var += Math.pow(ts.getGreedyCost() - mean, 2);
        }

        var /= numOfTSP;

        greedyStatistics[0] = mean;
        greedyStatistics[1] = Math.sqrt(var);
    }

    public void printStatistics(){
        System.out.println("TSP Solver");
        System.out.println("\tNum of TSP Instances: " + numOfTSP);
        System.out.println("\tNum of Cities: " + numOfCities);

        if (solveTSP) {
            System.out.println("\n\tOptimal Solutions");
            System.out.println("\t\tMean: " + optimalStatistics[0]);
            System.out.println("\t\tStd Dev: " + optimalStatistics[1]);
            System.out.println("\t\tMin Cost: " + optimalStatistics[2]);
            System.out.println("\t\tMax Cost: " + optimalStatistics[3]);
        }

        System.out.println("\n\tRandom Solutions");
        System.out.println("\t\tMean: " + randomStatistics[0]);
        System.out.println("\t\tStd Dev: " + randomStatistics[1]);
        System.out.println("\t\tMin Cost: " + randomStatistics[2]);
        System.out.println("\t\tMax Cost: " + randomStatistics[3]);
        if (solveTSP)
            System.out.println("\t\tNumber of Optimal Solutions found: " + randomStatistics[4]);

        System.out.println("\n\tGreedy Solutions");
        System.out.println("\t\tMean: " + greedyStatistics[0]);
        System.out.println("\t\tStd Dev: " + greedyStatistics[1]);
        System.out.println("\t\tMin Cost: " + greedyStatistics[2]);
        System.out.println("\t\tMax Cost: " + greedyStatistics[3]);
        if (solveTSP)
            System.out.println("\t\tNumber of Optimal Solutions found: " + greedyStatistics[4]);
    }

    public static void main(String[] args) {
        IDK idk = new IDK(100, 100, 260835976);

        idk.printStatistics();
    }
}
