package Scripts;

public class ShellExplosion extends Explosion {
  public ShellExplosion (Vector2 position, GameWorld.Player owner) {
    super(position, owner);

    damage = 10;

    multiSprite = new MultiSprite(GameWorld.loadSprite("Explosion_small_strip6.png"), 6);
    sprite = multiSprite.getSubSprite(0);
    if (sprite != null) {
      trigger = new BoxTrigger(this, new Vector2( sprite.getWidth(), sprite.getHeight() ));
      this.position = Vector2.subtractVectors(position, new Vector2(sprite.getWidth()/2, sprite.getHeight()/2));
    }
  }
}
