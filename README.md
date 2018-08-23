# Team J Java Games: Tank Game & Galactic Mail
Made by Jonathan Fox & Johnathan Lee

# Overview
These games were built in Java using the Swing framework. Classes that applied to both games were written abstractly to allow for shared use. This allowed us to reuse elements like object movement, collision detection, vector logic, game clock, screen-drawing logic, controls, and others. 

We built these games as a practice in writing object-oriented code, designing classes in a way that kept them organized, encapsulated, and reusable. However, we also wanted these to be standalone games that had well-implemented mechanics that made them fun to play. We play-tested them and added elements that we felt added to the games' experiences.

Tank Game is a 2-player tank shooter, where two people can play against each other via local multiplayer. Destroy your opponent's tank by damaging it with weapons, and deplete all of your opponent's lives to win the game. Collect weapons and powerups to help you win!

Galactic Mail is a 1-player game. Earn points by launching your rocket and landing on other moons. Your rocket is safe while docked on a moon, but when you launch, you'll have less control of your rocket and you'll have to avoid asteroids. Land on all the moons to continue to the next level. Try to beat the high score!

# How to Run
(Start in team-j-java-games folder)
  
- Tank Game:
  - javac -cp src src/tankGame/TankGameApplication.java
  - java -cp src tankGame.TankGameApplication

- Galactic Mail:
  - javac -cp src src/galacticMail/GalacticMailApplication.java
  - java -cp src galacticMail.GalacticMailApplication

# Controls

- Tank Game:
  - Player 1: WASD to move, R to shoot
  - Player 2: IJKL to move, P to shoot

- Galactic Mail:
  - AD to turn, R to launch

- Both Games:
  - Space to start/resume
  - Esc to pause/exit