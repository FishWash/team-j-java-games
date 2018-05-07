package galacticMail;

import general.GamePanel;
import general.GameWorld;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import java.awt.*;
import java.io.InputStream;

public class GalacticMailPanel extends GamePanel {

  private Sequencer loopingMidiSequencer;

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
    if (loopingMidiSequencer != null) {
      loopingMidiSequencer.stop();
    }
    new GalacticLevelWorld(1);
    loadLoopingMidi("Music.mid");
    clockThread.start();
    loopingMidiSequencer.start();
  }

  // This midi loops outside of GameWorlds.
  public void loadLoopingMidi(String fileName) {
    try {
      loopingMidiSequencer = MidiSystem.getSequencer();
      loopingMidiSequencer.open();
      InputStream soundStream = getClass()
              .getResourceAsStream("/galacticMail/sounds/" + fileName);
      loopingMidiSequencer.setSequence(soundStream);
      loopingMidiSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

      // Lower midi volume
      if (loopingMidiSequencer instanceof Synthesizer) {
        Synthesizer synthesizer = (Synthesizer) loopingMidiSequencer;
        MidiChannel[] channels = synthesizer.getChannels();

        // gain is a value between 0 and 1 (loudest)
        double gain = 0.1D;
        for (int i = 0; i < channels.length; i++) {
          channels[i].controlChange(7, (int) (gain * 127.0));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
