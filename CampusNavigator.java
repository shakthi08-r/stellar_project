import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * CampusNavigator.java
 * ---------------------------------------------------
 * This class contains ALL the navigation logic:
 *   - the list of real campus locations
 *   - the simplified 2D map
 *   - the simplified connection network (which block leads to which)
 *   - the main menu loop
 *   - finding a simple route between two locations
 *   - emergency locations
 *   - visited-locations tracking
 *
 * The locations used here are taken from the official Sathyabama
 * campus map (Administrative Zone, Research Zone, Departments &
 * Classrooms zone). The connections between them are a SIMPLIFIED
 * version created only for this academic project - they are NOT
 * claimed to be the official walking routes of the campus.
 */
public class CampusNavigator {

    private Scanner scanner;
    private String userName;

    // 1) ARRAY -> a simple String array holding every valid location name.
    // Used to check "does this location exist?" and to print the list.
    private String[] locationNames = {
            "Reception Block",
            "Administrative Block",
            "Controller of Examination",
            "Central Library",
            "Auditorium Block",
            "Class Room Block 01",
            "CSE Block",
            "ECE Block",
            "IT Block",
            "EEE Block",
            "Mechanical Block",
            "International Research Centre",
            "Research Park",
            "Indian Bank"
    };

    // 2) MAP (first use) -> stores a Location object for every location name.
    // Map<String, Location> lets us quickly look up the full details
    // (like the description) of a location just by typing its name.
    private Map<String, Location> locationDetails = new HashMap<>();

    // 3) MAP (second use) -> stores the simplified connections.
    // Map<String, List<String>> means: for every location name (key),
    // we keep a List of the location names it is directly connected to.
    private Map<String, List<String>> connections = new HashMap<>();

    // 4) 2D ARRAY -> a simple text layout of the campus used only for display.
    private String[][] campusMap = {
            {"Reception Block", "Administrative Block", "Controller of Examination"},
            {"Auditorium Block", "Central Library", "Class Room Block 01"},
            {"CSE Block", "ECE Block", "IT Block"},
            {"EEE Block", "Mechanical Block", "International Research Centre"},
            {"Research Park", "Indian Bank", ""}
    };

    // 5) SET -> stores visited locations. A Set automatically ignores
    // duplicates, so if the user visits "Central Library" three times,
    // it is still stored only ONCE.
    private Set<String> visitedLocations = new HashSet<>();

    // Constructor: sets up all the data described above.
    public CampusNavigator(Scanner scanner, String userName) {
        this.scanner = scanner;
        this.userName = userName;
        setupLocationDetails();
        setupConnections();
    }

    // Fills the locationDetails Map with a Location object for each block.
    private void setupLocationDetails() {
        locationDetails.put("Reception Block",
                new Location("Reception Block", "Main entry and enquiry point of the campus"));
        locationDetails.put("Administrative Block",
                new Location("Administrative Block", "Handles admissions and administrative work"));
        locationDetails.put("Controller of Examination",
                new Location("Controller of Examination", "Handles examination related work"));
        locationDetails.put("Central Library",
                new Location("Central Library", "Main library with books and reading halls"));
        locationDetails.put("Auditorium Block",
                new Location("Auditorium Block", "Used for events, seminars and functions"));
        locationDetails.put("Class Room Block 01",
                new Location("Class Room Block 01", "General classrooms shared by many departments"));
        locationDetails.put("CSE Block",
                new Location("CSE Block", "Department of Computer Science & Engineering"));
        locationDetails.put("ECE Block",
                new Location("ECE Block", "Department of Electronics & Communication Engineering"));
        locationDetails.put("IT Block",
                new Location("IT Block", "Department of Information Technology"));
        locationDetails.put("EEE Block",
                new Location("EEE Block", "Department of Electrical & Electronics Engineering"));
        locationDetails.put("Mechanical Block",
                new Location("Mechanical Block", "Department of Mechanical Engineering"));
        locationDetails.put("International Research Centre",
                new Location("International Research Centre", "Research zone of the campus"));
        locationDetails.put("Research Park",
                new Location("Research Park", "Colonel Dr. Jeppiaar Research Park"));
        locationDetails.put("Indian Bank",
                new Location("Indian Bank", "On-campus bank facility"));
    }

    // Fills the connections Map. Each entry means:
    // "this location is directly connected/reachable from these locations".
    // This is our SIMPLIFIED navigation network for the project.
    private void setupConnections() {
        connections.put("Reception Block",
                Arrays.asList("Administrative Block", "Controller of Examination", "Indian Bank"));
        connections.put("Administrative Block",
                Arrays.asList("Reception Block", "Auditorium Block", "Central Library", "International Research Centre"));
        connections.put("Controller of Examination",
                Arrays.asList("Reception Block"));
        connections.put("Central Library",
                Arrays.asList("Administrative Block", "Class Room Block 01"));
        connections.put("Auditorium Block",
                Arrays.asList("Administrative Block"));
        connections.put("Class Room Block 01",
                Arrays.asList("Central Library", "CSE Block", "ECE Block", "IT Block", "EEE Block", "Mechanical Block"));
        connections.put("CSE Block",
                Arrays.asList("Class Room Block 01"));
        connections.put("ECE Block",
                Arrays.asList("Class Room Block 01"));
        connections.put("IT Block",
                Arrays.asList("Class Room Block 01"));
        connections.put("EEE Block",
                Arrays.asList("Class Room Block 01"));
        connections.put("Mechanical Block",
                Arrays.asList("Class Room Block 01"));
        connections.put("International Research Centre",
                Arrays.asList("Administrative Block", "Research Park"));
        connections.put("Research Park",
                Arrays.asList("International Research Centre"));
        connections.put("Indian Bank",
                Arrays.asList("Reception Block"));
    }

    // ---------------------------------------------------------------
    // MAIN MENU LOOP
    // ---------------------------------------------------------------
    public void start() {
        showCampusMap();

        boolean running = true;

        // LOOP -> keeps showing the menu until the user chooses Exit.
        while (running) {
            printMenu();

            int choice = readMenuChoice(); // uses try-catch internally

            // SWITCH -> decides what to do based on the user's choice.
            switch (choice) {
                case 1:
                    findLocation();
                    break;
                case 2:
                    showCampusMap();
                    break;
                case 3:
                    showEmergencyLocations();
                    break;
                case 4:
                    showVisitedLocations();
                    break;
                case 5:
                    System.out.println("\nThank you for using Sathyabama Campus Navigator, " + userName + "!");
                    running = false;
                    break;
                default:
                    // IF/ELSE style validation for out-of-range numbers
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=============================================");
        System.out.println("                  MENU");
        System.out.println("=============================================");
        System.out.println("1. Find a Location");
        System.out.println("2. View Campus Map");
        System.out.println("3. Emergency Locations");
        System.out.println("4. View Visited Locations");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // EXCEPTION HANDLING -> if the user types a non-number (like "hello"),
    // scanner.nextInt() throws InputMismatchException. We catch it so the
    // program does NOT crash, and instead prints a friendly message.
    private int readMenuChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // clear the leftover newline from the buffer
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input. Please enter a number.");
            scanner.nextLine(); // clear the bad input so the loop doesn't get stuck
        }
        return choice;
    }

    // ---------------------------------------------------------------
    // 1) FIND A LOCATION
    // ---------------------------------------------------------------
    private void findLocation() {
        System.out.print("\nEnter your current location: ");
        String start = scanner.nextLine().trim();

        System.out.print("Enter the location you want to go to: ");
        String destination = scanner.nextLine().trim();

        // STRING METHOD -> matchLocationName uses equalsIgnoreCase()
        // so "central library", "CENTRAL LIBRARY" and "Central Library"
        // are all treated as the same location.
        String matchedStart = matchLocationName(start);
        String matchedDestination = matchLocationName(destination);

        // IF/ELSE -> validate that both locations actually exist.
        if (matchedStart == null || matchedDestination == null) {
            System.out.println("\nLocation not found.");
            System.out.println("Please check the location name and try again.");
            return;
        }

        if (matchedStart.equals(matchedDestination)) {
            System.out.println("\nYou are already at " + matchedDestination + "!");
            visitedLocations.add(matchedDestination);
            return;
        }

        // LIST -> route is stored step by step in a List<String>.
        List<String> route = findRoute(matchedStart, matchedDestination);

        if (route == null) {
            // No route found even through one connecting location.
            System.out.println("\nNo direct simplified route found from " + matchedStart
                    + " to " + matchedDestination + ".");
            System.out.println("Locations directly connected to " + matchedStart + ":");
            List<String> nearby = connections.get(matchedStart);
            // LOOP -> print every nearby connected location
            for (String place : nearby) {
                System.out.println("   - " + place);
            }
            return;
        }

        // Display the route nicely.
        System.out.println("\n=============================================");
        System.out.println("                 ROUTE FOUND");
        System.out.println("=============================================");
        System.out.println("From:");
        System.out.println(matchedStart);
        System.out.println("\nTo:");
        System.out.println(matchedDestination);
        System.out.println("\nRoute:\n");

        // LOOP -> print every step of the route with an arrow between steps.
        for (int i = 0; i < route.size(); i++) {
            System.out.println(route.get(i));
            if (i != route.size() - 1) {
                System.out.println("       |");
                System.out.println("       v");
            }
        }

        System.out.println("=============================================");
        System.out.println("\nYou have reached " + matchedDestination + "!");

        // SET -> add the destination to visited locations (duplicates ignored)
        visitedLocations.add(matchedDestination);
    }

    // Checks the locationNames array (ignoring case and extra spaces)
    // and returns the correctly-cased name if found, otherwise null.
    private String matchLocationName(String userInput) {
        // LOOP through the array -> this is the ARRAY being genuinely used.
        for (String name : locationNames) {
            if (name.equalsIgnoreCase(userInput.trim())) {
                return name;
            }
        }
        return null;
    }

    // Builds a simple route using the connections Map.
    // Rule 1: if destination is directly connected to start -> 2-step route.
    // Rule 2: otherwise, try going through ONE connecting location
    //         (start -> middle -> destination).
    // Rule 3: otherwise, no simple route is available.
    // NOTE: This is intentionally simple (no Dijkstra / BFS / A*).
    private List<String> findRoute(String start, String destination) {
        List<String> directNeighbours = connections.get(start);

        // Rule 1: direct connection
        if (directNeighbours.contains(destination)) {
            List<String> route = new ArrayList<>();
            route.add(start);
            route.add(destination);
            return route;
        }

        // Rule 2: one connecting (middle) location
        for (String middle : directNeighbours) {
            List<String> middleNeighbours = connections.get(middle);
            if (middleNeighbours != null && middleNeighbours.contains(destination)) {
                List<String> route = new ArrayList<>();
                route.add(start);
                route.add(middle);
                route.add(destination);
                return route;
            }
        }

        // Rule 3: nothing found within 2 hops
        return null;
    }

    // ---------------------------------------------------------------
    // 2) VIEW CAMPUS MAP  (uses the 2D array with NESTED LOOPS)
    // ---------------------------------------------------------------
    private void showCampusMap() {
        System.out.println("\n=============================================");
        System.out.println("             SATHYABAMA CAMPUS MAP");
        System.out.println("=============================================");
        System.out.println("(Simplified layout for this project - not to scale)\n");

        // NESTED LOOP -> outer loop moves through each row of the 2D array,
        // inner loop moves through each column inside that row.
        for (int row = 0; row < campusMap.length; row++) {
            for (int col = 0; col < campusMap[row].length; col++) {
                String block = campusMap[row][col];
                if (!block.isEmpty()) {
                    // %-32s pads the text so the columns line up neatly
                    System.out.printf("%-32s", "[" + block + "]");
                }
            }
            System.out.println(); // move to the next line after each row
        }
        System.out.println();
    }

    // ---------------------------------------------------------------
    // 3) EMERGENCY LOCATIONS
    // ---------------------------------------------------------------
    private void showEmergencyLocations() {
        System.out.println("\n=============================================");
        System.out.println("             EMERGENCY LOCATIONS");
        System.out.println("=============================================");
        System.out.println("1. Security / Main Gate");
        System.out.println("2. Medical / Hospital Help");
        System.out.println("3. Administrative Help");
        System.out.print("Enter your choice: ");

        int choice = readMenuChoice();

        // SWITCH -> picks the correct emergency location
        switch (choice) {
            case 1:
                System.out.println("\nNearest reference point: Reception Block (Main Gate / Security area).");
                break;
            case 2:
                System.out.println("\nFor medical help, proceed to the Administrative Block reception,");
                System.out.println("which can direct you to the campus health centre.");
                break;
            case 3:
                System.out.println("\nNearest reference point: Administrative Block.");
                break;
            default:
                System.out.println("\nInvalid choice.");
        }
    }

    // ---------------------------------------------------------------
    // 4) VIEW VISITED LOCATIONS  (uses the Set)
    // ---------------------------------------------------------------
    private void showVisitedLocations() {
        System.out.println("\n=============================================");
        System.out.println("           VISITED LOCATIONS");
        System.out.println("=============================================");

        if (visitedLocations.isEmpty()) {
            System.out.println("You have not visited any location yet.");
            return;
        }

        // LOOP through the Set -> order is not guaranteed, but every
        // location appears only ONCE even if visited many times.
        for (String place : visitedLocations) {
            System.out.println("[Visited] " + place);
        }
    }
}
