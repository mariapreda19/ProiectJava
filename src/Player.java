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
    private int bullets;


    private Player(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY, int bullets) {
        this.username = username;
        this.score = score;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.ability = ability;
        this.positionX = positionX;
        this.positionY = positionY;
        this.bullets = bullets;
    }


    public static Player getInstance(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY, int bullets) {
        if (instance == null) {
            instance = new Player(username, score, healthPoints, attackPower, ability, positionX, positionY, bullets);
        }
        return instance;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
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

    public int getBullets() {
        return bullets;
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

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }


    public void shoot() {
        bullets--;
    }

    public void reload() {
        bullets = 3;
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
            ImageIcon icon = new ImageIcon(Player.class.getResource("../img/player1.png"));
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
