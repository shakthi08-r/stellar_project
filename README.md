# Sathyabama Campus Navigator

## 1. Introduction

Sathyabama Campus Navigator is a simple console-based Java application
that helps a student, new joinee, or visitor find their way between
important locations on the Sathyabama Institute of Science and
Technology campus. It asks the user's name, shows a simplified text
map of the campus, and lets the user search for a location and see a
step-by-step route to reach it.

## 2. Problem Statement

Sathyabama is a large campus with many blocks - administrative
buildings, department blocks, the library, the auditorium, and more.
New students, first-year students, and visitors often do not know
where a particular block is located or how to reach it from where
they currently are. There is a need for a simple, quick way to look
up a location and get basic directions, without relying on a phone
app, GPS, or internet connection.

## 3. Proposed Solution

This Java console program stores a set of real Sathyabama campus
locations (taken from the official campus map) along with a
simplified network of how these locations connect to each other. When
a user enters their current location and a destination, the program
checks whether both locations exist, then shows a simple route built
from the stored connections. The program also lets the user view the
overall campus layout, look up emergency reference points, and see a
list of the locations they have visited during the session.

## 4. Objectives

- Help users quickly find how to reach a campus location.
- Provide a simplified visual (text-based) layout of the campus.
- Demonstrate core Java programming concepts in a small, real project.
- Handle invalid user input gracefully without crashing.
- Keep a record of locations visited during a session.
- Provide quick access to basic emergency reference points.

## 5. Features

- Asks for and welcomes the user by name.
- Displays a simplified campus map (2D array + nested loops).
- Location search that ignores case and extra spaces.
- Step-by-step route display between two locations.
- Emergency locations menu (Security, Medical, Administrative help).
- Visited locations tracker (duplicate-free, using a Set).
- Invalid menu input is caught and does not crash the program.

## 6. Technologies Used

- Java (JDK 8 or above)
- Console / Command Line (no GUI, no database, no external libraries)

## 7. Project Structure

```
SathyabamaCampusNavigator/
    src/
        Main.java
        Location.java
        CampusNavigator.java
    README.md
```

- **Main.java** - The entry point. Greets the user, takes their name,
  creates a `CampusNavigator` object, and starts the program.
- **Location.java** - A simple OOP class representing one campus
  location, with a name and a short description.
- **CampusNavigator.java** - Contains the location data, the
  simplified connection network, the menu loop, and all the
  navigation/search/emergency/visited-locations logic.

## 8. Java Concepts Used

### Switch
Used in `CampusNavigator.start()` to decide what to do based on the
user's main-menu choice (1 to 5), and again in
`showEmergencyLocations()` to pick which emergency location to show.
A switch is used here because the menu has a small, fixed set of
numbered options - it is cleaner and easier to read than a long chain
of if/else statements.

### If/Else
Used to check whether a typed-in location actually exists
(`matchLocationName` returning null), whether the start and
destination are the same place, whether a route was found, and to
validate emergency/menu choices. This is the project's main way of
handling "is this input valid or not" decisions.

### Loops
A `while` loop keeps the main menu running until the user chooses
Exit. `for` loops are used to search through the locations array, to
print each step of a route, and to print the visited locations. Loops
let the program repeat the same action (checking, printing) without
writing repeated code.

### Nested Loops
Used in `showCampusMap()` to print the 2D `campusMap` array: the outer
loop walks through each row, and the inner loop walks through each
column inside that row. This is needed because a 2D array has two
dimensions, so two loops are required to visit every cell.

### Strings
User name, location names, and all menu/route messages are Strings.
`trim()` is used to remove extra spaces the user might accidentally
type, and `equalsIgnoreCase()` is used so "central library",
"Central Library" and "CENTRAL LIBRARY" are all recognised as the
same valid location.

### Arrays
A 1D `String[] locationNames` array stores every valid location name
and is used to check if a typed-in name exists. A 2D
`String[][] campusMap` array stores the simplified campus layout used
only for display in `showCampusMap()`.

### OOP
`Location.java` is a class with private fields (`name`,
`description`), a constructor, and getter methods - this is
encapsulation. `CampusNavigator` creates real `Location` objects
(stored in `locationDetails`) and uses them to describe each block.
This shows a class that is actually built and used, not just declared.

### Exception Handling
`readMenuChoice()` wraps `scanner.nextInt()` in a try-catch block. If
the user types something that is not a number (like "hello"), Java
throws an `InputMismatchException`; the catch block prints
"Invalid input. Please enter a number." and clears the bad input, so
the program keeps running instead of crashing.

### List
`List<String>` is used inside `findRoute()` to build the route
step-by-step (start -> optional middle stop -> destination), and the
`connections` Map stores each location's neighbours as a
`List<String>`. Lists are used here because the number of steps in a
route (or neighbours of a location) can vary, and order matters when
printing the route.

### Set
`Set<String> visitedLocations` stores every location the user has
successfully navigated to. A Set is used specifically because it
automatically ignores duplicates - if the user visits "Central
Library" three times in one session, it still appears only once in
the Visited Locations list.

### Map
Two Maps are used. `Map<String, Location>` (`locationDetails`) looks
up the full `Location` object (name + description) for a given
location name. `Map<String, List<String>>` (`connections`) stores,
for every location, the list of locations it is directly connected to
- this Map is the actual data used by `findRoute()` to build a route.

## 9. Algorithm

1. Start program.
2. Ask user name.
3. Display welcome message.
4. Display campus map.
5. Display menu.
6. User selects Find Location.
7. Ask starting location.
8. Ask destination.
9. Validate locations against the stored array.
10. Find route using the stored connections Map (direct link, or one
    connecting location).
11. Display route.
12. Add destination to visited Set.
13. Return to menu.
14. Continue until Exit is chosen.

## 10. Flowchart

```
START
  |
Enter Name
  |
Welcome User
  |
Display Campus Map
  |
Display Menu
  |
Choose Option
  |
Find Location? --No--> (View Map / Emergency / Visited / Exit)
  |Yes
Enter Start + Destination
  |
Validate Location
  |
Find Route
  |
Display Route
  |
Save Visited Location
  |
Back to Menu
  |
Exit? --No--> back to Display Menu
  |Yes
END
```

## 11. Important Note on Campus Data

The 14 locations used in this project (Reception Block, Administrative
Block, Controller of Examination, Central Library, Auditorium Block,
Class Room Block 01, CSE Block, ECE Block, IT Block, EEE Block,
Mechanical Block, International Research Centre, Research Park, and
Indian Bank) are real blocks that appear on the official Sathyabama
campus map. The **connections between them** (which block leads to
which) are a simplified network created only for this academic
project, so that the route-finding logic stays easy to explain in a
viva. They are not claimed to be the official pedestrian routes of the
campus.


real-world-inspired application."
