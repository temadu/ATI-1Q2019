package tp1;

import java.util.Random;

public class RandomExpGenerator implements RandomGenerators{
    private double lambda;
    private Random r;

    public RandomExpGenerator (double lambda) {
        this.lambda = lambda;
        this.r = new Random();

    }

    public double nextRandom() {
        return Math.log(r.nextDouble()) / -lambda;
    }
}
