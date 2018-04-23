package Scripts;

// Made this just for fun. Adds drag to the controls to make the tanks feel more realistic.
public class FuzzyTankKeyInput extends TankKeyInput
{
  private double fuzzedMoveInput = 0, fuzzedTurnInput = 0;
  private double moveStep = 0.04, turnStep = 0.05;

  public FuzzyTankKeyInput(GameWorld.Player owner) {
    super(owner);
  }

  @Override
  public double getMoveInput() {
    Double _difference = super.getMoveInput() - fuzzedMoveInput;
    fuzzedMoveInput += Math.min(moveStep, Math.max(-moveStep, _difference));
    return fuzzedMoveInput;
  }

  @Override
  public double getTurnInput() {
    Double _difference = super.getTurnInput() - fuzzedTurnInput;
    fuzzedTurnInput += Math.min(turnStep, Math.max(-turnStep, _difference));
    return fuzzedTurnInput;
  }

  public void addFuzzedMoveInput(double amount) {
    fuzzedMoveInput = Math.max(Math.min(fuzzedMoveInput+amount, 1), -1);
  }
}
