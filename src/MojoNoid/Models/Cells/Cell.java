package MojoNoid.Models.Cells;

public abstract class Cell {
    private int type; // 1: glass / 2: wood / 3: blinking / 4: invisible / 5: prize
    private int x,y;
    public static final int WIDTH=75,HEIGHT=15;

    public Cell(int type, int x, int y){
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
