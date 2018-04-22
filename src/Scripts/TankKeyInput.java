package Scripts;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TankKeyInput
{
  private int moveForwardKeyCode, moveBackwardKeyCode, turnLeftKeyCode, turnRightKeyCode, shootKeyCode;

  public TankKeyInput(Tank.Player player) {
    if (player == Tank.Player.Two) {
      moveForwardKeyCode = KeyEvent.VK_I;
      moveBackwardKeyCode = KeyEvent.VK_K;
      turnLeftKeyCode = KeyEvent.VK_J;
      turnRightKeyCode = KeyEvent.VK_L;
      shootKeyCode = KeyEvent.VK_P;
    }
    else {
      moveForwardKeyCode = KeyEvent.VK_W;
      moveBackwardKeyCode = KeyEvent.VK_S;
      turnLeftKeyCode = KeyEvent.VK_A;
      turnRightKeyCode = KeyEvent.VK_D;
      shootKeyCode = KeyEvent.VK_R;
    }
    KeyInputHandler.getInstance().addValidKeyCodes(getUsedKeyCodes());
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

  private ArrayList<Integer> getUsedKeyCodes() {
    ArrayList<Integer> _usedKeyCodes = new ArrayList<>();
    _usedKeyCodes.add(moveForwardKeyCode);
    _usedKeyCodes.add(moveBackwardKeyCode);
    _usedKeyCodes.add(turnLeftKeyCode);
    _usedKeyCodes.add(turnRightKeyCode);
    _usedKeyCodes.add(shootKeyCode);
    return _usedKeyCodes;
  }
}
