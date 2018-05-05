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

  public void losePoints(){
    if(points > 0){
      points--;
    }
  }

  public int getPoints(){
    return points;
  }

  public static PointsHandler getInstance(){
    return instance;
  }
}
