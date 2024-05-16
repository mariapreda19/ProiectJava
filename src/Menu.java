package src;
import java.util.Objects;
import java.util.Scanner;
import DatabaseManagement.DatabaseManagement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;




public class Menu {
    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Game!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Authenticate");
            System.out.println("2. Start Game");
            System.out.println("3. CRUD Operations");
            System.out.print("Choose an option: ");

            int firstOption = scanner.nextInt();
            scanner.nextLine();
            String username = "";


            switch (firstOption) {
                case 1:
                    authenticate();
                    break;
                case 2:
                    if (!Objects.equals(AuthenticationSystem.isAuthenticated(), "somethingWentWrong")) {
                        int levelNumber;

                        username = AuthenticationSystem.getAuthenticatedUsername();

                        System.out.print("Enter the level number: ");
                        levelNumber = scanner.nextInt();
                        scanner.nextLine();

                        startGame(username, levelNumber);
                    } else {
                        System.out.println("Please authenticate first.");
                    }
                    break;
                case 3:
                    if (!Objects.equals(AuthenticationSystem.isAuthenticated(), "somethingWentWrong")) {
                        CRUDOperations();
                    } else {
                        System.out.println("Please authenticate first.");
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }




        }
    }

    private void authenticate() {
        AuthenticationSystem.main();

        // Check if the user is authenticated
        if (!Objects.equals(AuthenticationSystem.isAuthenticated(), "somethingWentWrong")) {
            String username = AuthenticationSystem.getAuthenticatedUsername();
        } else {
            System.out.println("Authentication failed. Exiting the application.");
        }

    }

    private void startGame(String username, int levelNumber) {

        Connection connection = DatabaseManagement.getInstance().getConnection();
        Level level = DatabaseManagement.createLevelById(connection, levelNumber);

        if (level != null) {
            List<Dog> dogPositions = new ArrayList<>();

            dogPositions.addAll(DatabaseManagement.findClassicDogs(connection, levelNumber));
            dogPositions.addAll(DatabaseManagement.findMotoDogs(connection, levelNumber));


            Player player = Player.getInstance(username, 0, 100, 10, "classic", 1, 1);
            List<Enemy> enemies = new ArrayList<>();
            GameMap map = new GameMap(dogPositions, player, level);
            Game game = new Game(map, player);
            game.startGame();
        } else {
            System.out.println("Level not found in the database.");
        }
    }

    private void CRUDOperations(){
        System.out.println("CRUD Operations:");
        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Update");
        System.out.println("4. Delete");

        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch(option){
            case 1:
                System.out.println("Create:");
                System.out.println("1. LEVELS");
                System.out.println("2. ENEMIES");
                System.out.println("3. CLASSIC_DOG");

                System.out.print("Choose an option: ");

                switch(scanner.nextInt()){
                    case 1:
                        CRUDCreateLevel();
                        break;
                    case 2:
                        CRUDCreateEnemy();
                        break;
                    case 3:
                        CRUDCreateClassicDog();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                break;
            case 2:
                CRUDRead();
                break;
            case 3:
                CRUDUpdate();
                break;
            case 4:
                CRUDDelete();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void CRUDDelete() {
        System.out.println("Delete:");
        System.out.println("1. LEVELS");
        System.out.println("2. ENEMIES");
        System.out.println("3. CLASSIC_DOG");
        System.out.println("4. PLAYER");

        System.out.print("Choose an option (write the word): ");

        String option = scanner.nextLine();
        scanner.nextLine();

        System.out.print("Enter the id: ");
        int id = scanner.nextInt();

        DatabaseManagement.deleteEntity(option, id);

    }

    private void CRUDUpdate() {
        System.out.println("Update:");
        System.out.println("1. LEVELS");
        System.out.println("2. ENEMIES");
        System.out.println("3. CLASSIC_DOG");
        System.out.println("4. PLAYER");

        System.out.print("Choose an option (write the word): ");

        String option = scanner.nextLine();
        scanner.nextLine();

        System.out.print("Enter the id: ");
        int id = scanner.nextInt();

        System.out.print("Enter the column: ");

        String column = scanner.nextLine();

        System.out.print("Enter the new value: ");

        String value = scanner.nextLine();

        DatabaseManagement.updateEntity(option,  id, column, value);
    }

    private void CRUDRead() {
        System.out.println("Read:");
        System.out.println("1. LEVELS");
        System.out.println("2. ENEMIES");
        System.out.println("3. CLASSIC_DOG");
        System.out.println("4. PLAYER");

        System.out.print("Choose an option (write the word): ");

        String option = scanner.nextLine();
        scanner.nextLine();

        System.out.print("Enter the id: ");
        int id = scanner.nextInt();

        DatabaseManagement.readEntity(option, id);
    }


    void CRUDCreateLevel(){
        System.out.print("Enter the level number: ");
        int levelNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the map_dimension: ");
        int map_dim = scanner.nextInt();

        System.out.print("How many enemies do you want to add? ");
        int numEnemies = scanner.nextInt();

        DatabaseManagement.insertLevel(new Level(levelNumber, map_dim, numEnemies));
    }

    void CRUDCreateEnemy(){
        System.out.print("Enter the positionX: ");
        int positionX = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the positionY: ");
        int positionY = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the sprite: ");
        String sprite = scanner.nextLine();

        DatabaseManagement.insertEnemy(new Enemy(positionX, positionY, sprite));
    }

    void CRUDCreateClassicDog(){
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the positionX: ");
        int positionX = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the positionY: ");
        int positionY = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the sprite: ");
        String sprite = scanner.nextLine();

        System.out.print("Enter the healPower: ");
        int healPower = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the points: ");
        int points = scanner.nextInt();
        scanner.nextLine();

        DatabaseManagement.insertClassicDog(new ClassicDog(name, positionX, positionY, sprite, healPower, points));
    }



}
