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

  public int getPoints(){
    return points;
  }
}
