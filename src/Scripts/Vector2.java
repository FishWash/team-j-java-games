package Scripts;

// A data type representing a 2D Vector with x and y components.
public class Vector2
{
  public double x;
  public double y;
  public static Vector2 zero = new Vector2(0,0);

  public Vector2() {
    this.x = 0;
    this.y = 0;
  }
  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2 addVector(Vector2 otherVector) {
    return new Vector2(this.x + otherVector.x, this.y + otherVector.y);
  }

  public String toString() {
    return "[(Vector2) x: " + x + ", y: " + y + "]";
  }
}
