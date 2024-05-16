package src;
import java.util.List;

public class Level {
    private int levelNumber;

    private int mapDimension;

    private int numberOfEnemies;

    public Level (int levelNumber, int mapDimension, int numberOfEnemies){
        this.levelNumber = levelNumber;
        this.mapDimension = mapDimension;
        this.numberOfEnemies = numberOfEnemies;
    }


    public int getMapDimension(){
        return mapDimension;
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public int getNumberOfEnemies(){
        return numberOfEnemies;
    }



}
