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

## 12. Viva Questions and Answers

1. **Why did you choose this project?**
   Because navigating a large campus is a real problem for new
   students and visitors, and it let me use many core Java concepts
   in one small application.

2. **What problem does the project solve?**
   It helps a user find a simple route from their current location to
   a destination on campus, using real Sathyabama block names.

3. **Why did you use Java?**
   Java is the language I am most comfortable with, and it has
   built-in collection classes (List, Set, Map) that fit this project
   well without needing external libraries.

4. **Where did you use switch?**
   In the main menu (`start()`) and in the emergency locations menu
   (`showEmergencyLocations()`), both based on the user's numeric
   choice.

5. **Where did you use if/else?**
   To check if a location exists, if start and destination are the
   same, if a route was found, and to validate menu choices.

6. **Where did you use loops?**
   A while loop keeps the menu running; for loops search the location
   array, print route steps, and print visited locations.

7. **Where did you use nested loops?**
   In `showCampusMap()`, to print every row and column of the 2D
   `campusMap` array.

8. **Why did you use a 2D array?**
   Because the campus map naturally has rows and columns, so a 2D
   array is the simplest way to represent and print that layout.

9. **Why did you use List?**
   To store the ordered steps of a route, and to store each
   location's neighbours in the connections Map - both need to keep
   items in a meaningful order.

10. **Why did you use Set?**
    To store visited locations without duplicates - a Set
    automatically ignores repeated entries.

11. **Why did you use Map?**
    One Map connects a location name to its full `Location` object
    (details lookup). The other Map connects a location name to the
    list of locations it is directly linked to (used for
    route-finding).

12. **What is a class?**
    A blueprint that groups related data and behaviour together. In
    this project, `Location` and `CampusNavigator` are classes.

13. **What is an object?**
    A real instance created from a class. For example,
    `new Location("Central Library", "...")` creates one Location
    object.

14. **Where is encapsulation used?**
    In `Location.java` - its fields (`name`, `description`) are
    private, and can only be read through public getter methods.

15. **Why did you use exception handling?**
    So that if the user types a non-numeric value at a menu prompt,
    the program shows a friendly message and continues instead of
    crashing.

16. **What happens if the user enters an invalid choice?**
    If it is a number outside the menu range, the switch's default
    case prints "Invalid choice." If it is not a number at all, the
    try-catch block catches the exception and asks again.

17. **How does route finding work?**
    The program first checks if the destination is directly connected
    to the start location using the connections Map. If not, it
    checks if any one neighbour of the start location connects to the
    destination. If neither works, it shows the start location's
    direct connections instead.

18. **How does the program know whether a location exists?**
    It compares the typed-in text (trimmed and case-ignored) against
    the `locationNames` array using `equalsIgnoreCase()`.

19. **How are visited locations stored?**
    In a `Set<String>` called `visitedLocations`, so each location is
    stored only once even if the user visits it multiple times.

20. **How can this project be improved in the future?**
    By adding more locations from the official map, showing walking
    distances or estimated time, or eventually adding a simple GUI or
    map-based visualization.

## 13. Presentation Script

**One-sentence explanation:**
"Sathyabama Campus Navigator is a simple Java console program that
helps students and visitors find routes between real blocks on our
campus."

**30-second explanation:**
"My project is called Sathyabama Campus Navigator. It's a Java
console application that solves a simple but real problem - new
students and visitors often don't know how to get from one block to
another on our campus. The program stores real Sathyabama locations
like the Reception Block, Central Library, and CSE Block, along with
a simplified network of how they connect. The user types their
current location and where they want to go, and the program shows a
step-by-step route."

**1-minute explanation:**
"My project is Sathyabama Campus Navigator, a console-based Java
application. The idea came from a real problem - our campus has many
blocks, and new students or visitors often struggle to find their way
around. In the program, the user enters their name, sees a simplified
text map of the campus, and then can search for a route between two
real Sathyabama locations, like from the Reception Block to the
Central Library. Behind the scenes, I store the locations in an
array, their connections in a Map, and build the route using a
List. I also added a visited-locations feature using a Set, so
repeated visits don't get counted twice, and an emergency locations
menu for quick reference points. I used a switch statement for the
menus, loops and nested loops to display the map, and try-catch to
make sure the program never crashes on bad input."

**3-minute explanation:**
"My project is Sathyabama Campus Navigator, a simple Java console
application built for a college academic project. The real-life
problem is that Sathyabama has a large campus with many blocks -
administrative buildings, department blocks, the library, auditorium,
and more - and new students, first-years, and visitors often don't
know their way around. Instead of building a full GPS-based app,
which would need APIs and databases, I kept this project intentionally
simple so I can explain every part of it in a viva.

When the program starts, it asks for the user's name and welcomes
them, then shows a simplified text-based campus map built from a 2D
array and printed using nested loops. The main menu is handled with a
switch statement, offering options to find a location, view the map
again, check emergency locations, or view visited locations.

For the core feature - finding a location - I store 14 real
Sathyabama locations from the official campus map in a String array,
which is used to validate whatever the user types, using
equalsIgnoreCase and trim so small typing differences don't matter. I
store how these locations connect to each other in a
Map<String, List<String>>, and when the user asks for a route, the
program checks this Map for a direct connection, or one connecting
location if there isn't a direct one, and builds the route step by
step in a List. I intentionally avoided complex algorithms like
Dijkstra or BFS so the logic stays something I can walk through line
by line.

I also added a Set to store visited locations, since a Set
automatically prevents duplicates, and a small emergency-locations
menu for quick reference points like Security or Medical help. Every
menu input is read inside a try-catch block, so if someone types
letters instead of a number, the program shows a friendly message
instead of crashing.

Overall, this project let me apply almost everything I've learned in
core Java - switch statements, if/else, loops, nested loops, Strings,
arrays, OOP with a Location class, exception handling, and the List,
Set and Map collections - inside one small, understandable,
real-world-inspired application."
