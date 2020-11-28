/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.BADT.bomberman.entities.character.enemy;

import group.BADT.bomberman.entities.character.enemy.findDir.Direction2;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;

/**
 *
 * @author TUNG318
 */
public class Doll extends Enemy{

    public Doll(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, 0.8, 100);

        _sprite = Sprite.balloom_left1;

        _direct = new Direction2(_board.getBomber(), this);
        _direction = _direct.calculateDirection();

    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                } else {
                    _sprite = Sprite.doll_left1;
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
                } else {
                    _sprite = Sprite.doll_left1;
                }
                break;
        }
    }
}
