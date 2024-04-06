import java.awt.Color;
import java.awt.Graphics;

public class ClassicDog extends Dog{
    private int healPower = 0;

    private int points = 1;


    public ClassicDog(String name, int possitionX, int posstionY){
        super(name, possitionX, posstionY);
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

    @Override
    public void drawDog(Graphics g, Dog dog) {
        int cellSize = 40;
        g.setColor(Color.RED);
        g.fillOval(positionX * cellSize, positionY * cellSize, cellSize, cellSize);
    }


}