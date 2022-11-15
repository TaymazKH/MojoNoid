package MojoNoid.Models.Cells;

public class BlinkingCell extends Cell {
    private boolean isVisible;
    private int toggleTime;

    public BlinkingCell(int x, int y, boolean isVisible, int toggleTime) {
        super(3, x, y);
        this.isVisible = isVisible;
        this.toggleTime = toggleTime;
    }
    public BlinkingCell(int x, int y) {
        super(3, x, y);
        isVisible = true;
        toggleTime=60;
    }

    public boolean isVisible() {
        return isVisible;
    }
    public int getToggleTime() {
        return toggleTime;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public void setToggleTime(int toggleTime) {
        this.toggleTime = toggleTime;
    }

    public void updateTime(){
        toggleTime--;
        if(toggleTime==0){
            isVisible = !isVisible;
            toggleTime=60;
        }
    }
}
