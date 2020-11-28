package group.BADT.bomberman.entities.tile;

import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.ThemeAndSound.MusicStuff;

public class Portal extends Tile {
        protected Board _board;
	public Portal(int x, int y,Board board, Sprite sprite) {
		super(x, y, sprite);
                _board = board;
	}
	
	@Override
	public boolean collide(Entity e) {//xu li khi 2 entity va cham
                                          //true cho di qua
                                          //false khong cho di qua
                if(e instanceof Bomber) {
			
			if(_board.detectNoEnemies() == false)
				return false;
			
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(_board.detectNoEnemies()){
					_board.nextLevel();
					MusicStuff.play("escape");
				}
			}
			
			return true;
		}
		
		return true;
	}

}
