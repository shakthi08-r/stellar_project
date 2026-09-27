import java.util.Scanner;

/*
 * Main.java
 * ---------------------------------------------------
 * This is the STARTING point of the program.
 * Its only job is to:
 *   1. Greet the user and ask their name.
 *   2. Create a CampusNavigator object.
 *   3. Hand control over to CampusNavigator.start().
 *
 * Keeping Main.java small makes the program easier to explain:
 * "Main.java starts the app, CampusNavigator.java runs the app."
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================");
        System.out.println("       SATHYABAMA CAMPUS NAVIGATOR");
        System.out.println("=============================================");

        System.out.print("\nEnter your name: ");
        String userName = scanner.nextLine().trim();

        // Basic validation using IF/ELSE: if the user enters nothing,
        // give them a default name so the program does not break.
        if (userName.isEmpty()) {
            userName = "Guest";
        }

        System.out.println("\nWelcome " + userName + "!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();

        // Create the navigator object and start the program.
        CampusNavigator navigator = new CampusNavigator(scanner, userName);
        navigator.start();

        scanner.close();
    }
}
