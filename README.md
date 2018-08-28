# Team J Java Games: Tank Game & Galactic Mail
Made by Jonathan Fox & Johnathan Lee

## Demos
[Tank Game Gameplay Video](https://youtu.be/C16IQBU7it4)

[Galactic Mail Gameplay Video](https://youtu.be/uIgtczAg4bg)

## Overview
Tank Game and Galactic Mail in Java using the Swing framework.

We built these games as practice in writing object-oriented code, designing classes in a way that kept them organized, encapsulated, and reusable. This allowed us to code efficiently and reuse elements like object movement, collision detection, vector logic, game clock, screen-drawing logic, controls, and others.

Additionally, we wanted these to be standalone games that would be fun to play, so we took care to give them clear visuals, responsive controls, and interesting game play.

Tank Game is a 2-player tank shooter, where two people can play against each other via local multiplayer. Destroy your opponent's tank by damaging it with weapons, and deplete all of your opponent's lives to win the game. Collect weapons and powerups to help you win!

Galactic Mail is a 1-player game. Earn points by launching your rocket and landing on other moons. Your rocket is safe while docked on a moon, but when you launch, you'll have less control of your rocket and you'll have to avoid asteroids. Land on all the moons to continue to the next level. Try to beat the high score!

## How to Run
(Start in team-j-java-games folder)
  
- Tank Game:
  - javac -cp src src/tankGame/TankGameApplication.java
  - java -cp src tankGame.TankGameApplication

- Galactic Mail:
  - javac -cp src src/galacticMail/GalacticMailApplication.java
  - java -cp src galacticMail.GalacticMailApplication

## Controls

- Tank Game:
  - Player 1: WASD to move, R to shoot
  - Player 2: IJKL to move, P to shoot

- Galactic Mail:
  - AD to turn, R to launch

- Both Games:
  - Space to start/resume
  - Esc to pause/exit