package utility;

import general.GameWorld;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundHandler {
  private static SoundHandler instance;

  public SoundHandler() {
    instance = this;
  }

  private HashMap<String, Clip> soundCache = new HashMap<>();
  private Clip loopingSound;

  public static SoundHandler getInstance() {
    return instance;
  }

  public Clip loadSound(String fileName) {
    Clip clip = soundCache.get(fileName);
    if (clip == null) {
      try {
        InputStream fileStream = getClass()
                .getResourceAsStream(GameWorld.getInstance().getSoundPath() + fileName);
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(fileStream));
        soundCache.put(fileName, clip);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return clip;
  }

  public void playSound(String fileName) {
    Clip clip = loadSound(fileName);
    if (clip != null) {
      clip.stop();
      clip.setFramePosition(0);
      clip.start();
    }
  }

  // Only one sound can be looping at a time.
  public void playSoundLooping(String fileName) {
    if (loopingSound != null) {
      loopingSound.stop();
    }
    try {
      InputStream fileStream = getClass()
              .getResourceAsStream(GameWorld.getInstance().getSoundPath() + fileName);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(fileStream));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      loopingSound = clip;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stopLoopingSound() {
    if (loopingSound != null) {
      loopingSound.stop();
    }
  }

  public void stopSounds() {
    for (Map.Entry<String, Clip> entry : soundCache.entrySet()) {
      Clip clip = entry.getValue();
      clip.stop();
    }

    if (loopingSound != null) {
      loopingSound.stop();
    }
  }
}
