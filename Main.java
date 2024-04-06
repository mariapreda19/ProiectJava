import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[][] layout = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        List<Dog> dogPositions = new ArrayList<>();
        // Example dog positions
        dogPositions.add(new ClassicDog("Buddy", 1, 2, 10, 100)); // Example ClassicDog position
        dogPositions.add(new MotoDog("Rex", 3, 3, 15, 150, "Speed Boost")); // Example MotoDog position

        // Convert List<Dog> to List<Point>
        List<Point> dogPoints = new ArrayList<>();
        for (Dog dog : dogPositions) {
            dogPoints.add(new Point(dog.getPositionX(), dog.getPositionY()));
        }

        List<Point> bulletPositions = new ArrayList<>();
        bulletPositions.add(new Point(1, 3)); // Example bullet position

        Player player = new Player("Player", 1, 1, 0, "", 1, 1, 0); // Initial player position

        Map map = new Map(layout, dogPoints, bulletPositions, player, new Level(1, 1, 1, 1, 1), new ArrayList<>());

        // Initialize enemies list
        List<Enemy> enemies = new ArrayList<>();
        // Initialize bullets list
        List<Bullet> bullets = new ArrayList<>();
        // Initialize level
        Level level = new Level(1, 1, 1, 1, 1);
        // Initialize dogs list
        List<Dog> dogs = new ArrayList<>();

        // Create a Game instance
        Game game = new Game(map, player, enemies, bullets, level, dogs);

        // Start the game
        game.startGame();
    }
}
