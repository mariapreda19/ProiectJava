package src;

import java.awt.*;



public abstract class Dog {
    protected String name;
    protected int positionX, positionY;
    protected boolean collected;

    protected String sprite = "../assets/3-removebg-preview.png";

    public Dog(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.collected = false;
    }

    public Dog(String name, int positionX, int positionY, String sprite) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.collected = false;
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public String getSprite() {
        return sprite;
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
