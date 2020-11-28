package group.BADT.bomberman.entities.character.enemy;

import group.BADT.bomberman.entities.character.enemy.findDir.Direction2;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Potan extends  Enemy {
    public Potan(int x, int y, Board _board) {
        super(x, y, _board, Sprite.Potan_dead, 0.8, 200);
        _sprite = Sprite.Potan_left1;
        _direct = new Direction2(_board.getBomber(), this);
        _direction = _direct.calculateDirection();
    }

    @Override
    public void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Potan_right1, Sprite.Potan_right2, Sprite.Potan_right3, _animate, 60);
                }else {
                    _sprite = Sprite.Potan_left1;
                }
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Potan_left1, Sprite.Potan_left2, Sprite.Potan_left3, _animate, 60);
                }else {
                    _sprite = Sprite.Potan_left1;
                }
        }
    }
}
