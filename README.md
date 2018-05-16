# Term Project Documentation
Jonathan Fox & Johnathan Lee

Github Repository Link: https://github.com/sfsu-csc-413-spring-2018/term-project-team-j.git

# Overview
Introduction
- We were tasked with building two games as a team in Java using GUI elements. This was to give us experience using the object-oriented programming model that we learned in class, meaning our program was organized around manipulating objects. By using abstract, object-oriented design, we would be able to reuse elements from our first game in our second game to save time and effort. Other goals of the project were to give us practice working as a team and working with Swift GUI elements.

Technical Work
- Because we would be building two games, the most efficient way to build them was to first design a system that could be used for both games. We first designed a class structure that laid out basic game elements that would be used for both games. These basic elements had to be tested first in a basic test environment to make sure they were working. Once we had a basic structure down, we built the specific elements of Tank Game, and then Galactic Mail. For the games, it was a process of adding one element at a time on our own individual branches, testing that they worked individually, and then merging them and fixing errors that would come from that.

Development Environment
- IDE: IntelliJ IDEA 2017.3.4 x64
- JDK: 9.0.4

Scope of Work
- Build a system to run games
  - Build an abstract GameObject, GameWorld, GamePanel, GameApplication
  - Build a Clock, KeyInputHandler, SpriteHandler, SoundHandler
  - Create Trigger, Vector2, Timer, Camera, MultiSprite, and AnimatedSprite classes

- Build Tank Game
  - Build TankGameWorld, TitleWorld
  - Build Tank, Projectile, Wall, DestructibleWall, Weapon, Pickup, TankSpawner, PickupSpawner
  - Build CollisionHandler
  - Implement reading a map from a file and creating a GameWorld from it

- Build Galactic Mail
  - Build abstract SpaceObject
  - Build Rocket, Asteroid, Moon, Planet, RocketSpawner
  - Build GalacticMailLevel, TitleWorld
  - Implement an algorithm for increased difficulty on higher levels
  - Implement reading scores from and writing scores to a file for an in-game Scoreboard

# Compilation Instructions
(Start in term-project-team-j)

- For Tank Game:
  - javac -cp src src/tankGame/TankGameApplication.java
  - java -cp src tankGame.TankGameApplication

- For Galactic Mail:
  - javac -cp src src/galacticMail/GalacticMailApplication.java
  - java -cp src galacticMail.GalacticMailApplication

# Assumptions
- General game specifications were given, but any implementation not mentioned was up to us to decide.
- Everything else was clearly answered in class.

# Implementation Discussion
General Classes

- The implementation of our games was based around reusing as many elements as possible, in order to save us time and make our work more efficient. At the same time, we wanted to make sure that individual classes had their own clear-cut responsibilities to avoid sharing responsibilities, keeping our implementation simple and easy to work with.
- Both games, at the most basic level, consist of GameObjects interacting with each other. The GameObjects are kept track of in a GameWorld, which keeps track of the current state of the game, and also renders an image of the current state of the game to the GamePanel display. The games are run through their respective GameApplications.
- To create behavior in our games over time, we implemented a Clock that updates ClockListeners every frame. For example, changing the position of a moving projectile or updating the GamePanel’s display. ClockListener is an interface with an update() function.
- We implemented a Timer to keep track of certain timed behaviour, where we wanted something to occur after a set number of frames. For example, having a delay between tank shots or having a delay after dying before spawning. Each class creates an instance of Timer for their own use.
- The KeyInputHandler receives keyboard input from the user(s), and is read by other classes to determine user input. 
- Vector2 is a data type we created to achieve functionality that we couldn’t find in another Java data type. It basically acts as a Point but uses doubles for its x and y position instead of ints, and it has other functionality as well. We frequently had to perform specific functions, like adding and subtracting vectors, finding magnitude of vectors, or creating vectors with a given magnitude and rotation, so we added these functions to Vector2, which increased our coding efficiency.
- GameObjects frequently needed to detect whether they were overlapping each other or not, so we implemented a Trigger that would handle that functionality. Triggers represent an area around the gameObject’s center, either in a square or circle shape with adjustable size, which can be compared to other Triggers to see if they overlap.
- CollisionHandler is a class that is only used by TankGame, but we wrote it abstractly so that it could be extended to other games if needed. GameObjects that are moving can check their movement vector and Trigger with CollisionHandler, and the CollisionHandler will return an adjusted movement vector that avoids intersections with Collidables.
- Singletons: We implemented GamePanel, GameWorld, KeyInputHandler, SpriteHandler, SoundHandler, and CollisionHandler as Singletons, meaning there is only one instance of each class at a time, and the reference to it can be accessed statically. These are all classes that should only have one instance at a time, which other classes can access. While this is usually thought of as poor code design, in our implementation it was the best way to achieve the functionality we wanted. 

Tank Game Classes
- For Tank Game, we wanted to implement player-controlled Tanks that could turn, move, and shoot projectiles. Turning and moving are determined by inputs read by the Tank’s TankKeyInput. Moving is checked with the CollisionHandler to avoid intersecting with Collidables. The Tank also checks the GameWorld for pickups that it may be intersecting with and applies them.
- Shooting projectiles is handled by the Tank’s currently equipped weapon, which determines which projectile to shoot and keeps a shoot delay Timer which is used to determine whether or not a shot can be fired. 	
- Projectiles move in a certain direction while checking for collisions. If an enemy Damageable or Collidable is found, it deals damage and dies.
- The Collidable interface has a method to determine the movement that must be performed to move another Trigger out of intersection with the Collidable.
- The Damageable interface has a method to take a given amount of damage.
- Wall is simply a Collidable. DestructibleWall is a Collidable and a Damageable. 
  - The GameWorld reads a map file and parses it, placing walls in the correct positions.
- Player Camera was implemented to handle the specific function of displaying the area of the GameWorld around its assigned Tank to the GamePanel.
- Pickups weren’t required, but we added them for fun. When the Tank intersects a Pickup’s trigger, the Pickup applies an effect to the Tank. For example, equipping a weapon or adding health.

Galactic Mail Classes
- SpaceObjects inherits all the behavior of a GameObject with the exception of its circular trigger/hit box, movement and ability to wrap around the map.
- Asteroids, Moons, and Explosions all have similar attributes but Moons are not animated
- Rockets use RocketKeyInput which is similar to Tank’s key input.
- Rockets check for overlapping triggers.
  - If the trigger is a Moon’s:
    - Rocket takes a reference of the current Moon it is docked upon.
- This allows points to be added(through PointsHandler) when docked or deducted over time while docked.
  - If it is an Asteroid’s
    - Rocket is destroyed and the player loses a life.
- RocketSpawner keeps track of lives as well as respawning Rockets when a life is lost.
- PointsHandler keeps track of points and has methods that add and subtract points
- Scoreboard keeps an arraylist of points and is updated when a score is beaten. The arraylist is written to a text file when the application is closed and read from when initialized.

# Results and Conclusions
This project put what we learned about object oriented programming to use. This was evident when coding Galactic Mail and our abstracted classes and interfaces were integratable with relative ease. Being the first team based project of the semester, we faced many challenges regarding division of work and general usage of Github, which tested our ability to communicate and plan our code and commits accordingly. Now that we have some foundation in working with object oriented design, we can add it to our repertoire and use it to tackle future assignments or projects of similar nature, as well as the experience we gained in collaborative coding. 
