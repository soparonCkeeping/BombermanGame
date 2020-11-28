package group.BADT.bomberman.entities.character.enemy;


import group.BADT.bomberman.entities.character.enemy.findDir.Direction2;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

public class Kondoria extends Enemy{
    public Kondoria (int x, int y, Board _board) {
        super(x, y, _board, Sprite.kondoria_dead, 0.5, 300);
        _sprite = Sprite.kondoria_left1;
        _direct = new Direction2(_board.getBomber(), this);

        _direction = _direct.calculateDirection();
    }

    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3,_animate, 60);
                } else{
                    _sprite = Sprite.kondoria_left1;
                }
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
                }else {
                    _sprite = Sprite.kondoria_left1;
                }
        }
    }
}
