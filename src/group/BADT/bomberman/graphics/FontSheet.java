package group.BADT.bomberman.graphics;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class FontSheet {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] spriteArray;
    private final int titleSize = 36;
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    public FontSheet(String file) {
        w = titleSize;
        h = titleSize;

        System.out.println("loading: " + file + "....");
        FONTSHEET = loadFont(file);
    }

    public FontSheet(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("loading: " + file + "...");
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wLetter = FONTSHEET.getWidth() /w;
    }

    public void setHeight(int i) {
        h  = i;
        hLetter = FONTSHEET.getHeight() /h;
    }

    public int getWidth() {
        return w;
    }
    public int getHeight() {
        return h;
    }

    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        }catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0 ; y < hLetter; y++) {
                spriteArray[x][y] = getLetter(x,y);
            }
        }
    }

    public BufferedImage getFontSheet() {
        return FONTSHEET;
    }

    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x * w, y * h, w, h);
    }


    public BufferedImage getFont(char letter) {
        int value = letter - 11;
        int x = value % wLetter;
        int y = value / wLetter;
        return getLetter(x, y);
    }
}
