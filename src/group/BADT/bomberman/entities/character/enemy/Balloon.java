package group.BADT.bomberman.entities.character.enemy;

import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.entities.character.enemy.findDir.Direction1;

public class Balloon extends Enemy {
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, 0.5, 100);
		
		_sprite = Sprite.balloom_left1;
		
		_direct = new Direction1();
		_direction = _direct.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
				break;
		}
	}
}
