package Scripts;

public class Vector2D
{
  public double x;
  public double y;

  public Vector2D() {
    this.x = 0;
    this.y = 0;
  }
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void addVector(Vector2D otherVector) {
    this.x += otherVector.x;
    this.y += otherVector.y;
  }
}
