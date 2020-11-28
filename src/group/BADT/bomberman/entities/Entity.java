package group.BADT.bomberman.entities;

import group.BADT.bomberman.GUI;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.level.PosTypeTrans;


public abstract class Entity implements GUI {

	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;


	@Override
	public abstract void update();


	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return _sprite;
	}


	public abstract boolean collide(Entity e);
	// process the collsion of 2 entities
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}

	public int getXTile() {
		return PosTypeTrans.pixelToTile(_x + _sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return PosTypeTrans.pixelToTile(_y - _sprite.SIZE / 2);
	}
    
}
