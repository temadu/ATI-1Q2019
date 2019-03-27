package tp1;

import java.util.Random;

public class RandomGaussianGenerator implements RandomGenerators {
    private double mean;
    private double std;
    private Random r1;
    private Random r2;

    public RandomGaussianGenerator(double mean, double std) {
        this.mean = mean;
        this.std = std;
        this.r1 = new Random();
        this.r2 = new Random();
    }
    public double nextRandom() {
        double x1;
        do {
            x1 = r1.nextDouble();
        } while(x1 == 0); //evitar log 0 -> undefined

        return Math.sqrt(-2 * Math.log(x1)) * Math.cos(r2.nextDouble() * 2 * Math.PI) * std + mean;
    }
}
