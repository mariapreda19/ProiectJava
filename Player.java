import java.awt.*;

public class Player {
    private String username;
    private int score;
    private int healthPoints;
    private int attackPower;
    private String ability;
    private int positionX;
    private int positionY;
    private int bullets;
    private int dogs;

    // Constructor
    public Player(String username, int score, int healthPoints, int attackPower, String ability, int positionX, int positionY, int bullets) {
        this.username = username;
        this.score = score;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.ability = ability;
        this.positionX = positionX;
        this.positionY = positionY;
        this.bullets = bullets;
        this.dogs = 0;
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

    public int getDogs() {
        return dogs;
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

    // Methods
    public void moveUp() {
        positionY--;
    }

    public void moveDown() {
        positionY++;
    }

    public void moveLeft() {
        positionX--;
    }

    public void moveRight() {
        positionX++;
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

    public static void drawPlayer(Graphics g, Player player) {
        int cellSize = 40;
        g.setColor(Color.RED);
        g.fillOval(player.getPositionX() * cellSize, player.getPositionY() * cellSize, cellSize, cellSize);
    }
}
