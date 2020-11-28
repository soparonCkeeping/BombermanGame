package group.BADT.bomberman.entities.tile;

import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.level.PosTypeTrans;

public abstract class Tile extends Entity {
	public Tile(int x, int y, Sprite sprite) {
		_x = x;
		_y = y;
		_sprite = sprite;
	}

	@Override
	public boolean collide(Entity e) {
		return false;//khong cho di qua
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( PosTypeTrans.tileToPixel(_x), PosTypeTrans.tileToPixel(_y), this);
	}
	
	@Override
	public void update() {}
}
