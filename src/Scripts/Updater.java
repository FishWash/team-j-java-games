package Scripts;

import java.util.ArrayList;

// Updater calls update() on every updatable in updatables every frame.
// It runs on a thread.
public class Updater implements Runnable
{
  private final long gameFrameLength = 10;
  private ArrayList<Updatable> updatables;

  public Updater(ArrayList<Updatable> updatables)
  {
    this.updatables = updatables;
  }

  public void run()
  {
    while (true) {
      for (Updatable _u : updatables) {
        _u.update();
        System.out.println(_u + " is being updated!");
      }

      try {
        Thread.sleep(gameFrameLength);
      } catch (Exception e) {
        // do catch stuff
      }
    }
  }

}
