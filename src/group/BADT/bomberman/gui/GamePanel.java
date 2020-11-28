package group.BADT.bomberman.gui;

import group.BADT.bomberman.TimeLooping;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

	private TimeLooping _game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(TimeLooping.WIDTH * TimeLooping.SCALE , TimeLooping.HEIGHT * TimeLooping.SCALE ));


		_game = new TimeLooping(frame);
		add(_game);

		_game.setVisible(true);

		this.setVisible(true);
		setFocusable(true);
	}

	public TimeLooping getGame() {
		return _game;
	}


}
