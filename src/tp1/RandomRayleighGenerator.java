package tp1;

import java.util.Random;

public class RandomRayleighGenerator implements RandomGenerators {
    private double psi;
    private Random r;

    public RandomRayleighGenerator(double psi) {
        this.psi = psi;
        this.r = new Random();
    }
    public double nextRandom() {
        return psi * Math.sqrt(-2 * Math.log(1 - r.nextDouble()));
    }
}
