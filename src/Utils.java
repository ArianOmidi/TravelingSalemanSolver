public class Utils {

    public static int nChoose2(int n){
        if (n < 2)
            return 0;
        else
            return n * (n - 1) / 2;
    }

    public static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    public static void printArray(int[] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i] + ", ");
        }
    }

    public static void printArray(double[] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i] + ", ");
        }
    }

    public static void print2DArray(double[][] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            printArray(input[i]);
        }
    }
}
