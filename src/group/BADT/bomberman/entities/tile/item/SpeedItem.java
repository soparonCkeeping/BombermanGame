package group.BADT.bomberman.entities.tile.item;

import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.ThemeAndSound.MusicStuff;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {

            if (e instanceof Bomber) {
                
                MusicStuff.play("Item");
                TimeLooping.addBomberSpeed(0.5);
                remove();
            }
        return false;
	}
}
