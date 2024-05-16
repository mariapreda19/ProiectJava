package src;

import javax.swing.*;
import java.awt.*;

public class Player {
    private static Player instance;
    private String username;
    private int score;
    private int healthPoints;
    private int attackPower;
    private String ability;
    private int positionX;
    private int positionY;
    private String sprite = "../assets/player1.png";


    private Player(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY, String sprite) {
        this.username = username;
        this.score = score;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.ability = ability;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sprite = sprite;
    }

    private Player(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY) {
        this.username = username;
        this.score = score;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.ability = ability;
        this.positionX = positionX;
        this.positionY = positionY;
    }


    public static Player getInstance(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY, int bullets, String sprite) {
        if (instance == null) {
            instance = new Player(username, score, healthPoints, attackPower, ability, positionX, positionY, sprite);
        }
        return instance;
    }

    public static Player getInstance(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY) {
        if (instance == null) {
            instance = new Player(username, score, healthPoints, attackPower, ability, positionX, positionY);
        }
        return instance;
    }

    // Getters
    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public String getAbility() {
        return ability;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }


    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


    public void useAbility() {
        if (ability.equals("heal")) {
            healthPoints += 10;
        }
        if (ability.equals("freeze")) {
            // Freeze the enemies
        }
    }

    public void collectedADog(Dog dog){
        if (dog instanceof ClassicDog){
            healthPoints += ((ClassicDog) dog).getHealPower();
            score += ((ClassicDog) dog).getPoints();
        }
        else{
            healthPoints += ((MotoDog) dog).getHealPower();
            score += ((MotoDog) dog).getPoints();
            ability = ((MotoDog) dog).getAbility();
        }
    }

    public static void drawPlayer(Graphics g, Player player) {
        int cellSize = 40;
        try {
            ImageIcon icon = new ImageIcon(Player.class.getResource(player.sprite));
            Image image = icon.getImage();
            g.drawImage(image, player.getPositionX() * cellSize, player.getPositionY() * cellSize, cellSize, cellSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
