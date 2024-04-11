package src;

import src.Dog;

import javax.swing.*;
import java.awt.*;

public class MotoDog extends Dog {
    //each dog heals the player when collected
    private final int healPower = 10;

    private final int points = 5;

    private final String ability = " ";

    public MotoDog(String name, int possitionX, int posstionY){
        super(name, possitionX, posstionY);
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
/*
    public void beingCollected(src.Player player){
        player.setHealthPoints(player.getHealthPoints() + healPower);
        player.setScore(player.getScore() + points);
        collected = true;
    }
*/
    // Draw the dog

    @Override
    public void drawDog(Graphics g, Dog dog) {
        int cellSize = 40;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("../img/3-removebg-preview.png"));
            Image image = icon.getImage();
            g.drawImage(image, positionX * cellSize, positionY * cellSize, cellSize, cellSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
