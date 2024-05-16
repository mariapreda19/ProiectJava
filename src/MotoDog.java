package src;

import javax.swing.*;
import java.awt.*;

public class MotoDog extends Dog {
    //each dog heals the player when collected
    private int healPower = 10;

    private int points = 5;

    private String ability = " ";


    public MotoDog(String name, int possitionX, int posstionY, String sprite, int healPower, int points, String ability){
        super(name, possitionX, posstionY, sprite);
        this.points = points;
        this.healPower = healPower;
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

/*
    public void beingCollected(Model.Player player){
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
            ImageIcon icon = new ImageIcon(getClass().getResource(dog.sprite));
            Image image = icon.getImage();
            g.drawImage(image, positionX * cellSize, positionY * cellSize, cellSize, cellSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
