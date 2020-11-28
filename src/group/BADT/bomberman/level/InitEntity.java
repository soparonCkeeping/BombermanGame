package group.BADT.bomberman.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import group.BADT.bomberman.entities.LayeredEntity;
import group.BADT.bomberman.entities.character.Bomber;
import group.BADT.bomberman.entities.character.enemy.*;
import group.BADT.bomberman.entities.tile.Grass;
import group.BADT.bomberman.entities.tile.Portal;
import group.BADT.bomberman.entities.tile.Wall;
import group.BADT.bomberman.entities.tile.destroyable.Brick;
import group.BADT.bomberman.entities.tile.item.FlameItem;
import group.BADT.bomberman.entities.tile.item.SpeedItem;
import group.BADT.bomberman.exceptions.LoadLevelException;
import group.BADT.bomberman.graphics.Screen;
import group.BADT.bomberman.graphics.Sprite;
import group.BADT.bomberman.Board;
import group.BADT.bomberman.TimeLooping;
import group.BADT.bomberman.entities.character.enemy.*;

public class InitEntity extends LevelLoader {


    private static char[][] _map;

    public InitEntity(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        // update map from .txt file.
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");//doc tep luu map
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
                //doc file txt luu vao list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        _level = Integer.parseInt(arrays[0]);
        _height = Integer.parseInt(arrays[1]);
        _width = Integer.parseInt(arrays[2]);
        _map = new char[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                _map[i][j] = list.get(i + 1).charAt(j);
            }
        }
        //gan cac phan tu cho mang
    }

    @Override
    public void createEntities() {
        // TODO: tạo các Entity của màn chơi
        // TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

        // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
        // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int pos = x + y * getWidth();
                char c = _map[y][x];

                switch (c) {
                    // Thêm grass
                    case ' ':
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    // Thêm Wall
                    case '#':
                        _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                        break;
                    // Thêm Portal
                    case 'x':
                        _board.addEntity(pos, new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Portal(x, y, _board, Sprite.portal),
                                new Brick(x, y, Sprite.brick)));
                        break;
                    // Thêm brick
                    case '*':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    // Thêm Bomber
                    case 'p':
                        _board.addCharacter(new Bomber(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    // Thêm balloon
                    case '1':
                        _board.addCharacter(new Balloon(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    // add minvo
                    case '4':
                        _board.addCharacter(new Minvo(PosTypeTrans.pixelToTile(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    // add kondoria
                    case '5':
                        _board.addCharacter(new Kondoria(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x + y *_width, new Grass(x, y, Sprite.grass));
                        break;

                    // add Ovape
                    case '6':
                        _board.addCharacter(new Ovape(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    // add Pass
                    case '7':
                        _board.addCharacter(new Pass(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    // add Potan
                    case '8':
                        _board.addCharacter(new Potan(PosTypeTrans.tileToPixel(x), PosTypeTrans.tileToPixel(y) + TimeLooping.TILES_SIZE, _board));
                        _board.addEntity(x +y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    case 'f':
                        LayeredEntity layerEntity = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick));
                        _board.addEntity(pos, layerEntity);
                        break;

                    case 's':
                        layerEntity = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick)
                                );
                        _board.addEntity(pos, layerEntity);
                        break;
                    case 'b':
                        layerEntity = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick)
                                );
                        _board.addEntity(pos, layerEntity);
                        break;
                    default:
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;

                }
            }
        }
    }
}
