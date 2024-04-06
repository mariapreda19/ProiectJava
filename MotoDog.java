import java.awt.Color;
import java.awt.Graphics;
public class MotoDog extends Dog{
    //each dog heals the player when collected
    private final int healPower = 10;

    private final int points = 5;

    private final String ability = " ";

    public MotoDog(String name, int possitionX, int posstionY){
        super(name, possitionX, posstionY);
    }

    //getters
    /*
    public int getHealPower(){
        return healPower;
    }

    public int getPoints(){
        return points;
    }

    public String getAbility(){
        return ability;
    }
*/
    //methods
/*
    public void beingCollected(Player player){
        player.setHealthPoints(player.getHealthPoints() + healPower);
        player.setScore(player.getScore() + points);
        collected = true;
    }
*/
    // Draw the dog

    @Override
    public void drawDog(Graphics g, Dog dog) {
        int cellSize = 40;
        g.setColor(Color.YELLOW);
        g.fillOval(positionX * cellSize, positionY * cellSize, cellSize, cellSize);
    }

}
