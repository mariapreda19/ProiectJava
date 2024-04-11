package src;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[][] layout = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        List<Dog> dogPositions = new ArrayList<>();
        dogPositions.add(new ClassicDog("Buddy", 1, 2));
        dogPositions.add(new MotoDog("Rex", 7, 7));
        dogPositions.add(new ClassicDog("Buddy", 5, 2));

        List<Bullet> bulletPositions = new ArrayList<>();
        bulletPositions.add(new Bullet(2, 2, 1, 0));

        Player player = Player.getInstance("Maria", 0, 100, 10, "classic", 1, 1, 0);


        GameMap map = new GameMap(layout, dogPositions, bulletPositions, player, new Level(1, 1, 1, 1, 1), new ArrayList<>());

        List<Enemy> enemies = new ArrayList<>();
        Level level = new Level(1, 1, 1, 1, 1);

        Game game = new Game(map, player, enemies, level);

        game.startGame();
    }

}
