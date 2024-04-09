package src;

public class Level {
    private int levelNumber;
    private int enemiesNumber;
    private int motoDogsNumber;
    private int classicDogsNumber;
    private int bulletsNumber;

    public Level(int levelNumber, int enemiesNumber, int motoDogsNumber, int classicDogsNumber, int bulletsNumber){
        this.levelNumber = levelNumber;
        this.enemiesNumber = enemiesNumber;
        this.motoDogsNumber = motoDogsNumber;
        this.classicDogsNumber = classicDogsNumber;
        this.bulletsNumber = bulletsNumber;
    }

    public int getEnemiesNumber(){
        return enemiesNumber;
    }

    public int getBulletsNumber(){

        return bulletsNumber;
    }

    public void setEnemiesNumber(int enemiesNumber){

        this.enemiesNumber = enemiesNumber;
    }

    public void setBulletsNumber(int bulletsNumber){

        this.bulletsNumber = bulletsNumber;
    }


}
