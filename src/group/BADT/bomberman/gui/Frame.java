package group.BADT.bomberman.gui;

import group.BADT.bomberman.TimeLooping;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {
	
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private Panel _infopanel;
	private TimeLooping _game;

	public Frame() {
		_containerpane = new JPanel(new BorderLayout());
		_gamepane = new GamePanel(this);
		_infopanel = new Panel(_gamepane.getGame());

		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		_game = _gamepane.getGame();
		add(_containerpane);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		_game.start();
	}
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}

	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}
	
}
