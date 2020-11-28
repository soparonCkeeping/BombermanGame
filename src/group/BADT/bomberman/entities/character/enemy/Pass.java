package group.BADT.bomberman.entities.character.enemy;

import group.BADT.bomberman.entities.character.enemy.findDir.Direction1;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Pass extends Enemy {

    public Pass (int x, int y, Board _board) {
        super(x, y, _board, Sprite.Pass_dead, 0.8 , 100);
        _sprite = Sprite.Pass_left1;

        _direct = new Direction1();
        _direction = _direct.calculateDirection();
    }

    @Override
    public void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Pass_right1, Sprite.Pass_right2, Sprite.Pass_right3, _animate, 60);
                }else {
                    _sprite = Sprite.Pass_left1;
                }
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Pass_left1, Sprite.Pass_left2, Sprite.Pass_left3, _animate, 60);
                } else {
                    _sprite = Sprite.Pass_left1;
                }
        }
    }
}
