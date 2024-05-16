package src;
import java.util.Scanner;
import DatabaseManagement.DatabaseManagement;

public class AuthenticationSystem {
    private static boolean isAuthenticated = false;
    private static String authenticatedUsername;

    public static void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Authentication System!");

        System.out.print("Do you have an account? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            authenticateExistingUser(scanner);
        } else if (response.equalsIgnoreCase("no")) {
            createNewUser(scanner);
        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
        }
    }

    private static void authenticateExistingUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Authenticate user
        if (authenticateUser(username, password)) {
            isAuthenticated = true;
            authenticatedUsername = username;
            System.out.println("Authentication successful. Welcome, " + username + "!");
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
        }
    }

    private static boolean authenticateUser(String username, String password) {
        return DatabaseManagement.getInstance().authenticate(username, password);
    }

    private static void createNewUser(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Check if the username already exists
        if (usernameExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        // Create a new player
        if (createPlayer(username, password)) {
            isAuthenticated = true;
            authenticatedUsername = username;
            System.out.println("Account created successfully. Welcome, " + username + "!");
        } else {
            System.out.println("Failed to create account. Please try again later.");
        }
    }

    private static boolean usernameExists(String username) {
        // Check if the username exists in the database
        return DatabaseManagement.getInstance().usernameExists(username);
    }

    private static boolean createPlayer(String username, String password) {
        // Create a new player with the provided username and password
        return DatabaseManagement.getInstance().createPlayer(username, password);
    }

    public static String isAuthenticated() {
        if (isAuthenticated) {
            return authenticatedUsername;
        } else {
            return "somethingWentWrong";
        }
    }

    public static String getAuthenticatedUsername() {
        return authenticatedUsername;
    }
}


