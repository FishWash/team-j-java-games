package scripts.utility;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Clock implements Runnable
{
  private static Clock instance;
  private int numFramesSinceStart = 0;
  private CopyOnWriteArrayList<ClockListener> clockListeners = new CopyOnWriteArrayList<>();
  private HashMap<ClockListener, Boolean> addedClockObservers = new HashMap<>();
  private boolean stopped;
  private boolean paused;

  private static final long FRAME_LENGTH = 15;

  public Clock() {
    instance = this;
  }

  public static Clock getInstance() {
    return instance;
  }

  public void run() {
    while (!stopped) {
      if (!paused) {
        updateClockObservers();
        numFramesSinceStart++;
      }
      try {
          Thread.sleep(FRAME_LENGTH);
      } catch (Exception e) {
        // do catch stuff
      }
    }
  }

  public void addClockObserver(ClockListener clockListener) {
    if (!addedClockObservers.containsKey(clockListener)) {
      clockListeners.add(clockListener);
      addedClockObservers.put(clockListener, true);
    }
  }

  public void removeClockObserver(ClockListener clockListener) {
    if (addedClockObservers.containsKey(clockListener)) {
      clockListeners.remove(clockListener);
      addedClockObservers.remove(clockListener);
    }
  }

  public void stop() {
    stopped = true;
  }

  public void pressPause() {
    paused = !paused;
  }

  public boolean getPaused() {
    return paused;
  }
  public int getNumFramesSinceStart() {
    return numFramesSinceStart;
  }

  public void updateClockObservers() {
    for (ClockListener _co : clockListeners) {
      _co.update();
    }
  }
}
