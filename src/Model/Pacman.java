package Model;

import Utils.CellValue;
import Utils.Direction;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Pacman{
	
	
	private Point2D pacmanLocation;
    private Point2D pacmanDirection;
 
    public Pacman(Point2D pacmanLocation, Point2D pacmanDirection)
    {
    	this.pacmanLocation = pacmanLocation;
    	this.pacmanDirection = pacmanDirection;    	
    }
    
    
  
    /**
     * Move PacMan based on the direction indicated by the user (based on keyboard input from the Controller)
     * direction the most recently inputed direction for PacMan to move in
     */
    public void movePacman(Direction direction) {
        Point2D potentialpacmanDirection = Game.changeDirection(direction); // change velocity in game CLass
        Point2D potentialPacmanLocation = pacmanLocation.add(potentialpacmanDirection);
        //if PacMan goes offscreen, wrap around
        potentialPacmanLocation = Game.getInstance().setGoingOffscreenNewLocation(potentialPacmanLocation); // in game class
        //determine whether PacMan should change direction or continue in its most recent direction
        //if most recent direction input is the same as previous direction input, check for walls
        CellValue [][] grid = Game.getInstance().getGrid();//added a new variable.
        if (direction.equals(Game.getLastDirection())) { //in game class
            //if moving in the same direction would result in hitting a wall, stop moving
        	
            if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL){
                pacmanDirection = Game.changeDirection(Direction.NONE);
                Game.getInstance().setLastDirection(Direction.NONE);
            }
            else {
                pacmanDirection = potentialpacmanDirection;
                pacmanLocation = potentialPacmanLocation;
            }
        }
        //if most recent direction input is not the same as previous input, check for walls and corners before going in a new direction
        else {
            //if PacMan would hit a wall with the new direction input, check to make sure he would not hit a different wall if continuing in his previous direction
            if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL){
                potentialpacmanDirection = Game.changeDirection(Game.getInstance().getLastDirection());
                potentialPacmanLocation = pacmanLocation.add(potentialpacmanDirection);
                //if changing direction would hit another wall, stop moving
                if (grid[(int) potentialPacmanLocation.getX()][(int) potentialPacmanLocation.getY()] == CellValue.WALL){
                    pacmanDirection = Game.changeDirection(Direction.NONE);
                    Game.getInstance().setLastDirection(Direction.NONE);
                }
                else {
                    pacmanDirection = Game.changeDirection(Game.getInstance().getLastDirection());
                    pacmanLocation = pacmanLocation.add(pacmanDirection);
                }
            }
            //otherwise, change direction and keep moving
            else {
                pacmanDirection = potentialpacmanDirection;
                pacmanLocation = potentialPacmanLocation;
                Game.getInstance().setLastDirection(direction); // game 
            }
        }
    }
    
    public boolean changeColor()
    {
    	return true;
    }
    
    
    //Getters and Setters
    public Point2D getPacmanLocation() {
        return pacmanLocation;
    }

    public void setPacmanLocation(Point2D pacmanLocation) {
        this.pacmanLocation = pacmanLocation;
    }
    
    public Point2D getpacmanDirection() {
        return pacmanDirection;
    }

    public void setpacmanDirection(Point2D direction) {
        this.pacmanDirection = direction;
    }
    
}