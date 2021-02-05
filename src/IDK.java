import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IDK {
    private final List<TravelingSalesman> TSPList;
    private double[] optimalStatistics, randomStatistics;
    private final int numOfTSP;


    public IDK(int numOfTSP){
        this.numOfTSP = numOfTSP;
        this.optimalStatistics = new double[]{0,0,Double.MAX_VALUE,0};
        this.randomStatistics = new double[]{0,0,Double.MAX_VALUE,0,0};
        this.TSPList = new ArrayList<>();

        // Random Number Generator
        int seed = 260835976;
        Random r = new Random(seed);
        int[] randomSeeds = r.ints(numOfTSP).toArray();
        for (int i = 0; i < numOfTSP; i++) {
            TSPList.add(new TravelingSalesman(randomSeeds[i]));
            TSPList.get(i).findSolutionByForce();
            TSPList.get(i).generateRandomTour();
        }

        calculateOptimalStatistics();
        calculateRandomStatistics();
    }


    public void calculateRandomStatistics(){
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

    public void calculateOptimalStatistics(){
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

    public void printStatistics(){
        System.out.println("Optimal Solutions");
        System.out.println("\tMean: " + optimalStatistics[0]);
        System.out.println("\tStd Dev: " + optimalStatistics[1]);
        System.out.println("\tMin Cost: " + optimalStatistics[2]);
        System.out.println("\tMax Cost: " + optimalStatistics[3]);
        System.out.println();
        System.out.println("Random Solutions");
        System.out.println("\tMean: " + randomStatistics[0]);
        System.out.println("\tStd Dev: " + randomStatistics[1]);
        System.out.println("\tMin Cost: " + randomStatistics[2]);
        System.out.println("\tMax Cost: " + randomStatistics[3]);
        System.out.println("\tNumber of Optimal Solutions found: " + randomStatistics[4]);
    }

    public static void main(String[] args) {
        IDK idk = new IDK(100);

        idk.printStatistics();
    }
}
