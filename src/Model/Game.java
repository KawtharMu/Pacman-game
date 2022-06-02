package Model;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import Controller.Controller;
import Controller.JsonDataManager;
import Utils.CellValue;
import Utils.Constants;
import Utils.Direction;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.stage.Stage;


public class Game {
			
	@FXML private int rowCount;
    @FXML private int columnCount;
    private CellValue[][] grid;
	private static Integer score;
    private int level;
    private int lives;
    private static String playerName;
    public int whichQuestionAte = 0;
    private int countOfBombDots = 0;
	private static boolean gameOver;
    private static boolean youWon;
    private static Direction lastDirection;
    private static Direction currentDirection;
    private Pacman pacman; //added a pacman to the game
    private Ghost [] ghosts = new Ghost[3];
    public Stage gameStage;
    
    private static Game instance;
    
    public static Game getInstance() {
		if(instance == null)
			instance = new Game();
		return instance; 
	}
    
    //Reset the game
    public void restartGame()
    {
    	//add to question Json HERE.
    	instance = new Game();
    	Controller.getInstance().game = instance;
    	
    }
    
   	/**
     * Start a new game upon initializion
     */
    private Game() { // added to game
        this.startNewGame();
    }
    
    public CellValue[][] getGrid() {
	       return grid;
	   }

	public void setGrid(CellValue[][] grid) {
		this.grid = grid;
	}
    
	/** Initialize values of instance variables and initialize level map
     */
    public void startNewGame() {
        gameOver = false;
        youWon = false;
        rowCount = 0;
        columnCount = 0;
        score = 0;
        this.level = 1;
        this.lives = 3;
        this.initializeLevel(Controller.getLevelFile(0));
    }
    
    /**
     *
     * @param fileName txt file containing the board configuration, and it displays them on screen
     */
    public void initializeLevel(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                columnCount++;
            }
            rowCount++;
        }
        columnCount = columnCount/rowCount;
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        grid = new CellValue[rowCount][columnCount];
        int row = 0;
        int pacmanRow = 0;
        int pacmanColumn = 0;
        int ghost1Row = 0;
        int ghost1Column = 0;
        int ghost2Row = 0;
        int ghost2Column = 0;
        int ghost3Row = 0;
        int ghost3Column = 0;
        while(scanner2.hasNextLine()){
            int column = 0;
            String line= scanner2.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()){
                String value = lineScanner.next();
                CellValue thisValue;
                if (value.equals("W")){
                    thisValue = CellValue.WALL;
                }
                else if (value.equals("S")){
                    thisValue = CellValue.SMALLDOT;
                }
                else if (value.equals("B")){
                    thisValue = CellValue.BIGDOT;
                }
                else if (value.equals("X")){ //Easy question
                    thisValue = CellValue.EQ;
                }
                else if (value.equals("Y")){ //Medium question
                    thisValue = CellValue.MQ;
                }
                else if (value.equals("Z")){ //Hard question
                    thisValue = CellValue.HQ;
                }
                else if (value.equals("1")){
                    thisValue = CellValue.GHOST1HOME;
                    ghost1Row = row;
                    ghost1Column = column;
                }
                else if (value.equals("2")){
                    thisValue = CellValue.GHOST2HOME;
                    ghost2Row = row;
                    ghost2Column = column;
                }
                else if (value.equals("P")){
                    thisValue = CellValue.PACMANHOME;
                    pacmanRow = row;
                    pacmanColumn = column;
                }
                else if (value.equals("3")){
                    thisValue = CellValue.GHOST3HOME;
                    ghost3Row = row;
                    ghost3Column = column;
                }
                else //(value.equals("E"))
                {
                    thisValue = CellValue.EMPTY;
                }
                grid[row][column] = thisValue;
                column++;
            }
            row++;
        }
        //Initiate the 1 pacman and the 3 ghosts.
        this.pacman = new Pacman(new Point2D(pacmanRow, pacmanColumn), new Point2D(0,0));
        this.ghosts[0] = new Ghost(new Point2D(ghost1Row,ghost1Column), new Point2D(-1, 0));
        this.ghosts[1] = new Ghost(new Point2D(ghost2Row,ghost2Column), new Point2D(-1, 0));
        this.ghosts[2] = new Ghost(new Point2D(ghost3Row,ghost3Column), new Point2D(0, 1));
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }
    
    
    // change to score per level 
    public void isLevelComplete() { 
        if(score >= 0 && score <= 50 && this.level != 1) // level 1
        {
        	startNextLevel(1);
        }
        if(score > 50 && score <= 100 && this.level != 2) // level 2
        {
        	startNextLevel(2);
        }
        if(score > 100 && score <= 150 && this.level != 3) // level 3
        {
        	startNextLevel(3);
        }
        if(score > 150 && score <= 200 && this.level != 4) // level 4
        {
        	startNextLevel(4);
        }
        if(score >= 200)
        	startNextLevel(5);
    }
    
    /** Initialize the level map for the next level
    *
    */
   public void startNextLevel(int currLevel) { 
       pacman.setpacmanDirection(new Point2D(0,0));
       this.level = currLevel;
       rowCount = 0;
       columnCount = 0;
       this.countOfBombDots = 0;
       youWon = false;
       try {
           this.initializeLevel(Controller.getLevelFile(this.level - 1));
       }
       catch (ArrayIndexOutOfBoundsException e) {
           //if there are no levels left in the level array, the game ends
           youWon = true;
           score = 200;
           level--;
           endGame();
       }
   }
   
   
   /**
    * move the pacman through the tunnels (from left to right)
    */
   public Point2D setGoingOffscreenNewLocation(Point2D objectLocation) {
       //if object goes offscreen on the right
       if (objectLocation.getY() >= columnCount) {
           objectLocation = new Point2D(objectLocation.getX(), 0);
       }
       //if object goes offscreen on the left
       if (objectLocation.getY() < 0) {
           objectLocation = new Point2D(objectLocation.getX(), columnCount - 1);
       }
       return objectLocation;
   }
   
   /**
    * Changes the pacman's direction
    */
   public static Point2D changeDirection(Direction direction){
       if(direction == Direction.LEFT){
           return new Point2D(0,-1);
       }
       else if(direction == Direction.RIGHT){
           return new Point2D(0,1);
       }
       else if(direction == Direction.UP){
           return new Point2D(-1,0);
       }
       else if(direction == Direction.DOWN){
           return new Point2D(1,0);
       }
       else{
           return new Point2D(0,0);
       }
   }
   
   /**
    * Initiate the directions
    */
   public static Direction intToDirection(int x){
	   switch(x)
       {
       	case 0:
       		return Direction.LEFT;
       	case 1:
               return Direction.RIGHT;
       	case 2:
               return Direction.UP;
       	default: 
               return Direction.DOWN;
       }
   }
   
   
   /**
    * Updates the model to reflect the movement of PacMan and the ghosts and the change in state of any objects
    */
   public void step(Direction direction) {
       this.pacmanStep(direction);
       this.ghostStep();
       // If we reach level 3 - we will increase the speed of the Pacman
       if (this.level >= 3)
    	   this.pacmanStep(direction);
       // If we reach level 4 - we will increase the speed of the Ghosts
       if (this.level == 4)
    	   this.ghostStep(); 
       //start a new level if level is complete
       this.isLevelComplete();
   }
   
   public void pacmanStep(Direction direction) {
       this.pacman.movePacman(direction);
	   //if PacMan is on a small dot, delete small dot
	   CellValue pacmanLocationCellValue = grid[(int) pacman.getPacmanLocation().getX()][(int) pacman.getPacmanLocation().getY()];
	   //if the pacman eats a small dot, it gets a 30 second timer and returns after it, and the pacman receives a score
	   if (pacmanLocationCellValue == CellValue.SMALLDOT) {
	       int x = (int) pacman.getPacmanLocation().getX();
		   int y = (int) pacman.getPacmanLocation().getY(); 
		   grid[x][y] = CellValue.EMPTY;
		   int currLvl = this.level;
	       new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() {
	    	            @Override
	    	            public void run() {
	        	        	if(currLvl == level && grid[x][y] == CellValue.EMPTY)
	        	        		grid[x][y] = CellValue.SMALLDOT;
	    	            }
	    	        }, 
	    	        30000 
	    	);
	       score += Constants.PECK_SCORE;
	   }
	   //if PacMan is on a big dot it will disappear and receive a 30 second timer, delete big dot and change game state to bomb pacman mode and initialize the counter
	   if (pacmanLocationCellValue == CellValue.BIGDOT) {
		   int x = (int) pacman.getPacmanLocation().getX();
		   int y = (int) pacman.getPacmanLocation().getY();
		   grid[x][y] = CellValue.EMPTY;
		   int currLvl = this.level;
	       new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() { 
	    	            @Override
	    	            public void run() {
	    	            	if(currLvl == level)
	    	            		grid[x][y] = CellValue.BIGDOT;
	    	            }
	    	        }, 
	    	        30000 
	    	);
	       countOfBombDots++;
	   }
	   //When pacman steps on the easy question, 30 seconds timer
	   if (pacmanLocationCellValue == CellValue.EQ) {
		   whichQuestionAte = 1;
	       int x = (int) pacman.getPacmanLocation().getX();
		   int y = (int) pacman.getPacmanLocation().getY(); 
		   grid[x][y] = CellValue.EMPTY;
		   int currLvl = this.level;
		   new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() { 
	    	            @Override
	    	            public void run() {
	    	            	if(currLvl == level && grid[x][y] == CellValue.EMPTY)
	    	            		grid[x][y] = CellValue.SMALLDOT;
	    	            }
	    	        }, 
	    	        30000
	    	);
		   // create instance of Random class
	       Random rand = new Random();
	       boolean success = true;
	       while(success)
	       {
	    	   int randx = rand.nextInt(14)+2;
	           int randy = rand.nextInt(14)+2;
	           if(grid[randx][randy] == CellValue.EMPTY || grid[randx][randy] == CellValue.SMALLDOT)
	           {
	        	   success = false;
	        	   grid[randx][randy] = CellValue.EQ;
	           }
	       }
           if(!SysData.getInstance().getEasyQuestions().isEmpty()) {
        	   Controller.showEasyQuestion();
           }
	   }
	   //When pacman steps on the medium question, 30 seconds timer
	   if (pacmanLocationCellValue == CellValue.MQ) {
		   whichQuestionAte = 2;
	       int x = (int) pacman.getPacmanLocation().getX();
		   int y = (int) pacman.getPacmanLocation().getY(); 
		   grid[x][y] = CellValue.EMPTY;
		   int currLvl = this.level;
		   new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() { 
	    	            @Override
	    	            public void run() {
	    	            	if(currLvl == level && grid[x][y] == CellValue.EMPTY)
	    	            		grid[x][y] = CellValue.SMALLDOT;
	    	            }
	    	        }, 
	    	        30000 
	    	);
		   // create instance of Random class
	       Random rand = new Random();
	       boolean success = true;
	       while(success)
	       {
	    	   int randx = rand.nextInt(14)+2;
	           int randy = rand.nextInt(14)+2;
	           if(grid[randx][randy] == CellValue.EMPTY || grid[randx][randy] == CellValue.SMALLDOT)
	           {
	        	   success = false;
	        	   grid[randx][randy] = CellValue.MQ;
	           }
		   }
	       //Opens a question pop-up screen
           if(!SysData.getInstance().getMediumQuestions().isEmpty()) {
        	   Controller.showMediumQuestion();
           }     
	   }
	   //When pacman steps on the hard question, 30 seconds timer
	   if (pacmanLocationCellValue == CellValue.HQ) {
		   whichQuestionAte = 3;
	       int x = (int) pacman.getPacmanLocation().getX();
		   int y = (int) pacman.getPacmanLocation().getY(); 
		   grid[x][y] = CellValue.EMPTY;
		   int currLvl = this.level;
		   new java.util.Timer().schedule( 
	    	        new java.util.TimerTask() { 
	    	            @Override
	    	            public void run() {
	    	            	if(currLvl == level && grid[x][y] == CellValue.EMPTY)
	    	            		grid[x][y] = CellValue.SMALLDOT;
	    	            }
	    	        }, 
	    	        30000 
	    	);
		   // create instance of Random class
	       Random rand = new Random();
	       boolean success = true;
	       while(success)
	       {
	    	   int randx = rand.nextInt(14)+2;
	           int randy = rand.nextInt(14)+2;
	           if(grid[randx][randy] == CellValue.EMPTY || grid[randx][randy] == CellValue.SMALLDOT)
	           {
	        	   success = false;
	        	   grid[randx][randy] = CellValue.HQ;
	           }
	       }
	       //Opens a question pop-up screen
           if(!SysData.getInstance().getHardQuestions().isEmpty()) {
        	   Controller.showHardQuestion();
	       }
	   }
   }
   
   public void releaseGame()
   {
	   Robot robot;
		try {
			robot = new Robot();
   		robot.keyPress(KeyEvent.VK_ENTER);
   		robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   }
   
   
   //Ghost step method, it helps us check if the ghost ate the pacman or not
   public void ghostStep() {
	   for(int i = 0; i < ghosts.length; i++)
       {
		   if(this.ghosts[i].getGhostLocation() != null) {
		       //game over if PacMan is eaten by a ghost
		       if (pacman.getPacmanLocation().equals(this.ghosts[i].getGhostLocation())) {
		    	   this.lives--;
		    	   if (this.lives > 0) {
		    		   pacman.setpacmanDirection(new Point2D(0,0));
		    		   startNextLevel(this.level);
		    	   }
		    	   // if the lives is over
		    	   else if(this.gameStage != null){
		    		   endGame();
		    	   }
	           }
		       //move ghosts and checks again if ghosts or PacMan are eaten (repeating these checks helps account for even/odd numbers of squares between ghosts and PacMan)
		       this.ghosts[i].moveAGhost(); 
		       if(pacman.getPacmanLocation().equals(this.ghosts[i].getGhostLocation())) {
		    	   this.lives--;
		    	   
		    	   if (this.lives > 0) {
		               pacman.setpacmanDirection(new Point2D(0,0));
		               startNextLevel(this.level);
		    	   }
		    	   // if the lives is over
		    	   else if(this.gameStage != null){
		    		   endGame();
		    	   }
		       }	       
	       }
       }
   }
   
   
   //Ends the game if the player won or lost the game.
   public void endGame() {
	   try
	   {
		   gameOver = true;
		   SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy  HH:mm");
			   Date date = new Date(System.currentTimeMillis());
		   Player player = new Player(playerName, score, formatter.format(date));
		   if (SysData.getInstance().getHistoryGamesForShow().size() < 10) {
			   SysData.getInstance().getHistoryGamesForShow().add(player);
			   SysData.getInstance().sortArrayList();
			   JsonDataManager.getInstance().writeHistoryIntoJsonFile();
		   }
		   else if (score>SysData.getInstance().getHistoryGamesForShow().get(SysData.getInstance().getHistoryGamesForShow().size()-1).getScore()) {
			   SysData.getInstance().getHistoryGamesForShow().remove(SysData.getInstance().getHistoryGamesForShow().get(SysData.getInstance().getHistoryGamesForShow().size()-1));
			   SysData.getInstance().getHistoryGamesForShow().add(player);
			   SysData.getInstance().sortArrayList();
			   JsonDataManager.getInstance().writeHistoryIntoJsonFile();
		   }
		   Controller.endGame();
	   }
	   catch(Exception e)
	   {
		   
	   }
   }
   
   public double calculateDistanceBetweenPacmanAndGhost(Ghost ghost) 
   {	   	
          double distance = pacman.getPacmanLocation().distance(ghost.getGhostLocation());
          return distance;	
   }
	   	
	     
   //Getter and Setter  	
   public int getCountOfBombDots() 
   {  
     return countOfBombDots;
   }
	   	
	   	
   public void setCountOfBombDots(int countOfBombDots) 
   {
   		this.countOfBombDots = countOfBombDots;
   }

   public static boolean isYouWon() {
       return youWon;
   }
   
   public static boolean isGameOver() {
       return gameOver;
   }

   /**
    * the Cell Value of cell (row, column)
    */
   public CellValue getCellValue(int row, int column) {
       assert row >= 0 && row < this.grid.length && column >= 0 && column < this.grid[0].length;
       return this.grid[row][column];
   }

   public static Direction getCurrentDirection() {
       return currentDirection;
   }

   public void setCurrentDirection(Direction direction) {
       currentDirection = direction;
   }

   public static Direction getLastDirection() {
       return lastDirection;
   }

   public void setLastDirection(Direction direction) {
       lastDirection = direction;
   }

   public int getScore() {
       return score;
   }

   public void setScore(int score) {
	   if(score < 0)
		   this.score = 0;
	   else if(score > 200)
		   this.score = 200;
	   else
		   this.score = score;
   }

   
   // Add new points to the score
   public void addToScore(int points) {
       score += points;
   }

   public int getLevel() {
       return level;
   }

   public void setLevel(int level) {
       this.level = level;
   }

   public int getRowCount() {
       return rowCount;
   }

   public void setRowCount(int rowCount) {
       this.rowCount = rowCount;
   }

   public int getColumnCount() {
       return columnCount;
   }

   public void setColumnCount(int columnCount) {
       this.columnCount = columnCount;
   }
    
   public Pacman getPacman()
   {
	   return this.pacman;
   }

	public Ghost[] getGhosts() {
		return ghosts;
	}
	
	public void setGhosts(Ghost[] ghosts) {
		this.ghosts = ghosts;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
   

}
