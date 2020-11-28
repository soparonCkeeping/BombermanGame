package group.BADT.bomberman.entities.character.enemy;

import group.BADT.bomberman.entities.character.enemy.findDir.Direction1;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Minvo extends Enemy{

    public Minvo(int x, int y, Board board) {
        super(x, y, board, Sprite.minvo_dead, 1, 200);
        _sprite = Sprite.minvo_left1;

        _direct = new Direction1();
        _direction = _direct.calculateDirection();
    }

    @Override
    public void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, 60);
                }  else{
                    _sprite = Sprite.minvo_left1;
                }
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, 60);
                }   else {
                    _sprite = Sprite.minvo_right1;
                }
        }
    }
}
