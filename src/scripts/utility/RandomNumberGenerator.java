package scripts.utility;

import java.util.Random;

public class RandomNumberGenerator {
  public static double getSpread(double maxDeviation) {
    Random random = new Random();
    return maxDeviation * (2*random.nextDouble() - 1);
  }
}
