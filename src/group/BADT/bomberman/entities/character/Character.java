package group.BADT.bomberman.entities.character;

import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.AnimatedEntitiy;


public abstract class Character extends AnimatedEntitiy {
	
	protected Board _board;
	protected int _direction = -1;
	protected boolean _alive = true;
	protected boolean _moving = false;
	public int _timeAfter = 40;
	
	public Character(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);

	 abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);


	public abstract void kill();


	protected abstract void afterKill();


	protected abstract boolean canMove(double x, double y);

	protected double getXMessage() {
		return (_x * TimeLooping.SCALE) + (_sprite.SIZE / 2 * TimeLooping.SCALE);
	}
	
	protected double getYMessage() {
		return (_y* TimeLooping.SCALE) - (_sprite.SIZE / 2 * TimeLooping.SCALE);
	}
	
}
