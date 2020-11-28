package group.BADT.bomberman.entities.tile;


import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.entities.Entity;

public class Grass extends Tile {

	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	// e allow any entity can pass through.
	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
