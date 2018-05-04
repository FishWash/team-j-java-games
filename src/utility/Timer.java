package utility;

public class Timer
{
  private int endFrame;

  public Timer() {
    endFrame = 0;
  }
  public Timer(int frames) {
    this.set(frames);
  }

  public void set(int numFrames) {
    endFrame = Clock.getInstance().getNumFramesSinceStart() + numFrames;
  }

  public boolean isDone() {
    return Clock.getInstance().getNumFramesSinceStart() >= endFrame;
  }
}
