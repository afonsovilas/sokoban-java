 ## LDTS_T13_G07 - SOKOBAN

 ### GAME DESCRIPTION

This project is a Java-based implementation of the classic puzzle game Sokoban. The player controls a warehouse keeper whose goal is to push boxes to specific storage locations (targets). The game challenges the player's logical thinking and spatial awareness, as boxes can only be pushed (never pulled) and walls restrict movement.

The game features a level system that increases in difficulty, a main menu for navigation, a high-score tracking system, and special gameplay mechanics like Super Powers and Undo capabilities.

This project was developed by Martim Ferreira (up202403764@up.pt) and Afonso Vilas (up202403577@up.pt) for LDTS 2024-25.

 ### Implemented Features

Main Menu: A navigational entry point that allows the player to start the game or exit the application.

Level Selection: A menu that allows players to replay previously unlocked levels.

State Management: The game utilizes the State Pattern to switch between the Menu, the actual Gameplay, Pausing, Victory screens, and the Scoreboard.

Level Loading: Levels are read dynamically from text files (.txt), allowing for easy creation and modification of maps without changing the source code.

Movement & Collision: The hero can move in four directions but is blocked by walls.

Box Interaction: The hero can push boxes if the space behind them is free. The system prevents pushing two boxes at once or pushing boxes into walls.

Win Condition: The level is completed when all boxes are placed on top of target positions. The game automatically detects this state and advances to the next level.

Step Counter & High Scores: The game tracks the number of steps taken. The best performance (fewest steps) for each level is saved persistently to a file and displayed in the Scoreboard.

Undo System: Players can reverse their last move (pressing 'U'), reverting positions, steps, and power-up states.

Super Power: By collecting a "PowerUp" item, the player gains a temporary ability (handled via Strategy Pattern) to push two boxes at once for a limited time.

Portals: Special elements that teleport the player to a corresponding location on the map.

Visual Feedback: Elements have distinct colors, and boxes change color (Green) when placed correctly on a target.

 ### Screenshots

The following screenshots illustrate the general look of our game:

Menu
<p align="center" justify="center"> <img src="docs/images/screenshots/Menu.png"/> </p> <p align="center"> <b><i>Fig 1. Main Menu with options</i></b> </p>

Gameplay
<p align="center" justify="center"> <img src="docs/images/screenshots/gameplay.png"/> </p> <p align="center"> <b><i>Fig 2. Visual feedback of Level4</i></b> </p>

 ## GAME DOCUMENTATION

 ### Architecture and Design Patterns

 ### 1. Architectural Pattern: Model-View-Controller (MVC)

The project is structured according to the Model-View-Controller (MVC) architectural pattern to ensure a clear separation of concerns. This structure is evident in the package organization defined in our UML:

Model (com.sokoban.model): Represents the data and the business logic of the game. It includes classes like Level, Hero, Box, and Menu. These classes are unaware of the visual representation.

View (com.sokoban.view): Responsible for displaying the data on the screen using the Lanterna library. Classes like LevelViewer and MenuViewer handle all drawing operations.

Controller (com.sokoban.controller & com.sokoban.states): Handles user input and updates the model. HeroController processes movement logic and collisions, while LevelController manages the input delegation within the game loop.
    
 ### 2. Design Patterns
   
 #### Design Patterns

 ##### -State Pattern

Context: The game has distinct behaviors depending on whether the user is in a Menu, Playing, or viewing the Scoreboard.

Implementation: We defined an abstract State<T> class. Concrete implementations (MenuState, GameState, ScoreBoardState, GameWonState) define specific behaviors for step() and draw().

Benefit: Allows the Game class to delegate logic to the current state object, avoiding complex if-else chains in the main loop.

 ##### -Factory Pattern (Simple Factory)

Context: Creating complex menus (Main Menu, Pause Menu, Win Menu) involves initializing many commands and items.

Implementation: The MenuFactory class provides static methods (createMainMenu, createPauseMenu, etc.) to construct these objects.

Benefit: Centralizes creation logic and cleans up the State classes.

 ##### -Command Pattern

Context: Menus need to execute different actions (Start Game, Exit, Open Scoreboard) without the Menu class knowing the details of these actions.

Implementation: We created a Command interface. Concrete commands like LoadLevelCommand and OpenLevelMenuCommand encapsulate these requests.

Benefit: Decouples the UI (Menu) from the business logic, allowing generic menus to contain any type of action.

 ##### -Strategy Pattern

Context: The Hero has different movement capabilities. Normally, he pushes one box, but with a PowerUp, he can push two.

Implementation: We defined a MovementStrategy interface. NormalMovementStrategy implements standard physics, while SuperMovementStrategy implements the powered-up physics. The PowerUpManager swaps these strategies at runtime.

Benefit: Allows dynamic switching of game rules without cluttering the HeroController with conditional logic.

 ##### -Memento Pattern

Context: The "Undo" feature requires restoring the game to a previous state.

Implementation: The Level class creates LevelMemento objects containing the state of the Hero, Boxes, and PowerUps. These mementos are stored in a Stack.

Implementation Detail: When 'U' is pressed, the state is popped from the stack and restored.

Benefit: Encapsulates the state snapshot logic, preserving encapsulation while allowing full restoration.

 ### 3. UML Class Diagram

The following class diagram illustrates the project structure, we used the PlantUML plugin to generate it.

<p align="center" justify="center"> <img src="docs/images/uml.png"/> </p> <p align="center"> <b><i>Fig 4. UML Class Diagram showing the MVC structure and patterns.</i></b> </p>

 ### 4. Code Smells and Refactoring

During development, we performed several refactoring steps to improve code quality and testability:

Extract Controller: We moved input handling logic from the Level class to LevelController to strictly adhere to the Single Responsibility Principle. Level is now purely a data container, while LevelController handles the logic.

Dependency Injection for Testing: We modified ScoreManager and LevelLoader to allow dependency injection of file paths or mocks. This enabled safe unit testing without overwriting real high-score files or requiring real level files.

Viewer Separation: We extracted drawing logic from Model classes into dedicated Viewers early in the project to enforce MVC boundaries.

 ### 5. Testing

We utilized JUnit 5 for unit testing, Mockito for isolating dependencies (especially Lanterna), and jqwik for Property-Based Testing (PBT).

Unit Testing: We ensured that core logic (Hero movement, Collisions, Score management) functions correctly.

Mocking: We mocked the Screen and TextGraphics to test the View and State logic without needing a real terminal window.

Property-Based Testing: We used PBT in GameStateTest and MenuStateTest to ensure the game is robust against random key inputs, guaranteeing that invalid keys are ignored and do not crash the application.

Mutation Testing: We used Pitest to assess the quality of our tests.

Coverage Summary:

Line Coverage: 88%

Mutation Coverage: 76%

<p align="center" justify="center"> <img src="docs/images/screenshots/coverage_report.png"/> </p> <p align="center"> <b><i>Fig 4. Pitest Coverage Report.</i></b> </p>


### Gameplay/WinLevel Demo
![Gameplay](docs/gifs/winLevel.gif)

### Undo Feature
![Undo System](docs/gifs/undo.gif)