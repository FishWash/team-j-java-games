package galacticMail;

import utility.KeyInputHandler;

import java.awt.event.KeyEvent;

public class RocketKeyInput
{
  private int moveForwardKeyCode, moveBackwardKeyCode, turnLeftKeyCode,
          turnRightKeyCode, launchKeyCode;

  private double fuzzedTurnInput = 0;
  private double turnStep = 0.1;

  public RocketKeyInput() {
//    moveForwardKeyCode = KeyEvent.VK_W;
//    moveBackwardKeyCode = KeyEvent.VK_S;
    turnLeftKeyCode = KeyEvent.VK_A;
    turnRightKeyCode = KeyEvent.VK_D;
    launchKeyCode = KeyEvent.VK_R;

    addUsedKeyCodes(KeyInputHandler.getInstance());
  }

//  public double getMoveInput() {
//    int moveInput = 0;
//    if ( KeyInputHandler.getInstance().getKeyPressed(moveForwardKeyCode) ) {
//      moveInput++;
//    }
//    if ( KeyInputHandler.getInstance().getKeyPressed(moveBackwardKeyCode) ) {
//      moveInput--;
//    }
//    return moveInput;
//  }

  public double getTurnInput() {
    int turnInput = 0;
    if ( KeyInputHandler.getInstance().getKeyPressed(turnLeftKeyCode) ) {
      turnInput--;
    }
    if ( KeyInputHandler.getInstance().getKeyPressed(turnRightKeyCode) ) {
      turnInput++;
    }
    Double difference = turnInput - fuzzedTurnInput;
    fuzzedTurnInput += Math.min(turnStep, Math.max(-turnStep, difference));
    return fuzzedTurnInput;
  }

  public boolean getLaunchPressed() {
    return ( KeyInputHandler.getInstance().getKeyPressed(launchKeyCode) );
  }

  private void addUsedKeyCodes(KeyInputHandler keyInputHandlerInstance) {
//    keyInputHandlerInstance.addValidKeyCode(moveForwardKeyCode);
//    keyInputHandlerInstance.addValidKeyCode(moveBackwardKeyCode);
    keyInputHandlerInstance.addValidKeyCode(turnLeftKeyCode);
    keyInputHandlerInstance.addValidKeyCode(turnRightKeyCode);
    keyInputHandlerInstance.addValidKeyCode(launchKeyCode);
  }
}
