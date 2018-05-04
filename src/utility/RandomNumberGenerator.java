package utility;

import java.util.Random;

public class RandomNumberGenerator {

  public static Vector2 getRandomPosition(double minX, double minY, double maxX, double maxY) {
    double randomX = getRandomDouble(minX, maxX);
    double randomY = getRandomDouble(minY, maxY);

    return new Vector2(randomX, randomY);
  }

  public static double getSpread(double maxDeviation) {
    Random random = new Random();
    return maxDeviation * (2*random.nextDouble() - 1);
  }

  public static boolean getRandomBoolean() {
    Random random = new Random();
    return random.nextBoolean();
  }

  public static int getRandomInt(int minimum, int maximum) {
    Random random = new Random();
    int bound = maximum - minimum + 1;
    return random.nextInt(bound) + minimum;
  }

  public static double getRandomDouble(double minimum, double maximum) {
    Random random = new Random();
    double range = maximum - minimum;
    return random.nextDouble()*range + minimum;
  }
}
