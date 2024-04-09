package src;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;

public class ClassicDog extends Dog {
    private int healPower = 0;

    private int points = 1;


    public ClassicDog(String name, int possitionX, int posstionY){
        super(name, possitionX, posstionY);
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
        int cellSize = 200;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("../img/2-removebag-preview.png"));
            Image image = icon.getImage();
            g.drawImage(image, positionX * cellSize, positionY * cellSize, cellSize, cellSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


