import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IDK {
    private final List<TravelingSalesman> TSPList;
    private double avgCost, stdDevCost, minCost, maxCost;
    private final int numOfTSP;


    public IDK(int numOfTSP){
        this.numOfTSP = numOfTSP;
        this.minCost = Double.MAX_VALUE;
        this.maxCost = 0;
        this.TSPList = new ArrayList<>();

        // Random Number Generator
        int seed = 260835976;
        Random r = new Random(seed);
        int[] randomSeeds = r.ints(numOfTSP).toArray();
        for (int i = 0; i < numOfTSP; i++) {
            TSPList.add(new TravelingSalesman(randomSeeds[i]));
            TSPList.get(i).findSolutionByForce();
        }

        calculateStatistics();
    }

    public double getAvgCost() {
        return avgCost;
    }

    public double getStdDevCost() {
        return stdDevCost;
    }

    public double getMaxCost() {
        return maxCost;
    }

    public double getMinCost() {
        return minCost;
    }

    public void calculateStatistics(){
        double mean = 0;
        double var = 0;

        for (TravelingSalesman ts : TSPList){
            if (ts.getCost() < minCost) {
                minCost = ts.getCost();
            } else if (ts.getCost() > maxCost){
                maxCost = ts.getCost();
            }
            mean += ts.getCost();
        }

        mean /= numOfTSP;

        for (TravelingSalesman ts : TSPList){
            var += Math.pow(ts.getCost() - mean, 2);
        }

        var /= numOfTSP;

        avgCost = mean;
        stdDevCost = Math.sqrt(var);
    }

    public void printStatistics(){
        System.out.println("Mean: " + avgCost);
        System.out.println("Std Dev: " + stdDevCost);
        System.out.println("Min Cost: " + minCost);
        System.out.println("Max Cost: " + maxCost);
    }

    public static void main(String[] args) {
        IDK idk = new IDK(100);

        idk.printStatistics();
    }
}
