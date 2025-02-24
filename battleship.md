# Battleship

Battleship is a battlefield, originally pen and paper, where 2 players secretly place 5 ships (of varying length) on their
field - a 10x10 grid.
They then trade firing shots to try and sink the opponents' ships. Once all of a players' ships have sunk they lose.

## Ships

| Ship name  | Length |
|------------|--------|
| Carrier    | 5      |
| Battleship | 4      |
| Destroyer  | 3      |
| Submarine  | 3      |
| PatrolBoat | 2      |

## Flow

Each player places ships simultaneously until they've placed all their ships.  
After that, player 1 fires the first shot.  
Then player 2 fires their first shot.  
Firing and hitting marks that ship as damaged. 💥  
Hitting a ship on all its coordinates marks that ship as sunken. 🏊  
When all of a players' ships have sunk, the battlefield ends immediately and the opponent is declared victorious.

## Higher difficulty flow

When a player fires and hits, they get to fire a second time instead of having the other player fire.



* [x] Render an empty ocean
* [] Placing ships horizontally
* [] Placing ships vertically
* [] Firing and missing
* [] Firing and hitting
* [] Firing and sinking a ship
* [ ] Placing all the ships
* [ ] Alternating moves
* [ ] Sinking all the ships of one player
* [ ] A battlefield can be played at a higher difficulty (hitting allows a second shot)