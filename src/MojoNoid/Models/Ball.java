package MojoNoid.Models;

public class Ball {
    private int x, y, XVelocity, YVelocity;
    private int speed; // -1: slow / 0: normal / 1: fast
    public static final int WIDTH=30, HEIGHT=30;
    public static final int[] DEFAULT_X_VELOCITIES = {15,14,12,9,6,3,0}, DEFAULT_Y_VELOCITIES = {6,9,12,14,15,16,17};
    private boolean isFireBall;

    public Ball(int x, int y, int XVelocity, int YVelocity, int speed, boolean isFireBall) {
        this.x = x;
        this.y = y;
        this.XVelocity = XVelocity;
        this.YVelocity = YVelocity;
        this.speed = speed;
        this.isFireBall = isFireBall;
    }
    public Ball(int x, int y){
        this.x = x;
        this.y = y;
        this.XVelocity = DEFAULT_X_VELOCITIES[2];
        this.YVelocity = -1*DEFAULT_Y_VELOCITIES[2];
        this.speed = 0;
        this.isFireBall = false;
    }
    public Ball(){
        this.x = 420;
        this.y = 630;
        this.XVelocity = DEFAULT_X_VELOCITIES[2];
        this.YVelocity = -1*DEFAULT_Y_VELOCITIES[2];
        this.speed = 0;
        this.isFireBall = false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getXVelocity() {
        return XVelocity;
    }
    public int getYVelocity() {
        return YVelocity;
    }
    public int getSpeed() {
        return speed;
    }
    public boolean isFireBall() {
        return isFireBall;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setXVelocity(int XVelocity) {
        this.XVelocity = XVelocity;
    }
    public void setYVelocity(int YVelocity) {
        this.YVelocity = YVelocity;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setFireBall(boolean fireBall) {
        isFireBall = fireBall;
    }
}
