package Scripts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyInputHandler
{
  private final int p1_moveForwardKey = KeyEvent.VK_W;
  private final int p1_moveBackwardKey = KeyEvent.VK_S;
  private final int p1_turnLeftKey = KeyEvent.VK_A;
  private final int p1_turnRightKey = KeyEvent.VK_D;
  private final int p1_shootKey = KeyEvent.VK_F;

  private final int p2_moveForwardKey = KeyEvent.VK_I;
  private final int p2_moveBackwardKey = KeyEvent.VK_K;
  private final int p2_turnLeftKey = KeyEvent.VK_J;
  private final int p2_turnRightKey = KeyEvent.VK_L;
  private final int p2_shootKey = KeyEvent.VK_SEMICOLON;

  private TankInput p1_tankInput, p2_tankInput;

  public KeyInputHandler()
  {
    p1_tankInput = new FuzzyTankInput();
    p2_tankInput = new FuzzyTankInput();
  }

  public TankInput getP1_tankInput() {
    return p1_tankInput;
  }
  public TankInput getP2_tankInput() {
    return p2_tankInput;
  }

  public void readKeyPressed(KeyEvent keyEvent)
  {
    switch (keyEvent.getExtendedKeyCode()) {
      case p1_moveForwardKey:
        p1_tankInput.setMoveForwardPressed(true);
        break;
      case p1_moveBackwardKey:
        p1_tankInput.setMoveBackwardPressed(true);
        break;
      case p1_turnLeftKey:
        p1_tankInput.setTurnLeftPressed(true);
        break;
      case p1_turnRightKey:
        p1_tankInput.setTurnRightPressed(true);
        break;
      case p1_shootKey:
        p1_tankInput.setShootPressed(true);
        break;
      case p2_moveForwardKey:
        p2_tankInput.setMoveForwardPressed(true);
        break;
      case p2_moveBackwardKey:
        p2_tankInput.setMoveBackwardPressed(true);
        break;
      case p2_turnLeftKey:
        p2_tankInput.setTurnLeftPressed(true);
        break;
      case p2_turnRightKey:
        p2_tankInput.setTurnRightPressed(true);
        break;
      case p2_shootKey:
        p2_tankInput.setShootPressed(true);
        break;
    }
  }

  public void readKeyReleased(KeyEvent keyEvent)
  {
    switch (keyEvent.getExtendedKeyCode()) {
      case p1_moveForwardKey:
        p1_tankInput.setMoveForwardPressed(false);
        break;
      case p1_moveBackwardKey:
        p1_tankInput.setMoveBackwardPressed(false);
        break;
      case p1_turnLeftKey:
        p1_tankInput.setTurnLeftPressed(false);
        break;
      case p1_turnRightKey:
        p1_tankInput.setTurnRightPressed(false);
        break;
      case p1_shootKey:
        p1_tankInput.setShootPressed(false);
        break;
      case p2_moveForwardKey:
        p2_tankInput.setMoveForwardPressed(false);
        break;
      case p2_moveBackwardKey:
        p2_tankInput.setMoveBackwardPressed(false);
        break;
      case p2_turnLeftKey:
        p2_tankInput.setTurnLeftPressed(false);
        break;
      case p2_turnRightKey:
        p2_tankInput.setTurnRightPressed(false);
        break;
      case p2_shootKey:
        p2_tankInput.setShootPressed(false);
        break;
    }
  }
}
