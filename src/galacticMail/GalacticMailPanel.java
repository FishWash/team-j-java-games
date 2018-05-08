package galacticMail;

import general.GamePanel;
import general.GameWorld;

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
    playLoopingSound("Music.wav");
    lives = 2;
    clockThread.start();
  }

//  // This midi loops outside of GameWorlds.
//  private void loadLoopingMidi(String fileName) {
//    try {
//      loopingMidiSequencer = MidiSystem.getSequencer();
//      loopingMidiSequencer.open();
//      InputStream soundStream = getClass()
//              .getResourceAsStream("/galacticMail/sounds/" + fileName);
//      loopingMidiSequencer.setSequence(soundStream);
//      loopingMidiSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  public void stopLoopingSound() {
    if (loopingSound != null) {
      loopingSound.stop();
    }
  }

  private void playLoopingSound(String fileName) {
    if (loopingSound != null) {
      loopingSound.stop();
    }
    try {
      InputStream fileStream = getClass()
              .getResourceAsStream("/galacticMail/sounds/" + fileName);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(fileStream));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      loopingSound = clip;
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
