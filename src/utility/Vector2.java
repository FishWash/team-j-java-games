package utility;

// A data type representing a 2D Vector with x and y components.
// Written by jfox 4/2018
public class Vector2
{
  public double x;
  public double y;

  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // newRotationVector creates a new Vector2 with rotation.
  public static Vector2 newRotationVector(double rotationInAngles) {
    Vector2 newVector = new Vector2(1, 0);
    newVector.setRotation(rotationInAngles);
    return newVector;
  }
  // newMagnitudeVector creates a new Vector2 with rotation and magnitude.
  public static Vector2 newMagnitudeVector(double magnitude) {
    Vector2 newVector = new Vector2(1, 0);
    newVector.setMagnitude(magnitude);
    return newVector;
  }
  // newRotationMagnitudeVector creates a new Vector2 with magnitude.
  public static Vector2 newRotationMagnitudeVector(double rotationInAngles, double magnitude) {
    Vector2 newVector = new Vector2(1, 0);
    newVector.setRotation(rotationInAngles);
    newVector.setMagnitude(magnitude);
    return newVector;
  }

  public static Vector2 addVectors(Vector2 firstVector, Vector2 secondVector) {
    return new Vector2(firstVector.x + secondVector.x, firstVector.y + secondVector.y);
  }
  public static Vector2 subtractVectors(Vector2 firstVector, Vector2 secondVector) {
    return new Vector2(firstVector.x - secondVector.x, firstVector.y - secondVector.y);
  }
  public static Vector2 multiplyVector(Vector2 vector, double multiplier) {
    return new Vector2(vector.x*multiplier, vector.y*multiplier);
  }

  public double getMagnitude() {
    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }
  public double getRotation() {
    return Math.atan2(x, -y);
  }

  public void setMagnitude(double magnitude) {
    double factor = magnitude / this.getMagnitude();
    this.x *= factor;
    this.y *= factor;
  }
  public void clampMagnitude(double maxMagnitude) {
    if (getMagnitude() > maxMagnitude) {
      setMagnitude(maxMagnitude);
    }
  }
  public void setRotation(double angle) {
    double newMagnitude = this.getMagnitude();
    this.x = Math.cos(Math.toRadians(angle)) * newMagnitude;
    this.y = -Math.sin(Math.toRadians(angle)) * newMagnitude;
  }

  public String toString() {
    return "[(Vector2) x: " + x + ", y: " + y + "]";
  }
}
