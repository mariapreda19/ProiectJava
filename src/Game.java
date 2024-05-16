package src;

import DatabaseManagement.DatabaseManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

enum EndFlag{
    CAUGHT, WON, NO_HEALTH_POINTS, NOFLAGS
}


public class Game {
    private final GameMap map;
    private final Player player;
    private final List<Enemy> enemies;
    private JPanel panel;
    private EndFlag gameOver;

    private boolean playerMoved;

    public Game(GameMap map, Player player) {
        this.map = map;
        this.player = player;
        this.gameOver = EndFlag.NOFLAGS;
        this.playerMoved = false;


        int levelNumber = map.getLevel().getLevelNumber();

        Connection connection = DatabaseManagement.getInstance().getConnection();


        this.enemies = DatabaseManagement.findEnemiesByLevelId(connection, levelNumber);

    }

    public float update(float lastTime, float currentTime) {
        if (gameOver != EndFlag.NOFLAGS) return lastTime;

        if (!playerMoved) return lastTime;

        if (currentTime - lastTime >= 10 && player.getHealthPoints() > 0){
            player.setHealthPoints(player.getHealthPoints() - 10);
            lastTime = currentTime;
        }
        else{
            if (player.getHealthPoints() <= 0){
                gameOver = EndFlag.NO_HEALTH_POINTS;
            }
        }


        List<Dog> dogsCopy = new ArrayList<>(map.getDogPositions());
        for (Dog dog : dogsCopy) {
            if (dog.getPositionX() == player.getPositionX() && dog.getPositionY() == player.getPositionY()) {
                map.removeDog(dog);
                player.collectedADog(dog);
            }
        }


        for (Enemy enemy : enemies) {
            enemy.move(player.getPositionX(), player.getPositionY(), map.getLayout(), map);
            enemy.catchPlayer(player);
            if (enemy.getPosition()[0] == player.getPositionX() && enemy.getPosition()[1] == player.getPositionY()) {
                System.out.println("Model.Game Over! You've been caught by an enemy.");
                gameOver = EndFlag.CAUGHT;
            }
        }

        if (allDogsCollected()) {
            allDogsCollected();
        }

        return lastTime;
    }

    private boolean allDogsCollected() {
        for (Dog dog : map.getDogPositions()) {
            if (!dog.isCollected()) {
                return false;
            }
        }
        System.out.println("Congratulations! You've collected all the dogs and won the level.");
        gameOver = EndFlag.WON;
        return true;
    }

    public void movePlayer(int dx, int dy) {
        int newX = player.getPositionX() + dx;
        int newY = player.getPositionY() + dy;

        if (map.isWalkable(newX, newY)) {
            map.movePlayer(player, dx, dy);
        }
    }


    public void startGame() {
        JFrame frame = new JFrame("Simple src.Map Model.Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int dimension = map.getLayout().length * 40;
        frame.setSize(dimension, dimension);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel messagePanel = new JPanel();


        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gameOver == EndFlag.NOFLAGS) {
                    map.drawMap(g);
                    Player.drawPlayer(g, player);
                    for (Enemy enemy : enemies) {
                        Enemy.drawEnemies(g, enemy);
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
                        playerMoved = true;
                        panel.repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, 1);
                        playerMoved = true;
                        panel.repaint();
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(-1, 0);
                        playerMoved = true;
                        panel.repaint();
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(1, 0);
                        playerMoved = true;
                        panel.repaint();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        float lastTime = 0;
        JPanel statusPanel = new JPanel(new GridLayout(1, 2));
        JLabel scoreLabel = new JLabel("Score: " + player.getScore());
        JLabel healthLabel = new JLabel("Health: " + player.getHealthPoints());
        statusPanel.add(scoreLabel);
        statusPanel.add(healthLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);


        while (gameOver == EndFlag.NOFLAGS) {
            lastTime = update(lastTime, (float) System.currentTimeMillis() / 1000);


            scoreLabel.setText("Score: " + player.getScore());
            healthLabel.setText("Health: " + player.getHealthPoints());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        mainPanel.remove(panel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        finalMesssage(mainPanel, statusPanel, messagePanel, gbc);

    }


    public void finalMesssage(JPanel mainPanel, JPanel statusPanel, JPanel messagePanel, GridBagConstraints gbc){
        mainPanel.remove(statusPanel);

        if (gameOver == EndFlag.WON) {
            messagePanel.add(new JLabel("Congratulations! You've collected all the dogs and won the level.", SwingConstants.CENTER));
            DatabaseManagement.getInstance().insertScore(player.getUsername(), player.getScore(),  map.getLevel().getLevelNumber());
        } else if (gameOver == EndFlag.CAUGHT) {
            messagePanel.add(new JLabel("Game Over! You've been caught by an enemy.", SwingConstants.CENTER));
            DatabaseManagement.getInstance().insertScore(player.getUsername(), player.getScore(),  map.getLevel().getLevelNumber());

        }
        else if (gameOver == EndFlag.NO_HEALTH_POINTS){
            messagePanel.add(new JLabel("Game Over! You've run out of health points.", SwingConstants.CENTER));
        }





        mainPanel.add(messagePanel, gbc);
        mainPanel.revalidate();
    }

}
