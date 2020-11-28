package group.BADT.bomberman.util;

public class Vector2f {
    public float x;
    public float y;

    public static float worldX;
    public static float worldY;

    public Vector2f () {
        x = 0;
        y = 0;
    }

    public Vector2f(Vector2f vec) {
        new Vector2f(vec.x, vec.y);
    }

    public Vector2f (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void setWorldVar(float x, float y) {
        worldX = x;
        worldY = y;
    }

    public Vector2f getWorldVar() {
        return new Vector2f(x - worldX, y - worldY);
    }

    @Override
    public String toString() {
        return x + ", " + y ;
    }

}
