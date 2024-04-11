package src;

import java.awt.*;

public class Bullet {
    private int x, y;
    private int dx, dy;
    private boolean active;
    private boolean collected;

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
    public void missed() {
        active = false;
    }

    public void hit(Enemy enemy, Player player) {
        enemy.beingShot(player);
        active = false;
    }

    public void collect(Player player) {
        player.setBullets(player.getBullets() + 1);
        collected = true;
    }

    public static void drawBullets(Graphics g, Bullet bullet) {
        int cellSize = 40;
        g.setColor(Color.GREEN);
        if (bullet.isActive()) {
            g.fillOval(bullet.getPositionX() * cellSize, bullet.getPositionY() * cellSize, cellSize, cellSize);
        }
    }
}
