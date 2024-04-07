import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;



public class Game {
    private Map map;
    private Player player;
    private List<Enemy> enemies;
    private Level level;
    private JPanel panel; // Reference to the panel
    private boolean gameOver;
    private boolean gameWon;

    public Game(Map map, Player player, List<Enemy> enemies, Level level) {
        this.map = map;
        this.player = player;
        this.enemies = enemies;
        this.level = level;
        this.gameOver = false;
        this.gameWon = false;


        Enemy enemy1 = new Enemy(100, 1, 5, 5);
        enemies.add(enemy1);
    }

    public void update() {
        if (gameOver) return;

        List<Bullet> bulletsCopy = new ArrayList<>(map.getBullets());
        for (Bullet bullet : bulletsCopy) {
            if (bullet.getPositionX() == player.getPositionX() && bullet.getPositionY() == player.getPositionY()) {
                map.removeBullet(bullet);
            }
        }

        List<Dog> dogsCopy = new ArrayList<>(map.getDogPositions());
        for (Dog dog : dogsCopy) {
            if (dog.getPositionX() == player.getPositionX() && dog.getPositionY() == player.getPositionY()) {
                map.removeDog(dog);
                gameWon = allDogsCollected();
            }
        }


        for (Enemy enemy : enemies) {
            enemy.move(player.getPositionX(), player.getPositionY(), map.getLayout());
            enemy.catchPlayer(player);
            if (enemy.getPosition()[0] == player.getPositionX() && enemy.getPosition()[1] == player.getPositionY()) {
                System.out.println("Game Over! You've been caught by an enemy.");
                gameOver = true;
            }
        }

        if (allDogsCollected()) {
            allDogsCollected();
        }
    }

    private boolean allDogsCollected() {
        for (Dog dog : map.getDogPositions()) {
            if (!dog.isCollected()) {
                return false;
            }
        }
        System.out.println("Congratulations! You've collected all the dogs and won the level.");
        gameOver = true;
        return true;
    }

    public void movePlayer(int dx, int dy) {
        map.movePlayer(player, dx, dy);
    }

    public void shoot(int dx, int dy) {
        if (player.getBullets() > 0) {
            Bullet bullet = new Bullet(player.getPositionX(), player.getPositionY(), dx, dy);
            map.getBullets().add(bullet);
            player.shoot();
        }
    }

    public void startGame() {
        JFrame frame = new JFrame("Simple Map Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel messagePanel = new JPanel();


        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!gameOver && !gameWon) {
                    map.drawMap(g);
                    player.drawPlayer(g, player);
                    for (Dog dog : map.getDogPositions()) {
                        dog.drawDog(g, dog);
                    }
                    for (Enemy enemy : enemies) {
                        enemy.drawEnemies(g, enemy);
                    }
                    for (Bullet bullet : map.getBullets()) {
                        bullet.drawBullets(g, bullet);
                    }
                }
            }
        };

        mainPanel.add(panel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setFocusable(true);

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
                        shoot(0, -1);
                        panel.repaint();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Start the game loop
        while (!gameOver && !gameWon) {
            update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Remove the game panel and display the game message or image in the center
        mainPanel.remove(panel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (gameWon) {
            messagePanel.add(new JLabel("Congratulations! You've collected all the dogs and won the level.", SwingConstants.CENTER));
        } else {
            ImageIcon gameOverImageIcon = new ImageIcon("gameOver.jpg");
            Image gameOverImage = gameOverImageIcon.getImage().getScaledInstance(600, 650, Image.SCALE_DEFAULT);
            ImageIcon resizedGameOverImageIcon = new ImageIcon(gameOverImage);
            JLabel gameOverLabel = new JLabel(resizedGameOverImageIcon);
            messagePanel.add(gameOverLabel);
        }


        mainPanel.add(messagePanel, gbc);
        mainPanel.revalidate();
    }




}
