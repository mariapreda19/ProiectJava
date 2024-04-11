package src;

import java.awt.*;
import java.util.Objects;
import javax.swing.ImageIcon;


public class Wall {
    private final Image DEFAULT_TEXTURE = new ImageIcon(Objects.requireNonNull(getClass().getResource("../img/wall_texture.png"))).getImage();
    private final int positionX;
    private final int positionY;

    private Wall(Builder builder) {
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void drawWall(Graphics g, int cellSize) {
        g.drawImage(DEFAULT_TEXTURE, positionX * cellSize, positionY * cellSize, cellSize, cellSize, null);
    }

    public static class Builder {
        private int positionX;
        private int positionY;


        public Builder positionX(int positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(int positionY) {
            this.positionY = positionY;
            return this;
        }

        public Wall build() {
            return new Wall(this);
        }
    }
}


