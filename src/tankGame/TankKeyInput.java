package tankGame;

import utility.KeyInputHandler;

import java.awt.event.KeyEvent;

public class TankKeyInput
{
  private int moveForwardKeyCode, moveBackwardKeyCode, turnLeftKeyCode,
          turnRightKeyCode, shootKeyCode;

  public TankKeyInput(TankGameWorld.Player player) {
    if (player == TankGameWorld.Player.One) {
      moveForwardKeyCode = KeyEvent.VK_W;
      moveBackwardKeyCode = KeyEvent.VK_S;
      turnLeftKeyCode = KeyEvent.VK_A;
      turnRightKeyCode = KeyEvent.VK_D;
      shootKeyCode = KeyEvent.VK_R;
    }
    else if (player == TankGameWorld.Player.Two) {
      moveForwardKeyCode = KeyEvent.VK_I;
      moveBackwardKeyCode = KeyEvent.VK_K;
      turnLeftKeyCode = KeyEvent.VK_J;
      turnRightKeyCode = KeyEvent.VK_L;
      shootKeyCode = KeyEvent.VK_P;
    }

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
