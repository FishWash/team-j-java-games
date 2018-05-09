package galacticMail;

import general.GamePanel;
import general.GameWorld;
import utility.SoundHandler;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.InputStream;

public class GalacticMailPanel extends GamePanel {

  private Clip loopingSound;
  private int lives;

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new GalacticTitleWorld();
    title = "You rock-et!";
  }

  @Override
  protected void start() {
    resetClock();
    int newLevel = GameWorld.getInstance().getLevel()+1;
    new GalacticLevelWorld(newLevel);
    clockThread.start();
  }

  @Override
  protected void restart() {

    resetClock();
    new PointsHandler();
    new GalacticLevelWorld(1);

    SoundHandler.getInstance().playSoundLooping("Music.wav");
    lives = 2;
    clockThread.start();
  }

  public int getLives() {
    return lives;
  }
  public void addLife() {
    lives++;
  }
  public void loseLife() {
    lives--;
  }
}
