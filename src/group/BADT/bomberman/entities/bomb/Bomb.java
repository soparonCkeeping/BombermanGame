package group.BADT.bomberman.entities.bomb;

import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.entities.character.Character;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.AnimatedEntitiy;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.level.PosTypeTrans;
import group.BADT.bomberman.ThemeAndSound.MusicStuff;
public class Bomb extends AnimatedEntitiy {
	// time to explode 2 seconds
	protected double _timeToExplode = 120;
	// time during explode
	public int _timeAfter = 20;
	
	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	 
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}


	protected void explode() {
		_exploded = true;
		_allowedToPassThru = true;
		// process if bomber is close to bomb
		Character x = _board.getCharacterAtExcluding((int)_x, (int)_y, null);
                if(x != null){
                    x.kill();
                }
		// creating flames
                _flames = new Flame[4];
                for (int i = 0; i < _flames.length; i++) {
                    _flames[i] = new Flame((int) _x, (int) _y, i, TimeLooping.getBombRadius(), _board);
                }
                MusicStuff.play("bomb");
	}

        public void time_explode() {
		_timeToExplode = 0;
	}


	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null)
				return null;

			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null)
				return e;
		}
		return null;
	}

	@Override
	public boolean collide(Entity e) {

        // process allow bomber come through bomb or not
        
        if(e instanceof Bomber) {
			double diffX = e.getX() - PosTypeTrans.tileToPixel(getX());
			double diffY = e.getY() - PosTypeTrans.tileToPixel(getY());
			
			if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) {
				_allowedToPassThru = false;
			}
			return _allowedToPassThru;
		}

		if(e instanceof Flame ) {
			time_explode();
			return true;
		}
		return false;
	}
}
