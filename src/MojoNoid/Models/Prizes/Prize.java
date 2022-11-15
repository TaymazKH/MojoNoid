package MojoNoid.Models.Prizes;

public class Prize {
    private int type; // 1: fire / 2: multi / 3,4: big,small / 5,6: fast,slow / 7: dizzy / 8: random
    private int x,y;
    public static final int WIDTH=30,HEIGHT=30,SPEED=8;

    public Prize(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getType() {
        return type;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
