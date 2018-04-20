package Scripts;

// A data type representing a 2D Vector with x and y components.
public class Vector2
{
  public double x;
  public double y;

  public Vector2() {
    this.x = 0;
    this.y = 0;
  }
  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void addVector(Vector2 otherVector) {
    this.x += otherVector.x;
    this.y += otherVector.y;
  }
}
