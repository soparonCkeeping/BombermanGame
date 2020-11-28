package group.BADT.bomberman.entities.character;

import java.util.ArrayList;

import group.BADT.bomberman.GameLauncher;
import group.BADT.bomberman.entities.character.enemy.Enemy;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.entities.bomb.Bomb;
import group.BADT.bomberman.util.Keyboard;

import java.util.Iterator;
import java.util.List;
import group.BADT.bomberman.entities.LayeredEntity;
import group.BADT.bomberman.entities.bomb.Flame;
import group.BADT.bomberman.entities.tile.item.Item;
import group.BADT.bomberman.level.PosTypeTrans;
import group.BADT.bomberman.ThemeAndSound.MusicStuff;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    public static List<Item> _items = new ArrayList<Item>();

    // if _timeBetweenPutBombs < 0 => set bomber are available.
    // _timeBetweenBombs decrease through looped update() method.

    protected int _timeBetweenPutBombs = 0;


    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();

        // first state of bomber
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            // if bomber was killed => convert to after killed state.
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();
        calculateMove();
        detectPlaceBomb();

    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    // check if this position is available for setting bomb. if yes let bomb position is the bomberman's position.

    private void detectPlaceBomb() {

        // getBombRate: the number of bomb can set at this moment.
        // after set bomb _timeBetweenBombs will be set to 0;


        if(_input.space && TimeLooping.getBombRate() > 0 && _timeBetweenPutBombs < 0) {
			
			int xt = PosTypeTrans.pixelToTile(_x + _sprite.getSize() / 2);
			int yt = PosTypeTrans.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() ); //subtract half player height and minus 1 y position
			
			placeBomb(xt,yt);
			TimeLooping.addBombRate(-1);
			
			_timeBetweenPutBombs = 30;
		}
    }

    // construct a ner bomb.
    protected void placeBomb(int x, int y) {

        Bomb b = new Bomb(x, y, _board);
	    _board.addBomb(b);
        MusicStuff.play("BOM_SET");
    }

    private void clearBombs() {

        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                TimeLooping.addBombRate(1);
            }
        }
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        GameLauncher.sound.stop();
        GameLauncher.sound.loadMusic("lose");
        GameLauncher.sound.play();
        MusicStuff.play("die");

    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            System.out.println("helloo");
            _board.endGame();

        }
    }

    @Override
    protected void calculateMove() {

        int xa = 0, ya = 0;
		if(_input.up) ya--;
		if(_input.down) ya++;
		if(_input.left) xa--;
		if(_input.right) xa++;
		
		if(xa != 0 || ya != 0)  {
			move(xa * TimeLooping.getBomberSpeed(), ya * TimeLooping.getBomberSpeed());
			_moving = true;
		} else {
			_moving = false;
		}
    }

    @Override
    public boolean canMove(double x, double y) {

       for (int c = 0; c < 4; c++) { //colision detection for each corner of the player

			double xt = ((_x + x) + c % 2 * 9) / TimeLooping.TILES_SIZE; //divide with tiles size to pass to tile coordinate

			double yt = ((_y + y) + c / 2 * 10 - 13) / TimeLooping.TILES_SIZE; //these values are the best from multiple tests.....!!||>
			
			Entity a = _board.getEntity(xt, yt, this);
			
			if(!a.collide(this))
				return false;
		}
		
		return true;
    }

    @Override
    public void move(double xa, double ya) {
        if(xa > 0) _direction = 1;
		if(xa < 0) _direction = 3;
		if(ya > 0) _direction = 2;
		if(ya < 0) _direction = 0;
		if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
			_y += ya;
		}
		if(canMove(xa, 0)) {
			_x += xa;
		}
    }


    // processing while collide with flame and enemy
    @Override
    public boolean collide(Entity e) {

        if(e instanceof Flame){
            this.kill();
            return false;
        }
        if(e instanceof Enemy){
            this.kill();
            return true;
        }
        if( e instanceof LayeredEntity) return(e.collide(this));
        return true;
    }

    //sprite
    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
