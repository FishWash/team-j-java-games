package Scripts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyInputHandler implements KeyListener {

  public static KeyInputHandler instance;
  private HashMap<Integer, Boolean> keyCodeMap = new HashMap<>();

  public KeyInputHandler() {
    instance = this;
  }

  public static KeyInputHandler getInstance() {
    return instance;
  }

  public boolean getKeyPressed(int keyCode) {
    Boolean isPressed = keyCodeMap.get(keyCode);
    if (isPressed != null) {
      return isPressed;
    }
    System.out.println("ERROR in " + this + ": " + KeyEvent.getKeyText(keyCode) + " is not a valid key.");
    return false;
  }

  public void addValidKeyCode(int validKeyCode) {
    if (!keyCodeMap.containsKey(validKeyCode)) {
      keyCodeMap.put(validKeyCode, false);
    }
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    int _keyCode = keyEvent.getExtendedKeyCode();
    if (keyCodeMap.containsKey(_keyCode)) {
      keyCodeMap.replace(_keyCode, true);
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    int _keyCode = keyEvent.getExtendedKeyCode();
    if (keyCodeMap.containsKey(_keyCode)) {
      keyCodeMap.replace(_keyCode, false);
    }
  }

  @Override
  public void keyTyped(KeyEvent keyEvent) {

  }
}
