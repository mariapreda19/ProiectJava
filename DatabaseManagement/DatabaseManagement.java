package DatabaseManagement;
import oracle.jdbc.datasource.impl.OracleDataSource;
import java.sql.*;
import src.*;
import java.util.ArrayList;
import java.util.List;
import CSVFileWriter.CSVFileWriter;


public class DatabaseManagement {

    private static final String url = "jdbc:oracle:thin:@localhost:1522:xe";
    private static final String username = "c##maria";
    private static final String password = "Mariabd19";
    private static Connection connection;
    private static DatabaseManagement instance = null;

    private DatabaseManagement(){
        try{
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            connection = dataSource.getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static DatabaseManagement getInstance(){
        if(instance == null){
            instance = new DatabaseManagement();
        }
        return instance;
    }


    public int executeUpdate(String q) throws SQLException{
        try {
            PreparedStatement stmt = connection.prepareStatement(q);
            return stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }

    // find player id by username
    public static int findPlayerIdByUsername(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM PLAYERS WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }




    public void insertScore(String username, int score, int levelNumber) {
        try {
            int playerId = findPlayerIdByUsername(username);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO SCORES (user_id, score, level_id) VALUES (?, ?, ?)");
            stmt.setInt(1, playerId);
            stmt.setInt(2, score);
            stmt.setInt(3, levelNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean authenticate(String username, String password) {
        try {
            PreparedStatement stmt = prepareStatement("SELECT * FROM PLAYERS WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLAYERS WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static Connection getConnection() {
        return connection;
    }

    public boolean createPlayer(String username, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PLAYERS (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            CSVFileWriter.writeDatabaseAction("executeInsertForPlayer");
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Level createLevelById(Connection connection, int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM levels WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int levelNumber = resultSet.getInt("id");
                int mapDimension = resultSet.getInt("map_dimension");
                int numberOfEnemies = resultSet.getInt("number_of_enemies");
                return new Level(levelNumber, mapDimension, numberOfEnemies);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Enemy> findEnemiesByLevelId(Connection connection, int levelId) {
        List<Enemy> enemies = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM enemies");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int enemyId = resultSet.getInt("id");
                String sprite = resultSet.getString("sprite");
                int positionX = resultSet.getInt("positionX");
                int positionY = resultSet.getInt("positionY");
                enemies.add(new Enemy(positionX, positionY, sprite));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        Level level = createLevelById(connection, levelId);
        int numberOfEnemies = level.getNumberOfEnemies();
        List<Enemy> randomEnemies = new ArrayList<>();
        for (int i = 0; i < numberOfEnemies; i++) {
            int randomIndex = (int) (Math.random() * enemies.size());
            randomEnemies.add(enemies.get(randomIndex));
        }
        return randomEnemies;
    }


    public static List<ClassicDog> findClassicDogs(Connection connection, int numberOfDogs) {
        List<ClassicDog> classicDogs = new ArrayList<>();
        try {
            String query = "SELECT * FROM dogs INNER JOIN classic_dogs ON dogs.id = classic_dogs.id";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sprite = resultSet.getString("sprite");
                int positionX = resultSet.getInt("positionX");
                int positionY = resultSet.getInt("positionY");
                int healPoints = resultSet.getInt("healPoints");
                int points = resultSet.getInt("points");

                ClassicDog classicDog = new ClassicDog(name, positionX, positionY, sprite, healPoints, points);

                classicDogs.add(classicDog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<ClassicDog> randomDogs = new ArrayList<>();
        for (int i = 0; i < numberOfDogs; i++) {
            int randomIndex = (int) (Math.random() * classicDogs.size());
            randomDogs.add(classicDogs.get(randomIndex));
        }
        return randomDogs;
    }

    public static List<MotoDog> findMotoDogs(Connection connection, int numberOfDogs) {
        List<MotoDog> motoDogs = new ArrayList<>();
        try {
            String query = "SELECT * FROM dogs INNER JOIN moto_dogs ON dogs.id = moto_dogs.id";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sprite = resultSet.getString("sprite");
                int positionX = resultSet.getInt("positionX");
                int positionY = resultSet.getInt("positionY");
                int healPoints = resultSet.getInt("healPoints");
                int points = resultSet.getInt("points");
                String ability = resultSet.getString("ability");

                MotoDog motoDog = new MotoDog(name, positionX, positionY, sprite, healPoints, points, ability);

                motoDogs.add(motoDog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<MotoDog> randomDogs = new ArrayList<>();
        for (int i = 0; i < numberOfDogs; i++) {
            int randomIndex = (int) (Math.random() * motoDogs.size());
            randomDogs.add(motoDogs.get(randomIndex));
        }
        return randomDogs;
    }



    public static void deleteEntity(String table, int id){
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEntity(String table, int id, String column, String value){
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE " + table + " SET " + column + " = ? WHERE id = ?");
            stmt.setString(1, value);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            CSVFileWriter.writeDatabaseAction("executeUpdate");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readEntity(String table, int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
            CSVFileWriter.writeDatabaseAction("executeRead");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertLevel(Level level) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO levels (id, map_dimension, number_of_enemies) VALUES (?, ?, ?)");
            stmt.setInt(1, level.getLevelNumber());
            stmt.setInt(2, level.getMapDimension());
            stmt.setInt(3, level.getNumberOfEnemies());
            stmt.executeUpdate();
            CSVFileWriter.writeDatabaseAction("executeInsertForLevel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertEnemy(Enemy enemy) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO enemies (positionX, positionY, sprite) VALUES (?, ?, ?)");
            stmt.setInt(1, enemy.getPosition()[0]);
            stmt.setInt(2, enemy.getPosition()[1]);
            stmt.setString(3, enemy.getSprite());
            stmt.executeUpdate();
            CSVFileWriter.writeDatabaseAction("executeInsertForEnemy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertClassicDog(ClassicDog classicDog) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO dogs (name, positionX, positionY, sprite) VALUES (?, ?, ?, ?)");
            stmt.setString(1, classicDog.getName());
            stmt.setInt(2, classicDog.getPositionX());
            stmt.setInt(3, classicDog.getPositionY());
            stmt.setString(4, classicDog.getSprite());
            stmt.executeUpdate();
            stmt = connection.prepareStatement("INSERT INTO classic_dogs (healPoints, points) VALUES (?, ?)");
            stmt.setInt(1, classicDog.getHealPower());
            stmt.setInt(2, classicDog.getPoints());
            stmt.executeUpdate();
            CSVFileWriter.writeDatabaseAction("executeInsertForClassicDog");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
