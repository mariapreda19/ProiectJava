package src;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GameMap {
    private final int[][] layout;
    private final List<Dog> dogs;
    private final Player player;
    private final Level level;
    private final Set<Wall> walls = new HashSet<>();

    public GameMap(List<Dog> dogs, Player player, Level level) {
        int[][] layoutForMap = new int[level.getMapDimension()][level.getMapDimension()];
        for (int i = 0; i < level.getMapDimension(); i++) {
            for (int j = 0; j < level.getMapDimension(); j++) {
                if (i == 0 || j == 0 || i == level.getMapDimension() - 1 || j == level.getMapDimension() - 1) {
                    layoutForMap[i][j] = 1;
                } else {
                    layoutForMap[i][j] = 0;
                }
            }
        }
        this.layout = layoutForMap;

        this.dogs = dogs;
        this.player = player;
        this.level = level;

        for (int i = 0; i< 15; i++) {
            boolean noCollision;
            int x;
            int y;
            do {
                noCollision = true;
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                for (Dog dog : dogs) {
                    if (dog.getPositionX() == x && dog.getPositionY() == y) {
                        noCollision = false;
                        break;
                    }
                }

            } while (!noCollision);

            Wall wall = new Wall.Builder()
                    .positionX(x)
                    .positionY(y)
                    .build();

            walls.add(wall);
        }
    }

    public int[][] getLayout() {
        return layout;
    }

    public Level getLevel() {
        return level;
    }


    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= layout[0].length || y < 0 || y >= layout.length) {
            return false;
        }
        for (Wall wall : walls) {
            if (wall.getPositionX() == x && wall.getPositionY() == y) {
                return false;
            }
        }
        return layout[y][x] == 0;
    }


    public void movePlayer(Player player, int dx, int dy) {
        int newX = player.getPositionX() + dx;
        int newY = player.getPositionY() + dy;

        if (isWalkable(newX, newY)) {
            player.setPositionX(newX);
            player.setPositionY(newY);
        }
    }

    public List<Dog> getDogPositions() {
        return dogs;
    }


    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.setCollected(true);
    }

    public void drawMap(Graphics g) {
        int cellSize = 40;
        int mapWidth = layout[0].length * cellSize;
        int mapHeight = layout.length * cellSize;


        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(244, 164, 96);
        Color color2 = new Color(160, 82, 45);
        GradientPaint gradient = new GradientPaint(0, 0, color1, mapWidth, mapHeight, color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, mapWidth, mapHeight);

        // Draw walls
        for (Wall wall : walls) {
            wall.drawWall(g, cellSize);
        }

        // Draw metallic walls
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] == 1) {
                    drawMetallicWall(g, j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }

        // Draw dogs
        for (Dog dog : dogs) {
            dog.drawDog(g, dog);
        }


        // Draw player
        player.drawPlayer(g, player);


        int lightRadius = 100;
        int lightIntensity = 100;
        int lightX = player.getPositionX() * cellSize + cellSize / 2;
        int lightY = player.getPositionY() * cellSize + cellSize / 2;


        RadialGradientPaint radialGradient = new RadialGradientPaint(
                lightX, lightY, lightRadius,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 255, 255, lightIntensity), new Color(255, 255, 255, 0)}
        );


        Composite originalComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2d.setPaint(radialGradient);
        g2d.fillRect(0, 0, mapWidth, mapHeight);
        g2d.setComposite(originalComposite);
    }

    private void drawMetallicWall(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(100, 100, 100));
        g2d.fillRect(x, y, width, height);


        g2d.setColor(new Color(240, 150, 150));
        for (int i = 0; i < height; i += 5) {
            g2d.drawLine(x, y + i, x + width, y + i);
        }

        g2d.dispose();
    }
}
