package group.BADT.bomberman;

import group.BADT.bomberman.gui.Frame;
import group.BADT.bomberman.ThemeAndSound.GameTheme;

public class GameLauncher {

	public static GameTheme sound = GameTheme.getInstance();
	public static String filePath = "victory";
	//public static long clipTimePosition;

	public static void main(String[] args) {
			try {
				sound.loadMusic("victory");
				sound.play();
				GameTheme.volumeControl((double) 0.3);
			}catch (Exception e) {
				System.err.println(e);
			}
		new Frame();
	}
}
