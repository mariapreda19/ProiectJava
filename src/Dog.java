package src;

import java.awt.*;

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

    // Getters
    /*public String getName() {
        return name;
    }*/

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isCollected() {
        return collected;
    }

    // Setters
    /*public void setName(String name) {
        this.name = name;
    }*/

    /*public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }*/

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    //abstract method for drawing the dog
    public abstract void drawDog(Graphics g, Dog dog);
}
