package MojoNoid.Models;

public class Paddle {
    private int x,y,width,speed;
    public static final int HEIGHT=40;

    public Paddle(int x, int y, int width, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.speed = speed;
    }
    public Paddle(){
        this.x = 372;
        this.y = 660;
        this.width = 125;
        this.speed = 18;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getSpeed() {
        return speed;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
