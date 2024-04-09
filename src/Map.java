package src;

import src.Bullet;
import src.Dog;
import src.Enemy;
import src.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int[][] layout;
    private List<Dog> dogs;
    private List<Bullet> bullets;
    private Player player;
    private Level level;
    private List<Enemy> enemies;

    public Map(int[][] layout, List<Dog> dogs, List<Bullet> bullets, Player player, Level level, List<Enemy> enemies) {
        this.layout = layout;
        this.dogs = new ArrayList<>(dogs);
        this.bullets = bullets;
        this.player = player;
        this.level = level;
        this.enemies = enemies;
    }

    // Methods to access map data
    public int[][] getLayout() {
        return layout;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    // Method to check if a position is walkable
    public boolean isWalkable(int x, int y) {
        return x >= 0 && x < layout[0].length && y >= 0 && y < layout.length && layout[y][x] == 0;
    }

    // Method to update player position and check for collisions with walls
    public void movePlayer(Player player, int dx, int dy) {
        int newX = player.getPositionX() + dx;
        int newY = player.getPositionY() + dy;

        if (isWalkable(newX, newY)) {
            player.setPositionX(newX);
            player.setPositionY(newY);
        }
    }

    // Getters for positions
    public List<Dog> getDogPositions() {
        return dogs;
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        bullet.setCollected(true);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.setCollected(true);
    }



    public boolean isDogCollected(Point position) {
        for (Dog dog : dogs) {
            if (position.getX() == dog.getPositionX() && position.getY() == dog.getPositionY()) {
                return dog.isCollected();
            }
        }
        return false;
    }

    public void drawMap(Graphics g) {
        int cellSize = 40; // Size of each cell in pixels
        int mapWidth = layout[0].length * cellSize;
        int mapHeight = layout.length * cellSize;

        // Draw gradient background
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(10, 10, 50); // Dark blue
        Color color2 = new Color(20, 20, 100); // Darker blue
        GradientPaint gradient = new GradientPaint(0, 0, color1, mapWidth, mapHeight, color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, mapWidth, mapHeight);

        // Draw walls with metallic texture
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] == 1) {
                    // Enhance wall appearance with metallic texture
                    drawMetallicWall(g, j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }

        // Draw enemies
        for (Enemy enemy : enemies) {
            int[] position = enemy.getPosition();

            g.setColor(Color.YELLOW);
            g.fillRect(position[0] * cellSize, position[1] * cellSize, cellSize, cellSize);
        }

        // Draw dogs
        for (Dog dog : dogs) {
            dog.drawDog(g, dog);
        }

        // Draw bullets
        for (Bullet bullet : bullets) {
            if (!bullet.isCollected()) {
                bullet.drawBullets(g, bullet);
            }
        }

        // Draw light effect
        int lightRadius = 100; // Radius of light effect
        int lightIntensity = 100; // Intensity of light effect
        int lightX = player.getPositionX() * cellSize + cellSize / 2; // X-coordinate of light source
        int lightY = player.getPositionY() * cellSize + cellSize / 2; // Y-coordinate of light source

        // Create radial gradient for light effect
        RadialGradientPaint radialGradient = new RadialGradientPaint(
                lightX, lightY, lightRadius,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 255, 255, lightIntensity), new Color(255, 255, 255, 0)}
        );

        // Set composite to achieve light blending effect
        Composite originalComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2d.setPaint(radialGradient);
        g2d.fillRect(0, 0, mapWidth, mapHeight);
        g2d.setComposite(originalComposite);
    }


    private void drawMetallicWall(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(100, 100, 100)); // Dark gray for base color
        g2d.fillRect(x, y, width, height);

        // Add metallic texture with lines
        g2d.setColor(new Color(150, 150, 150)); // Lighter gray for texture
        for (int i = 0; i < height; i += 5) {
            g2d.drawLine(x, y + i, x + width, y + i);
        }

        g2d.dispose();
    }

}
