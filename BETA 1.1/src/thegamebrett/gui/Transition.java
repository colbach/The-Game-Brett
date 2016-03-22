package thegamebrett.gui;

public class Transition {

    private final double oldX, oldY, newX, newY;

    public Transition(double oldX, double oldY, double newX, double newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public double getNewX() {
        return newX;
    }

    public double getNewY() {
        return newY;
    }
}
