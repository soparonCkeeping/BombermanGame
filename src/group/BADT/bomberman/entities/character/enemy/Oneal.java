package group.BADT.bomberman.entities.character.enemy;


import group.BADT.bomberman.entities.character.enemy.findDir.Direction1;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Oneal extends Enemy {
	//private Random random = new Random();
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, 0.8 , 100);
		
		_sprite = Sprite.balloom_left1;
		
		_direct = new Direction1();
		_direction = _direct.calculateDirection();
                //this._speed += random.nextDouble()/2;
                
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
}
