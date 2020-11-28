package group.BADT.bomberman.entities.bomb;

import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.entities.character.enemy.Enemy;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.entities.Entity;


public class FlameSegment extends Entity {

	protected boolean _last;

	// last = yes => this is the last flame.
	// last = no => this is not the last flame so use another sprite for the flame segment.

	public FlameSegment(int x, int y, int direction, boolean last) {
		_x = x;
		_y = y;

		_last = last;

		switch (direction) {
			case 0:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}
	
	@Override
	public void render(Screen screen) {
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		screen.renderEntity(xt, yt , this);
	}
	
	@Override
	public void update() {}


	// process if bomber or enemy collide with any flame segment.
	@Override
	public boolean collide(Entity e) {

            if(e instanceof Bomber) ((Bomber) e).kill();
            if(e instanceof Enemy) ((Enemy) e).kill();
		return true;
	}
	

}