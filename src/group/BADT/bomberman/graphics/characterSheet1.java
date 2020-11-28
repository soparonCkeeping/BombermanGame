package group.BADT.bomberman.graphics;

import group.BADT.bomberman.Board;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class characterSheet1 {

    public  String _path;
    public final int SIZE;
    public int[] _pixels;


    public static characterSheet1 tiles = new characterSheet1("/textures/Sbomber.png", 439);
    public static characterSheet1 tiles1 = new characterSheet1("/textures/BlackBbomber.png", 439);

    public characterSheet1(String path, int size) {
        System.out.println(Board.isLetgowithSbomber + " " + Board.letgowithBbomber);
        if (Board.isLetgowithSbomber || Board.letgowithBbomber){
            System.out.println("gheghe");
            _path = "/textures/BlackBbomber.png";
        }else {
            _path = path;
        }
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();

        System.out.println("loading character!!");
    }


    private void load() {
        try {
            URL a = characterSheet1.class.getResource(_path);
            BufferedImage image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, _pixels, 0, w);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}

