package Scripts;

public class Timer
{
  private int endFrame;

  public Timer() {
    endFrame = 0;
  }

  public void set(int frames) {
    endFrame = GameWorld.Instance.framesSinceStart + frames;
  }

  public boolean isDone() {
    return GameWorld.Instance.framesSinceStart >= endFrame;
  }
}
