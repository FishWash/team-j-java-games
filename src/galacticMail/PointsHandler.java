package galacticMail;

public class PointsHandler {
  protected int points = 0;
  protected static PointsHandler instance;
  
  public PointsHandler(){
    instance = this;
  }

  public void addPoints(int pointsToAdd){
    points += pointsToAdd;
  }

  public void losePoints(int pointsToLose) {
    if (points > 0) {
      points -= pointsToLose;
    }
  }

  public int getPoints(){
    return points;
  }

  public static PointsHandler getInstance(){
    return instance;
  }
}
