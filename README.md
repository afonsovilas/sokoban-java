LDTS_T13_G07 - SOKOBAN

GAME DESCRIPTION

This project is a Java-based implementation of the classic puzzle game Sokoban. The player controls a warehouse keeper whose goal is to push boxes to specific storage locations (targets). The game challenges the player's logical thinking and spatial awareness, as boxes can only be pushed (never pulled) and walls restrict movement.

The game features a level system that increases in difficulty, a main menu for navigation, and a collision system to enforce the game rules.

This project was developed by Martim Ferreira (up202403764@up.pt) and Afonso Vilas (up202403577@up.pt) for LDTS 2024-25.

Implemented Features

Main Menu: A navigational entry point that allows the player to start the game or exit the application.

State Management: The game utilizes the State Pattern to switch between the Menu, the actual Gameplay, and level transitions.

Level Loading: Levels are read dynamically from text files (.txt), allowing for easy creation and modification of maps without changing the source code.

Movement & Collision: The hero can move in four directions but is blocked by walls.

Box Interaction: The hero can push boxes if the space behind them is free. The system prevents pushing two boxes at once or pushing boxes into walls.

Win Condition: The level is completed when all boxes are placed on top of target positions. The game automatically detects this state and advances to the next level.

Visual Feedback: Elements have distinct colors, and boxes change color (Green) when placed correctly on a target.

Planned Features

Undo System: Implementation of the Command Pattern to allow players to reverse their last move.

Move Counter: An element to track the number of steps taken, adding a layer of optimization challenge.

Reset Level: Functionality to instantly restart the current level.

High Scores: Saving the best performance (fewest steps) for each level.

Addicional Powers like:
-SuperPower : Allow the player do move 2 Boxes at the same time.
-Portals: Allow the player and boxes to teleport to a diferente location inside the map.

Screenshots
The following screenshots illustrate the general look of our game:

Menu
<p align="center" justify="center"> <img src="docs/images/screenshots/Menu.png"/> </p> <p align="center"> <b><i>Fig 1. Main Menu with options</i></b> </p>

Gameplay
<p align="center" justify="center"> <img src="docs/images/screenshots/gameplay.png"/> </p> <p align="center"> <b><i>Fig 2. Visual feedback when boxes are on targetst</i></b> </p>

Mockups (Current and future Features)
To guide the development of future features, we designed mockups illustrating the User Interface flow and new mechanics.

<p align="center" justify="center">
  <img src="docs/images/mockups.png"/>
</p>
<p align="center">
  <b><i>Fig 3. Sketches showing the Main Menu, Score Board, Level Selection, and new gameplay mechanics (Portals and Powers)</i></b>
</p>

Design Decisions shown in Mockups:
-Step Counter:** To introduce a resource management challenge.
-Reset Button:** Reset the current Level.
-Powers & Portals:** New puzzle elements to increase complexity in later levels.
-Level Selection:** A grid menu to allow players to replay specific levels.
-Score Board:** To save and display the best performance (High Scores).

GAME DOCUMENTATION

Architecture and Design Patterns

1. Architectural Pattern: Model-View-Controller (MVC)

The project is structured according to the Model-View-Controller (MVC) architectural pattern to ensure a clear separation of concerns. This structure is evident in the package organization defined in our UML:

Model (com.sokoban.model): Represents the data and the business logic of the game. It includes classes like Level, Menu, and game elements (Hero, Box, Wall, Target). These classes are unaware of the visual representation.

View (com.sokoban.viewer): Responsible for displaying the data on the screen. Classes like LevelViewer, MenuViewer, and ElementViewer (e.g., HeroViewer, BoxViewer) handle all Lanterna drawing operations.

Controller (com.sokoban.controller & com.sokoban.states): Handles user input and updates the model. HeroController processes movement logic and collisions , while MenuState and GameState manage the application flow and input delegation.

2. Design Patterns
   
-State Pattern

Problem: The game has distinct behaviors depending on the current context (e.g., navigating the Menu uses different logic than controlling the Hero in the Game). Managing these contexts with simple conditional statements in the main loop would lead to unmaintainable code.

Solution: We implemented the State Pattern. The Game class maintains a reference to a current State. We defined an abstract State<T> class with methods step() and draw(). Concrete implementations, MenuState and GameState, define specific behaviors for each context.

Consequences:

( + ) New states (e.g., "GameOverState", "InstructionsState") can be added easily without modifying the Game class.

( + ) Encapsulates state-specific behavior, keeping the main loop clean.

-Factory Pattern (Simple Factory)

Problem: When loading a level from a text file, the application reads characters (e.g., '#', 'X') and needs to instantiate the corresponding objects (Wall, Hero). Placing this instantiation logic directly inside the LevelLoader would violate the Single Responsibility Principle and create high coupling.

Solution: We utilized a simple factory mechanism via the ElementCreation class. The LevelLoader delegates the object creation to this factory, which takes a character and coordinates returns the Element instance.

Consequences:

( + ) Centralizes object creation logic in one place.

( + ) Decouples the file parsing logic from the object instantiation logic.

3. UML Class Diagram

The following class diagram illustrates the project structure, we used the PlantUML plugin to generate it.

<p align="center" justify="center"> <img src="docs/images/uml.png"/> </p> <p align="center"> <b><i>Fig 4. UML Class Diagram showing the MVC structure and patterns.</i></b> </p>

4. Code Smells and Refactoring

Refactoring Performed:

Separation of View and Model: Initially, game elements (like Hero or Wall) contained their own draw() methods. We refactored this by extracting the drawing logic into specific viewer classes (HeroViewer, WallViewer) implementing the ElementViewer interface. This eliminated the dependency on the GUI library (Lanterna) from the Model layer.

Class decomposition: The Level class initially handled loading, logic, and drawing. We extracted LevelLoader for file operations and LevelViewer for rendering, leaving Level responsible only for holding the data.