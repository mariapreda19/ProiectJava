import java.awt.Color;
import java.awt.Graphics;

public class ClassicDog extends Dog{
    private final int healPower;

    private final int points;

    public ClassicDog(String name, int possitionX, int posstionY, int healPower, int points){
        super(name, possitionX, posstionY);
        this.healPower = healPower;
        this.points = points;
    }

    //getters

    /*public int getHealPower(){
        return healPower;
    }

    public int getPoints(){
        return points;
    }

    //methods

    public void beingCollected(Player player) {
        player.setHealthPoints(player.getHealthPoints() + healPower);
        player.setScore(player.getScore() + points);
        collected = true;
    }*/

    // Draw the dog

    public void drawDog(Graphics g, ClassicDog dog) {
        int cellSize = 40;
        g.setColor(Color.PINK);
        int[] position = {getPositionX(), getPositionY()};
        g.fillOval(position[0] * cellSize, position[1] * cellSize, cellSize, cellSize);
    }


}