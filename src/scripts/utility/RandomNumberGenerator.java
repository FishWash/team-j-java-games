package scripts.utility;

import java.util.Random;

public class RandomNumberGenerator {
  public static double getSpread(double maxDeviation) {
    Random random = new Random();
    return maxDeviation * (2*random.nextDouble() - 1);
  }

  public static int getRandomInt(int minimum, int maximum) {
    Random random = new Random();
    return random.nextInt(maximum-minimum+1) + minimum;
  }
}
