package group.BADT.bomberman.level;

import group.BADT.bomberman.TimeLooping;

public class PosTypeTrans {
	
	public static int pixelToTile(double i) {
		return (int)(i / TimeLooping.TILES_SIZE);
	}
	
	public static int tileToPixel(int i) {
		return i * TimeLooping.TILES_SIZE;
	}
	
	public static int tileToPixel(double i) {
		return (int)(i * TimeLooping.TILES_SIZE);
	}
}
