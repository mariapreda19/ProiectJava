import java.awt.Color;
import java.awt.Graphics;
public class MotoDog extends Dog{
    //each dog heals the player when collected
    private int healPower;

    private int points;

    private String ability;

    public MotoDog(String name, int possitionX, int posstionY, int healPower, int points, String ability){
        super(name, possitionX, posstionY);
        this.healPower = healPower;
        this.points = points;
        this.ability = ability;
    }

    //getters

    public int getHealPower(){
        return healPower;
    }

    public int getPoints(){
        return points;
    }

    public String getAbility(){
        return ability;
    }

    //methods

    public void beingCollected(Player player){
        player.setHealthPoints(player.getHealthPoints() + healPower);
        player.setScore(player.getScore() + points);
        collected = true;
    }

    // Draw the dog

    public void drawDog(Graphics g, MotoDog dog) {
        int cellSize = 40; // Size of each cell in pixels
        g.setColor(Color.YELLOW);
        int[] position = {getPositionX(), getPositionY()};
        g.fillOval(position[0] * cellSize, position[1] * cellSize, cellSize, cellSize);
    }

}
