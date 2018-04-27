package scripts;

import scripts.utility.DisplayableElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Menu extends DisplayableElement implements KeyListener {
  private enum Button {Play, Options, Quit}
  private Button button = Button.Play;
  private BufferedImage backgroundImage;

  private GamePanel gamePanel;
  private int upKeyCode = KeyEvent.VK_W, downKeyCode = KeyEvent.VK_S;

  public Menu(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }


  public void displayOnGraphics(Graphics graphics) {

  }

  public void keyPressed(KeyEvent keyEvent) {

  }
  public void keyReleased(KeyEvent keyEvent) {}
  public void keyTyped(KeyEvent keyEvent) {}
}
