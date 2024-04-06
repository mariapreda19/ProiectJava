import java.awt.*;

public class Dog {
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

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    //abstract method for drawing the dog
    public static void drawDogs(Graphics g, Dog dog) {
        int cellSize = 40; // Size of each cell in pixels
        g.setColor(Color.PINK);
        int[] position = {dog.getPositionX(), dog.getPositionY()};
        g.fillOval(position[0] * cellSize, position[1] * cellSize, cellSize, cellSize);
    }
}
