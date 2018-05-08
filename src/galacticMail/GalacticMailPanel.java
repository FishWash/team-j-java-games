package galacticMail;

import general.GamePanel;
import general.GameWorld;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.awt.*;
import java.io.InputStream;

public class GalacticMailPanel extends GamePanel {

  private Sequencer loopingMidiSequencer;
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
    if (loopingMidiSequencer != null) {
      loopingMidiSequencer.stop();
    }

    resetClock();
    new PointsHandler();
    new GalacticLevelWorld(1);
    loadLoopingMidi("Music.mid");
    lives = 2;
    clockThread.start();
    loopingMidiSequencer.start();
  }

  // This midi loops outside of GameWorlds.
  private void loadLoopingMidi(String fileName) {
    try {
      loopingMidiSequencer = MidiSystem.getSequencer();
      loopingMidiSequencer.open();
      InputStream soundStream = getClass()
              .getResourceAsStream("/galacticMail/sounds/" + fileName);
      loopingMidiSequencer.setSequence(soundStream);
      loopingMidiSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
