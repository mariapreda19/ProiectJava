package src;

import java.awt.Graphics;



public abstract class Dog {
    protected String name;
    protected int positionX, positionY;
    protected boolean collected;

    public Dog(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.collected = false;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public abstract void drawDog(Graphics g, Dog dog);
}
