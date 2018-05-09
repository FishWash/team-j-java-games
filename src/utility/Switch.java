package utility;

public class Switch implements ClockListener {
  private boolean on;
  private Timer switchTimer = new Timer();
  private int switchTime;

  public Switch(int switchTime) {
    this.switchTime = switchTime;
  }

  public void update() {
    if (switchTimer.isDone()) {
      on = !on;
      switchTimer.set(switchTime);
    }
  }

  public boolean isOn() {
    return on;
  }
}
