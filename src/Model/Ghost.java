package Model;

import java.util.Random;

import Utils.CellValue;
import Utils.Direction;
import javafx.geometry.Point2D;

public class Ghost {
	
	 private Point2D ghostLocation;
	 private Point2D ghostDirection;
	 
	 
	    public Ghost(Point2D ghostLocation, Point2D ghostDirection)
	    {
	    	this.ghostLocation = ghostLocation;
	    	this.ghostDirection = ghostDirection;
	    }
	    
	    /**
	     * Move a ghost to follow PacMan if he is in the same row or column, otherwise move randomly when it hits a wall.
	     * location the current location of the specified ghost
	     * an array of Point2Ds containing a new velocity and location for the ghost
	     */
		public Point2D[] moveAGhost(){
	        Random generator = new Random();
	        //if the ghost is in the same row or column as PacMan and not in ghostEatingMode,
	        // go in his direction until you get to a wall, then go a different direction
	        //otherwise, go in a random direction, and if you hit a wall go in a different random direction
	        CellValue [][] grid = Game.getInstance().getGrid();
	        
	        
            //check if ghost is in PacMan's column and move towards him
            if (this.ghostLocation.getY() == Game.getInstance().getPacman().getPacmanLocation().getY()) {
                if (this.ghostLocation.getX() > Game.getInstance().getPacman().getPacmanLocation().getX()) {
                	this.ghostDirection = Game.changeDirection(Direction.UP);
                } else {
                	this.ghostDirection = Game.changeDirection(Direction.DOWN);
                }
                Point2D potentialLocation = this.ghostLocation.add(this.ghostDirection);
                //if the ghost would go offscreen, wrap around
                potentialLocation = Game.getInstance().setGoingOffscreenNewLocation(potentialLocation);
                //generate new random directions until ghost can move without hitting a wall
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = Game.intToDirection(randomNum);
                    this.ghostDirection = Game.changeDirection(direction);
                    potentialLocation = this.ghostLocation.add(this.ghostDirection);
                }
                this.ghostLocation = potentialLocation;
            }
            //check if ghost is in PacMan's row and move towards him
            else if (this.ghostLocation.getX() == Game.getInstance().getPacman().getPacmanLocation().getX()) {
                if (this.ghostLocation.getY() > Game.getInstance().getPacman().getPacmanLocation().getY()) {
                	this.ghostDirection = Game.changeDirection(Direction.LEFT);
                } else {
                	this.ghostDirection = Game.changeDirection(Direction.RIGHT);
                }
                Point2D potentialLocation = this.ghostLocation.add(this.ghostDirection);
                potentialLocation = Game.getInstance().setGoingOffscreenNewLocation(potentialLocation);
                while (grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL) {
                    int randomNum = generator.nextInt(4);
                    Direction direction = Game.getInstance().intToDirection(randomNum); // TODO: intTO in game class
                    this.ghostDirection = Game.changeDirection(direction);
                    potentialLocation = this.ghostLocation.add(this.ghostDirection);
                }
                this.ghostLocation = potentialLocation;
            }
            //move in a consistent random direction until it hits a wall, then choose a new random direction
            else{
                Point2D potentialLocation = this.ghostLocation.add(this.ghostDirection);
                potentialLocation = Game.getInstance().setGoingOffscreenNewLocation(potentialLocation);
                while(grid[(int) potentialLocation.getX()][(int) potentialLocation.getY()] == CellValue.WALL){
                    int randomNum = generator.nextInt( 4);
                    Direction direction = Game.getInstance().intToDirection(randomNum);
                    this.ghostDirection = Game.changeDirection(direction);
                    potentialLocation = this.ghostLocation.add(this.ghostDirection);
                }
                this.ghostLocation = potentialLocation;
            }
	        Point2D[] data = {this.ghostDirection, this.ghostLocation};
	        return data;
	    }
	    
	    /**
	     * Resets a ghost's location to its home state
	     */
	    public void sendGhostHome() {
	        CellValue [][] grid = Game.getInstance().getGrid();
	        for (int row = 0; row < Game.getInstance().getRowCount(); row++) {
	            for (int column = 0; column < Game.getInstance().getColumnCount(); column++) {
	                if (grid[row][column] == CellValue.GHOST1HOME) {
	                    ghostLocation = new Point2D(row, column);
	                }
	            }
	        }
	        ghostDirection = new Point2D(-1, 0);
	    }
	    
	    public void sendGhostHomeAfter5sec() {
            ghostLocation = null;
 		   new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() { 
	    	            @Override
	    	            public void run() {
	    	    	        CellValue [][] grid = Game.getInstance().getGrid();
	    	    	        for (int row = 0; row < Game.getInstance().getRowCount(); row++) {
	    	    	            for (int column = 0; column < Game.getInstance().getColumnCount(); column++) {
	    	    	                if (grid[row][column] == CellValue.GHOST1HOME) {
	    	    	                    ghostLocation = new Point2D(row, column);
	    	    	                }
	    	    	            }
	    	    	        }
	    	            }
	    	        }, 
	    	        5000
	    	);
	        CellValue [][] grid = Game.getInstance().getGrid();

	        ghostDirection = new Point2D(-1, 0);
	    }
	    
	    public Point2D getGhostLocation() {
	        return ghostLocation;
	    }

	    public void setGhostLocation(Point2D ghost1Location) {
	        this.ghostLocation = ghost1Location;
	    }
	    
	    public Point2D getghostDirection() {
	        return ghostDirection;
	    }

	    public void setghostDirection(Point2D ghost1Velocity) {
	        this.ghostDirection = ghost1Velocity;
	    }
}
