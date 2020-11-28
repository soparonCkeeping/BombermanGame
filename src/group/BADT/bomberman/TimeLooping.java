package group.BADT.bomberman;

import group.BADT.bomberman.State.GameState;
import group.BADT.bomberman.graphics.FontSheet;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.gui.Frame;
import group.BADT.bomberman.ThemeAndSound.GameTheme;
import group.BADT.bomberman.util.Keyboard;
import group.BADT.bomberman.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

	// creating game looping.
	// call update and render method.
public class TimeLooping extends Canvas {
	public static final int TILES_SIZE = 16,
							WIDTH = TILES_SIZE * (31 / 2),
							HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	public static final String TITLE = "BombermanGame";
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double BOMBERSPEED = 1.0;//toc do bomber
	public static final int TIME = 200;
	public static final int POINTS = 0;
	protected static int SCREENDELAY = 3;
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	public static group.BADT.bomberman.gui.Panel _infoPanel;
	protected int _screenDelay = SCREENDELAY;
	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	private Board _board;
	private Screen screen;
	private group.BADT.bomberman.gui.Frame _frame;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	public static FontSheet font = new FontSheet("font/bFont.png", 10, 10);
	private static boolean soundtracking = true;


	public static GameState gs = new GameState(true, false);;

	public TimeLooping(Frame frame) {
		_frame = frame;
		_frame.setTitle(TITLE);
		screen = new Screen(WIDTH , HEIGHT ) ;
		_input = new Keyboard();
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
	}

	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		_board.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		int time = (int) this.getBoard().getTime();
		int score = (int) this.getBoard().getPoints();

		Sprite.drawArray1((Graphics2D) g, font,  + time/60 +  ":" + time % 60, new Vector2f(0,0), 28,28, 26, 0);
		Sprite.drawArray1((Graphics2D) g, font, "score:" + score, new Vector2f(280, 0), 28, 28, 23, 0);

		_board.renderMessages(g);
		g.dispose();
		bs.show();

		if (_input.escape) {
			gs._menuState = true;
			gs._playState = false;
			GameLauncher.sound.stop();
			soundtracking = true;
			Board._game.pause();
		}

	}

//	public void setTime(int time) {
//		_infoPanel.setTime(time);
//	}
//
//	public void setPoints(int points) {
//		_infoPanel.setPoints(points);
//	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();
		_board.drawScreen(g);
		g.dispose();
		bs.show();
	}

	private void renderMenu() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();
		_board.drawMenu(g);
		g.dispose();
		bs.show();
	}



	private void update() {
		_input.update();
		_board.update();
	}

	public static GameTheme nsound = GameTheme.getInstance();
	
	public void start() {
		_running = true;
		
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while(_running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}

			if (gs._menuState) {
				renderMenu();
			} else {
				gs._menuState = false;

				if (soundtracking) {
					GameLauncher.sound.stop();
//						MusicStuff.play("maintheme");
					nsound.loadMusic("maintheme");
					nsound.play();
					soundtracking = false;
				}

				if(_paused) {
					if(_screenDelay < 0) {
						_board.setShow(-1);
						_paused = false;
					}
					renderScreen();
				} else {
					renderGame();
				}
			}

			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}
	
	public static double getBomberSpeed() {
		return bomberSpeed;
	}
	public static int getBombRate() {
		return bombRate;
	}
	public static int getBombRadius() {
		return bombRadius;
	}
	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	public static void addBombRate(int i) {
		bombRate += i;
	}
	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}
	public Board getBoard() {
		return _board;
	}
	public boolean isPaused() {
		return _paused;
	}
	public void pause() {
		_paused = true;
	}
	public static void setBombRate(int bombRate) {
        TimeLooping.bombRate = bombRate;
    }
    public static void setBombRadius(int bombRadius) {
        TimeLooping.bombRadius = bombRadius;
    }
    public static void setBomberSpeed(double bomberSpeed) {
        TimeLooping.bomberSpeed = bomberSpeed;
    }

}
