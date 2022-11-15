package MojoNoid.Models.Cells;

public class WoodenCell extends Cell {
    private int health;

    public WoodenCell(int x, int y, int health) {
        super(2, x, y);
        this.health = health;
    }
    public WoodenCell(int x, int y) {
        super(2, x, y);
        health=2;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
