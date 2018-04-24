package Scripts;

import javafx.beans.Observable;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Clock implements Runnable
{
  private static Clock instance;
  private int numFramesSinceStart;
  private CopyOnWriteArrayList<ClockObserver> clockObservers;
  private HashMap<ClockObserver, Boolean> addedClockObservers;

  private static final long FRAME_LENGTH = 15;

  public Clock() {
    instance = this;
    numFramesSinceStart = 0;
    clockObservers = new CopyOnWriteArrayList<>();
    addedClockObservers = new HashMap<>();
  }

  public static Clock getInstance() {
    return instance;
  }

  public void run()
  {
    while (true) {
      updateClockObservers();
      try {
        Thread.sleep(FRAME_LENGTH);
      } catch (Exception e) {
        // do catch stuff
      }
    }
  }

  public void addClockObserver(ClockObserver clockObserver) {
    if (!addedClockObservers.containsKey(clockObserver)) {
      clockObservers.add(clockObserver);
      addedClockObservers.put(clockObserver, true);
    }
  }

  public void removeClockObserver(ClockObserver clockObserver) {
    if (addedClockObservers.containsKey(clockObserver)) {
      clockObservers.remove(clockObserver);
      addedClockObservers.remove(clockObserver);
    }
  }

  public int getNumFramesSinceStart() {
    return numFramesSinceStart;
  }

  public void updateClockObservers() {
    for (ClockObserver _co : clockObservers) {
      _co.update();
    }
    numFramesSinceStart++;
  }
}
