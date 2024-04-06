import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int[][] layout;
    private List<Dog> dogs;
    private List<Point> bulletPositions;
    private Player player;
    private Level level;
    private List<Enemy> enemies;

    public Map(int[][] layout, List<Point> dogs, List<Point> bulletPositions, Player player, Level level, List<Enemy> enemies) {
        this.layout = layout;
        this.dogs = new ArrayList<>();
        for (Point dog : dogs) {
            this.dogs.add(new Dog(" ", (int) dog.getX(), (int) dog.getY()));
        }
        this.bulletPositions = bulletPositions;
        this.player = player;
        this.level = level;
        this.enemies = enemies;
    }

    // Methods to access map data
    public int[][] getLayout() {
        return layout;
    }

    public List<Point> getBulletPositions() {
        return bulletPositions;
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

    public void removeDog(Point position) {
        for (Dog dog : dogs) {
            if (position.getX() == dog.getPositionX() && position.getY() == dog.getPositionY()) {
                dog.setCollected(true);
                break;
            }
        }
    }

    // Getters for positions
    public List<Point> getDogPositions() {
        List<Point> dogPositions = new ArrayList<>();
        for (Dog dog : dogs) {
            if (!dog.isCollected()) { // Add only if the dog is not collected
                dogPositions.add(new Point(dog.getPositionX(), dog.getPositionY()));
            }
        }
        return dogPositions;
    }

    public void removeBullet(Point position) {
        bulletPositions.remove(position);
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
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
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
        for (Point dogPosition : getDogPositions()) {
            int x = (int) dogPosition.getX();
            int y = (int) dogPosition.getY();
            g.setColor(Color.BLUE);
            g.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
        }

        // Draw bullets
        for (Point bulletPosition : bulletPositions) {
            int x = (int) bulletPosition.getX();
            int y = (int) bulletPosition.getY();
            g.setColor(Color.GREEN);
            g.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
        }
    }
}
