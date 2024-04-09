package src;

import java.awt.*;

public class Bullet {
    private int x, y; // src.Bullet position
    private int dx, dy; // src.Bullet direction and speed
    private boolean active; // If the bullet is active or not
    private boolean collected; // If the bullet is collected or not

    private int id;
    private static int nextId = 1;

    // Constructor
    public Bullet(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        active = true;
        collected = false;
        id = nextId;
        nextId++;
    }

    // Method to move the bullet
    public void move() {
        x += dx;
        y += dy;
    }

    // Getters

    public int getPositionX() {
        return x;
    }

    public int getPositionY() {
        return y;
    }

    public int[] getDirection() {
        return new int[]{dx, dy};
    }

    public boolean isActive() {
        if (collected) {
            return false;
        }
        return active;
    }

    public boolean isCollected() {
        return collected;
    }

    // Setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    // Methods

    // Method to handle when the bullet misses
    public void missed() {
        active = false;
    }

    // Method to handle when the bullet hits an enemy
    public void hit(Enemy enemy, Player player) {
        enemy.beingShot(player);
        active = false;
    }

    // Method to handle when the bullet is collected by the player
    public void collect(Player player) {
        player.setBullets(player.getBullets() + 1);
        collected = true;
    }

    public static void drawBullets(Graphics g, Bullet bullet) {
        int cellSize = 40; // Size of each cell in pixels
        g.setColor(Color.GREEN);
        if (bullet.isActive()) {
            g.fillOval(bullet.getPositionX() * cellSize, bullet.getPositionY() * cellSize, cellSize, cellSize);
        }
    }
}