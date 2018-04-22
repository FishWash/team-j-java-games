package Scripts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyInputHandler implements KeyListener {

  private static KeyInputHandler instance;
  private HashMap<Integer, Boolean> keyCodeMap;

  public KeyInputHandler() {
    instance = this;
    keyCodeMap = new HashMap<>();
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

  public void addValidKeyCodes(ArrayList<Integer> validKeyCodes) {
    for (int i : validKeyCodes) {
      if (!keyCodeMap.containsKey(i)) {
        keyCodeMap.put(i, false);
      }
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
