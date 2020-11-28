package group.BADT.bomberman.graphics;

import group.BADT.bomberman.util.Vector2f;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.entities.character.Bomber;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Screen {
	protected int _width, _height;
	public int[] _pixels;
	private int _transparentColor = 0xffff00ff;
	
	public static int xOffset = 0, yOffset = 0;

	public static FontSheet fontA = new FontSheet("font/standardofont.png", 10, 10);
	public static FontSheet fontB = new FontSheet("font/bFont.png", 10, 10);
	
	public Screen(int width, int height) {
		_width = width;
		_height = height;
		
		_pixels = new int[width * height];
		
	}
	
	public void clear() {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = 0;
		}
	}
	
	public void renderEntity(int xp, int yp, Entity entity) { //save entity pixels
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp; //add offset
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp; //add offset
				if(xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height) break; //fix black margins
				if(xa < 0) xa = 0; //start at 0 from left
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != _transparentColor) _pixels[xa + ya * _width] = color;
			}
		}
	}
	
	public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if(xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height) break; //fix black margins
				if(xa < 0) xa = 0;
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != _transparentColor) 
					_pixels[xa + ya * _width] = color;
				else
					_pixels[xa + ya * _width] = below.getPixel(x + y * below.getSize());
			}
		}
	}
	
	public static void setOffset(int xO, int yO) {
		xOffset = xO;
		yOffset = yO;
	}
	
	public static int calculateXOffset(Board board, Bomber bomber) {
		if(bomber == null) return 0;
		int temp = xOffset;
		
		double BomberX = bomber.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if( BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
			temp = (int)bomber.getX()  - (TimeLooping.WIDTH / 2);
		}
		
		return temp;
	}
	
	public void drawEndGame(Graphics g, int points) {
		Sprite.drawArray1((Graphics2D) g, fontA, "GAMEOVER!", new Vector2f(235,290), 42, 42, 32, 0);
	}

	public void drawChangeLevel(Graphics g, int level) {
		Sprite.drawArray1((Graphics2D) g, fontA, "LEVEL: " + level, new Vector2f(235, 290), 42, 42, 32, 0 );
		
	}
	
	public void drawPaused(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "PAUSE!!", new Vector2f(235, 290), 42, 42, 32, 0 );
	}

	private BufferedImage loadBack(String file) {
		BufferedImage back = null;
		try {
			back = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
		}catch (Exception e) {

		}
		return back;
	}

	public void drawMainMenu(Graphics g) {
		g.drawImage(loadBack("background/bombermanback.png"), 0,0,TimeLooping.WIDTH * TimeLooping.SCALE, TimeLooping.HEIGHT * TimeLooping.SCALE, null);
	}



	public void drawPlayLetter(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "Play", new Vector2f(235 - 10,290 + 90), 42, 42, 28, 0);
	}

	public void drawPlayLetter1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "> Play", new Vector2f(235 - 10,290 + 90), 42, 42, 28, 0);
	}



	public void drawSetSystemVolume(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "System volume", new Vector2f(235 - 55,290), 42, 42, 28, 0);
	}

	public void drawMusicState(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "Music", new Vector2f(235  + 40, 420), 42, 42, 28, 0);
	}

	public void drawMusicStateOn(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "< On >", new Vector2f(235 +15, 420 + 50), 42, 42, 28, 0);
	}

	public void drawMusicStateOff(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "< Off >", new Vector2f(235 +5, 420  +50), 42, 42, 28, 0);
	}

	public void drawMusicStateOn1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "< 0n >", new Vector2f(235 + 15, 420  +50), 42, 42, 28, 0);
	}

	public void drawMusicStateOff1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "< Off >", new Vector2f(235 + 5, 420  +50), 42, 42, 28, 0);
	}

	public void drawSettingLetter(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "Setting", new Vector2f(235 - 10,390 + 90), 42, 42, 28, 0);
	}

	public void drawSettingLetter1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "> Setting", new Vector2f(235 - 10,390 + 90), 42, 42, 28, 0);
	}

	public void drawHelpLetter(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "Instruction", new Vector2f(225 - 10,340 + 90), 42, 42, 28, 0);
	}

	public void drawHelpLetter1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "> Instruction", new Vector2f(225 - 10,340 + 90), 42, 42, 28, 0);
	}

	public void drawCharacterintro(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontA, "Character", new Vector2f(225  ,290 + 90), 42, 42, 28, 0);
	}


	public void drawCharacter(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "< SBOMBER >", new Vector2f(225 - 30,340 + 90), 42, 42, 30, 0);
	}

	public void drawCharacter1(Graphics g) {
		Sprite.drawArray1((Graphics2D) g, fontB, "< BLACKBBOMBER >", new Vector2f(225 - 100,340 + 90), 42, 42, 30, 0);
	}


	public static int extra = 200;
	public void drawIns1(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press W to move up", new Vector2f(10, extra), 28, 28, 20, 0);
	}

	public void drawIns2(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press S to move down", new Vector2f(10, extra + 50), 28, 28, 20, 0);

	}

	public void drawIns3(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press A to move left", new Vector2f(10, extra + 100), 28, 28, 20, 0);
	}

	public void drawIns4(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press D to move right", new Vector2f(10, extra + 150), 28, 28, 20, 0);
	}
	public void drawIns5(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press SPACE or E to set bomb", new Vector2f(10, extra + 200), 28, 28, 20, 0);
	}
	public void drawIns6(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Press ESCAPE to back", new Vector2f(10, extra + 250), 28, 28, 20, 0);
			}
	public void drawIns7(Graphics g) {
		Sprite.drawArray1((Graphics2D)g, fontB, "Try to set bomb and kill enemy!!!", new Vector2f(10, extra + 300), 28, 28, 20, 0);
	}

	public void drawVolumeModifier(Graphics g, int xModifier) {
		g.setColor(new Color(87, 90, 90));
		g.fillRect(200,   350, 300, 20);
		g.setColor(new Color(240, 176, 0));
		g.fillRect(200,  350, xModifier, 20);
	}

	public void drawVolumeModifier1(Graphics g, int xModifier) {
		g.setColor(new Color(87, 90, 90));
		g.fillRect(200,  350, 300, 20);
		g.setColor(new Color(3, 216, 237));
		g.fillRect(200, 350, xModifier, 20);
	}




	public int getWidth() {
		return _width;
	}
	public int getHeight() {
		return _height;
	}
	public int getRealWidth() {
		return _width * TimeLooping.SCALE;
	}
	public int getRealHeight() {
		return _height * TimeLooping.SCALE;
	}
}
