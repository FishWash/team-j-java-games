package Scripts;

public class TankInput
{
  private boolean moveForwardPressed, moveBackwardPressed, turnLeftPressed, turnRightPressed, shootPressed;

  public void setMoveForwardPressed(boolean pressed) {
    moveForwardPressed = pressed;
  }
  public void setMoveBackwardPressed(boolean pressed) {
    moveBackwardPressed = pressed;
  }
  public void setTurnLeftPressed(boolean pressed) {
    turnLeftPressed = pressed;
  }
  public void setTurnRightPressed(boolean pressed) {
    turnRightPressed = pressed;
  }
  public void setShootPressed(boolean pressed) {
    shootPressed = pressed;
  }

  public double getMoveInput() {
    int _moveInput = 0;
    if (moveForwardPressed) _moveInput++;
    if (moveBackwardPressed) _moveInput--;
    return _moveInput;
  }

  public double getTurnInput() {
    int _turnInput = 0;
    if (turnLeftPressed) _turnInput--;
    if (turnRightPressed) _turnInput++;
    return _turnInput;
  }

  public int getShootInput() {
    if (shootPressed) return 1;
    else return 0;
  }
}
