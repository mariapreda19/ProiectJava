package src;

import javax.swing.*;
import java.awt.*;


public class ClassicDog extends Dog {
    private int healPower = 0;

    private int points = 1;


    public ClassicDog(String name, int possitionX, int posstionY, String sprite, int healPower, int points){
        super(name, possitionX, posstionY, sprite);
        this.healPower = healPower;
        this.points = points;
    }

    //getters

    public int getHealPower(){
        return healPower;
    }

    public int getPoints(){
        return points;
    }




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


