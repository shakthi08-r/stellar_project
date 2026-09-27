/*
 * Location.java
 * ---------------------------------------------------
 * This class represents ONE location inside the Sathyabama campus.
 *
 * WHY THIS CLASS EXISTS:
 * Instead of just using plain Strings everywhere, we wrap the
 * details of a location (its name and a short description) inside
 * an object. This is basic Object-Oriented Programming (OOP):
 *   - private fields   -> data hiding (encapsulation)
 *   - constructor      -> creates a Location object with values
 *   - getter methods   -> controlled, read-only access to the data
 *
 * This class is used by CampusNavigator to store details about
 * every real location on campus.
 */
public class Location {

    // private fields -> outside classes cannot change these directly.
    // This is called ENCAPSULATION.
    private String name;
    private String description;

    // Constructor: runs when we create a new Location object
    // Example: new Location("Central Library", "Main library block");
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // A simple method that returns a nicely formatted line of text.
    // This shows that a class can have behaviour (methods), not just data.
    public String getSummary() {
        return name + " - " + description;
    }
}
