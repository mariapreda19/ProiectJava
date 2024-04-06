import java.awt.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;


public class Game {
    private Map map;
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Level level;
    private List<Dog> dogs;

    public Game(Map map, Player player, List<Enemy> enemies, List<Bullet> bullets, Level level, List<Dog> dogs) {
        this.map = map;
        this.player = player;
        this.enemies = enemies;
        this.bullets = bullets;
        this.level = level;
        this.dogs = dogs;

        // Create enemies and add them to the list
        Enemy enemy1 = new Enemy(100, 1, 5, 5); // Example enemy, adjust position and attributes as needed
        enemies.add(enemy1);
    }

    // Method to update the game state
    public void update() {
        // Move enemies
        for (Enemy enemy : enemies) {
            enemy.move(player.getPositionX(), player.getPositionY(), map.getLayout());
            enemy.catchPlayer(player); // Check if enemy catches the player
            // Check if enemy is on the same position as player
            if (enemy.getPosition()[0] == player.getPositionX() && enemy.getPosition()[1] == player.getPositionY()){
                // Player loses if an enemy is on the same position
                System.out.println("Game Over! You've been caught by an enemy.");
                // Implement game over logic here
            }
        }

        // Check win condition: if all dogs are collected
        if (allDogsCollected()) {
            System.out.println("Congratulations! You've collected all the dogs and won the level.");
            // Implement level completion logic here
        }

        // Move bullets, check collisions, etc.
    }

    // Helper method to check if all dogs are collected
    private boolean allDogsCollected() {
        List<Point> dogPositions = map.getDogPositions();
        for (Point dogPosition : dogPositions) {
            if (!map.isDogCollected(dogPosition)) {
                return false;
            }
        }
        return true;
    }

    // Method to handle player movement
    public void movePlayer(int dx, int dy) {
        map.movePlayer(player, dx, dy);
    }

    // Method to handle shooting
    public void shoot(int dx, int dy) {
        // Check if player has enough bullets
        if (player.getBullets() > 0) {
            // Create a bullet in the specified direction
            Bullet bullet = new Bullet(player.getPositionX(), player.getPositionY(), dx, dy);
            bullets.add(bullet);
            player.shoot();
        }
    }

    // Method to start the game
    public void startGame() {
        // Create a JFrame and add the map and player to it
        JFrame frame = new JFrame("Simple Map Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                map.drawMap(g);
                player.drawPlayer(g, player);
                for (Dog dog : dogs) {
                    dog.drawDogs(g, dog);
                }

                for (Enemy enemy : enemies) {
                    enemy.drawEnemies(g, enemy);
                }

                for (Bullet bullet : bullets) {
                    bullet.drawBullets(g, bullet);
                }


                // Add drawing for enemies, bullets, etc. if necessary
            }
        };

        frame.add(panel);
        frame.setVisible(true);
        frame.setFocusable(true); // Set focusable to true so that the frame can receive keyboard events

        // Add keyboard listener for player movement
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        movePlayer(0, -1);
                        panel.repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, 1);
                        panel.repaint();
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(-1, 0);
                        panel.repaint();
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(1, 0);
                        panel.repaint();
                        break;
                    case KeyEvent.VK_SPACE:
                        shoot(0, -1); // Shoot upwards
                        panel.repaint();
                        break;
                    // Add more cases for other directions or actions as needed
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Start the game loop
        while (true) {
            // Update the game state
            update();

            // Repaint the panel to render the updated game state
            panel.repaint();

            // Add a delay to control the game's frame rate
            try {
                Thread.sleep(100); // Adjust the delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
