package scripts.utility;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Clock implements Runnable
{
  private static Clock instance;
  private int numFramesSinceStart = 0;
  private CopyOnWriteArrayList<ClockListener> clockListeners = new CopyOnWriteArrayList<>();
  private HashMap<ClockListener, Boolean> addedClockListeners = new HashMap<>();
  private boolean stopped, paused;


  private static final long FRAME_LENGTH = 15;

  public Clock() {
    instance = this;
  }

  public static Clock getInstance() {
    return instance;
  }

  public boolean getPaused() {
    return paused;
  }

  public void run() {
    while (!stopped) {
      if (!paused) {
        updateClockListeners();
        numFramesSinceStart++;
      }
      try {
          Thread.sleep(FRAME_LENGTH);
      } catch (Exception e) {
        // do catch stuff
      }
    }
  }

  public void addClockListener(ClockListener clockListener) {
    if (!addedClockListeners.containsKey(clockListener)) {
      clockListeners.add(clockListener);
      addedClockListeners.put(clockListener, true);
    }
  }

  public void removeClockListener(ClockListener clockListener) {
    if (addedClockListeners.containsKey(clockListener)) {
      clockListeners.remove(clockListener);
      addedClockListeners.remove(clockListener);
    }
  }

  public void stop() {
    stopped = true;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public int getNumFramesSinceStart() {
    return numFramesSinceStart;
  }

  public void updateClockListeners() {
    for (ClockListener _co : clockListeners) {
      _co.update();
    }
  }
}
