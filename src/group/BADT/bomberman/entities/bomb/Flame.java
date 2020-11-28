package group.BADT.bomberman.entities.bomb;

import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.entities.character.enemy.Enemy;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.entities.Entity;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];


	// (x,y): position of fame center.
	// direction: direction of flame.
	// radius: radius of explode.

	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;

		_x = x;
		_y = y;

		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	// creating flame segment each has it own length.
	private void createFlameSegments() {

		// calculate the flame length <=> the flame segment
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		// last: flag for lastest segment

		boolean last = false;
		
		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++) {
			last = i == _flameSegments.length -1 ? true : false;
			
			switch (_direction) {
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}
			_flameSegments[i] = new FlameSegment(x, y, _direction, last);
		}
	}

	// calculate the length of flame, if any
	private int calculatePermitedDistance() {

		int radius = 0;
		int x = (int)_x;
		int y = (int)_y;

		while(radius < _radius) {
			if(_direction == 0) y--;
			if(_direction == 1) x++;
			if(_direction == 2) y++;
			if(_direction == 3) x--;
			
			Entity a = _board.getEntity(x, y, null);
			
			if(a instanceof Bomb) ++radius; //explosion has to be below the bom
			
			if(a.collide(this) == false) //cannot pass through
				break;
			++radius;

		}
		return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {

		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}

		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}


	// processing if bomber or enemy around bomb radius => be killed
	@Override
	public boolean collide(Entity e) {
		if(e instanceof Bomber) ((Bomber) e).kill();
                if(e instanceof Enemy) ((Enemy) e).kill();
                return true;
	}
}
