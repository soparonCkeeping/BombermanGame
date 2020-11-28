package group.BADT.bomberman;

import group.BADT.bomberman.entities.Entity;
import group.BADT.bomberman.entities.Message;
import group.BADT.bomberman.entities.bomb.Bomb;
import group.BADT.bomberman.entities.bomb.FlameSegment;
import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.entities.character.Character;
import group.BADT.bomberman.exceptions.LoadLevelException;
import group.BADT.bomberman.graphics.FontSheet;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.level.InitEntity;
import group.BADT.bomberman.level.LevelLoader;
import group.BADT.bomberman.ThemeAndSound.MusicStuff;
import group.BADT.bomberman.ThemeAndSound.GameTheme;
import group.BADT.bomberman.util.Keyboard;
import group.BADT.bomberman.util.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Board implements GUI {

	public static int temps = 0;
	public LevelLoader _levelLoader;
	public static TimeLooping _game;

	public Keyboard _input;
	public Keyboard _menuInput;
	public Screen _screen;
	public Entity[] _entities;
	public List<Character> _characters = new ArrayList<>();
	public List<Bomb> _bombs = new ArrayList<>();
	public List<Message> _messages = new ArrayList<>();
	
	private int _screenToShow = -1;
	private int _time = TimeLooping.TIME;
	private int _points = TimeLooping.POINTS;

	public static FontSheet fonta = new FontSheet("font/bFont.png", 10, 10);

	public boolean playIs = false;
	public boolean instructionIs = false;
	public boolean settingIs = false;
	public boolean checkright = false;
	public static boolean letgowithBbomber = false;
	public static boolean isLetgowithSbomber = false;
	public boolean playIsSoundCheck = true;
	public boolean isInstructionIsSoundcheck = true;


		public Board(TimeLooping game, Keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		loadLevel(1);
		 //start in level 1
	}
	
	@Override
	public void update() {
		if( _game.isPaused() ) return;
		
		updateEntities();
		updateCharacters();
		updateBombs();
		updateMessages();
		detectEndGame();
		
		for (int i = 0; i < _characters.size(); i++) {
			Character a = _characters.get(i);
			if(a.isRemoved()) _characters.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;
		
		//only render the visible part of screen
		int x0 = Screen.xOffset >> 4; //tile precision, -> left X
		int x1 = (Screen.xOffset + screen.getWidth() + TimeLooping.TILES_SIZE) / TimeLooping.TILES_SIZE; // -> right X
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight()) / TimeLooping.TILES_SIZE; //render one tile plus to fix black margins
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				_entities[x + y * _levelLoader.getWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderCharacter(screen);
		
	}
	
	public void nextLevel() {
                TimeLooping.setBombRadius(1);
                TimeLooping.setBombRate(1);
                TimeLooping.setBomberSpeed(1.0);
		loadLevel(_levelLoader.getLevel() + 1);
	}
	
	public void loadLevel(int level) {
		_time = TimeLooping.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_bombs.clear();
		_messages.clear();
		System.out.println("for level");
		try {
			_levelLoader = new InitEntity(this, level);
			_entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth()];
			
			_levelLoader.createEntities();
		} catch (LoadLevelException e) {
			endGame();
		}
	}
	
	protected void detectEndGame() {
		if(_time <= 0)
			endGame();
	}
	
	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();

	}
	
	public boolean detectNoEnemies() {// phat hien enemies
		int total = 0;
		for (int i = 0; i < _characters.size(); i++) {
			if(_characters.get(i) instanceof Bomber == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points);
				break;
			case 2:
				_screen.drawChangeLevel(g, _levelLoader.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
		}
	}

	public void drawMenu(Graphics g) {
		_screen.drawMainMenu(g);
		_screen.drawHelpLetter(g);
		_screen.drawPlayLetter1(g);
		_screen.drawSettingLetter(g);
		_menuInput = this.getInput();

		if (_menuInput.down && temps >= 0 && temps <= 5 && playIs == false && instructionIs == false && settingIs == false) {
			temps ++;
		}else if (_menuInput.up && temps <= 6 && temps >= 1 &&playIs == false && instructionIs == false && settingIs == false) {
			temps--;
		}

		if (temps >= 0 && temps <= 2 ) {
			drawMenu1(g);
			//MusicStuff.play("element");

		}else if (temps >= 3 && temps <= 4 ) {
			drawMenu2(g);
			//MusicStuff.play("element");

		}else {
			drawMenu3(g);
			//MusicStuff.play("element");
		}
	}

	public void drawMenu1 (Graphics g) {
		_screen.drawMainMenu(g);
		_screen.drawHelpLetter(g);
		_screen.drawPlayLetter1(g);
		_screen.drawSettingLetter(g);

		if (_menuInput.space) {
			playIs = true;
			System.out.println(playIsSoundCheck);
			playIsSoundCheck = true;
			instructionIs = false;
			settingIs = false;
		}
		if (playIs) {
			drawCharacter(g);
			if (playIsSoundCheck) {
				MusicStuff.play("selected");
				System.out.println("sound check character");
				playIsSoundCheck = false;
			}
		}
	}


	public void drawCharacter(Graphics g) {
		_screen.drawMainMenu(g);
		_screen.drawCharacter(g);
		if (_menuInput.right) {
			checkright = true;
		}
		if (_menuInput.left) {
			checkright = false;
		}
		if (checkright) {
			_screen.drawMainMenu(g);
			_screen.drawCharacterintro(g);
			_screen.drawCharacter1(g);
			if (_menuInput.enter) {
				letgowithBbomber = true;
				isLetgowithSbomber = false;
				MusicStuff.play("starter");

			}
			if (letgowithBbomber) {
				_game.gs._menuState = false;
				Sprite.characterConverter();

				//MusicStuff.play("starter");
			}
		}else {
			_screen.drawMainMenu(g);
			_screen.drawCharacterintro(g);
			_screen.drawCharacter(g);
			if (_menuInput.enter) {
				isLetgowithSbomber = true;
				letgowithBbomber = false;
				MusicStuff.play("starter");
			}
			if (isLetgowithSbomber) {
				_game.gs._menuState = false;

			}
		}
		if (_menuInput.escape) {
			playIs = false;
			isInstructionIsSoundcheck = true;
			playIsSoundCheck = true;
			instructionIs = false;
		}
	}

	public void drawMenu2 (Graphics g) {
		_screen.drawMainMenu(g);
		_screen.drawHelpLetter1(g);
		_screen.drawPlayLetter(g);
		_screen.drawSettingLetter(g);
		if (_menuInput.space) {
			instructionIs = true;
			playIs = false;
			settingIs = false;
			isInstructionIsSoundcheck = true;
		}
		if (instructionIs) {
			drawInstruction(g);
			if(isInstructionIsSoundcheck) {
				MusicStuff.play("selected");
				System.out.println("sound check instruction");
				isInstructionIsSoundcheck = false;
			}
		}
	}

	public void drawInstruction(Graphics g) {
			_screen.drawMainMenu(g);
			_screen.drawIns1(g);
			_screen.drawIns2(g);
			_screen.drawIns3(g);
			_screen.drawIns4(g);
			_screen.drawIns5(g);
			_screen.drawIns6(g);
			_screen.drawIns7(g);
			if(_menuInput.escape) {
				instructionIs = false;
				isInstructionIsSoundcheck = true;
				playIsSoundCheck = true;
			}
	}


	public boolean settingSoundCheck = false;

	public void drawMenu3 (Graphics g) {
		_screen.drawMainMenu(g);
		_screen.drawHelpLetter(g);
		_screen.drawPlayLetter(g);
		_screen.drawSettingLetter1(g);
		if (_menuInput.space) {
			settingIs = true;
			settingSoundCheck = true;
		}
		if (settingIs){
			drawSetting(g);
			if (settingSoundCheck) {
				MusicStuff.play("selected");
				settingSoundCheck = false;
			}
		}
	}

	public static int xModifier = 90;
	public boolean isMusic = false;

	public void drawSetting (Graphics g) {
			_screen.drawMainMenu(g);
			_screen.drawSetSystemVolume(g);
			_screen.drawMusicState(g);

			if (isMusicOn) {
				_screen.drawMusicStateOn1(g);
			}else {
				_screen.drawMusicStateOff1(g);
			}
			if (_menuInput.down){
				isMusic = true;
			}else if (_menuInput.up){
				isMusic = false;
			}

			if (isMusic) {
				drawSetting2(g, xModifier);
			}else {
				if (_menuInput.left && xModifier > 0) {
					xModifier -= 30;
					GameTheme.volumeDownControl(0.1);
					MusicStuff.play("BOM_SET");
				} else if (_menuInput.right && xModifier < 300) {
					xModifier += 30;
					GameTheme.volumeUpControl(0.1);
					MusicStuff.play("BOM_SET");
				}
				_screen.drawVolumeModifier(g, xModifier);
			}

			if (_menuInput.escape) {
				settingIs = false;
				playIs = false;
				instructionIs = false;
				settingSoundCheck = true;
			}
	}


	public boolean isMusicOn = true;

	public void drawSetting2 (Graphics g, int xModifier){
		_screen.drawMainMenu(g);
		_screen.drawSetSystemVolume(g);
		_screen.drawMusicState(g);
		_screen.drawVolumeModifier1(g, xModifier);

		if (_menuInput.right) {
			isMusicOn = false;
		}else if (_menuInput.left){
			isMusicOn = true;
		}
		if (isMusicOn) {
			_screen.drawMusicStateOn(g);
			GameLauncher.sound.musicSate = true;
			TimeLooping.nsound.musicSate = true;
			GameLauncher.sound.play();
		}
		else  {
			_screen.drawMusicStateOff(g);
			GameLauncher.sound.musicSate = false;
			TimeLooping.nsound.musicSate = false;
			GameLauncher.sound.stop();
		}

		if (_menuInput.escape) {
			settingIs = false;
			playIs = false;
			instructionIs = false;
			settingSoundCheck = true;
		}
	}

	
	public Entity getEntity(double x, double y, Character m) {
		
		Entity res = null;
		
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return _bombs;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}

	public Bomber getBomber() {
		Iterator<Character> itr = _characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Bomber)
				return (Bomber) cur;
		}
		
		return null;
	}
	
	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = _characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameSegment getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			FlameSegment e = b.flameAt(x, y);
			if(e != null) {
				return e;
			}
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return _entities[(int)x + (int)y * _levelLoader.getWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addCharacter(Character e) {
		_characters.add(e);
	}
	
	public void addBomb(Bomb e) {
		_bombs.add(e);
	}
	
	public void addMessage(Message e) {
		_messages.add(e);
	}

	protected void renderCharacter(Screen screen) {
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}




	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
				Sprite.drawArray1((Graphics2D) g, fonta, m.getMessage(), new Vector2f((int)m.getX() - Screen.xOffset * TimeLooping.SCALE, (int) m.getY() ),20, 20, 13, 0 );
		}
	}
	
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}
	
	protected void updateCharacters() {
		if( _game.isPaused() ) return;
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;
		Iterator<Bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().update();
	}
	
	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		int left;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				_messages.remove(i);
		}
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public Keyboard getInput() {
		return _input;
	}

	public LevelLoader getLevel() {
		return _levelLoader;
	}

	public TimeLooping getGame() {
		return _game;
	}

	public int getShow() {return _screenToShow;}

	public int getPoints() {
		return _points;
	}

	public void setShow(int i) {
			_screenToShow = i;
	}
	public int getTime() {
			return _time;
	}

	public void addPoints(int points) {
		this._points += points;
	}
	
	public int getWidth() {
		return _levelLoader.getWidth();
	}

	public int getHeight() {
		return _levelLoader.getHeight();
	}
	
}
