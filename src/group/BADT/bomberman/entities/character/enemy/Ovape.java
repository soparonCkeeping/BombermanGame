package group.BADT.bomberman.entities.character.enemy;


import group.BADT.bomberman.entities.character.enemy.findDir.Direction2;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Ovape extends  Enemy {
    public Ovape(int x, int y, Board _board) {
        super(x, y, _board, Sprite.Ovape_dead, 0.8, 200);
        _sprite = Sprite.Ovape_left1;
        _direct = new Direction2(_board.getBomber(), this);
        _direction = _direct.calculateDirection();
    }

    @Override
    public void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Ovape_right1, Sprite.Ovape_right2, Sprite.Ovape_right3, _animate, 60);
                }else {
                    _sprite = Sprite.Ovape_left1;
                }
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.Ovape_left1, Sprite.Ovape_left2, Sprite.Ovape_left3, _animate, 60);
                }else {
                    _sprite = Sprite.Ovape_left1;
                }
        }
    }
}
