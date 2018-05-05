package galacticMail;

import utility.KeyInputHandler;

import java.awt.event.KeyEvent;

public class RocketKeyInput
{
  private int moveForwardKeyCode, moveBackwardKeyCode, turnLeftKeyCode,
          turnRightKeyCode, shootKeyCode;

  public RocketKeyInput() {
    moveForwardKeyCode = KeyEvent.VK_W;
    moveBackwardKeyCode = KeyEvent.VK_S;
    turnLeftKeyCode = KeyEvent.VK_A;
    turnRightKeyCode = KeyEvent.VK_D;
    shootKeyCode = KeyEvent.VK_R;

    addUsedKeyCodes(KeyInputHandler.getInstance());
  }

  public double getMoveInput() {
    int _moveInput = 0;
    if ( KeyInputHandler.getInstance().getKeyPressed(moveForwardKeyCode) ) {
      _moveInput++;
    }
    if ( KeyInputHandler.getInstance().getKeyPressed(moveBackwardKeyCode) ) {
      _moveInput--;
    }
    return _moveInput;
  }

  public double getTurnInput() {
    int _turnInput = 0;
    if ( KeyInputHandler.getInstance().getKeyPressed(turnLeftKeyCode) ) {
      _turnInput--;
    }
    if ( KeyInputHandler.getInstance().getKeyPressed(turnRightKeyCode) ) {
      _turnInput++;
    }
    return _turnInput;
  }

  public boolean getShootPressed() {
    return ( KeyInputHandler.getInstance().getKeyPressed(shootKeyCode) );
  }

  private void addUsedKeyCodes(KeyInputHandler keyInputHandlerInstance) {
    keyInputHandlerInstance.addValidKeyCode(moveForwardKeyCode);
    keyInputHandlerInstance.addValidKeyCode(moveBackwardKeyCode);
    keyInputHandlerInstance.addValidKeyCode(turnLeftKeyCode);
    keyInputHandlerInstance.addValidKeyCode(turnRightKeyCode);
    keyInputHandlerInstance.addValidKeyCode(shootKeyCode);
  }
}
