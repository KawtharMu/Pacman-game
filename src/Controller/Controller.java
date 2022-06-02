/**
* @author All Members
*/
package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Model.Game;
import Model.Question;
import Model.SysData;
import View.PacManView;
import Utils.Constants;
import Utils.Direction;

public class Controller implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private Label gameOverLabel;
    @FXML private PacManView pacManView;
    private static Controller instance;
    private SysData sysData;
    public Game game;
    private static final String[] levelFiles = {"src/levels/level1.txt", "src/levels/level2.txt", "src/levels/level3.txt", "src/levels/level4.txt"};

    public static Timer timer;
    public static boolean isTimerOn = false;
    private static boolean paused;
    

    public Controller() {
        paused = false;
    }
    public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}

    /**
     * Initialize and update the model and view from the first txt file and starts the timer.
     */
    public void initialize() {
        this.game = Game.getInstance();
        this.update(Direction.NONE);
        this.startTimer();
        isTimerOn = true;
    }

    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                       update(Game.getCurrentDirection());
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Steps the game, updates the view, updates score and level, displays Game Over/You Won, and instructions of how to play
     */
    private void update(Direction direction) {
        this.game.step(direction);
        this.pacManView.update(game);
        this.scoreLabel.setText(String.format("Score: %d", this.game.getScore()));
        this.levelLabel.setText(String.format("Level: %d", this.game.getLevel()));
        setLives(this.game.getLives());
    }

	/**
     * Takes in user keyboard input to control the movement of PacMan and start new games
     * keyEvent user's key click
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        Direction direction = Direction.NONE;
        if (code == KeyCode.LEFT) {
            direction = Direction.LEFT;
        } else if (code == KeyCode.RIGHT) {
            direction =Direction.RIGHT;
        } else if (code == KeyCode.UP) {
            direction = Direction.UP;
        } else if (code == KeyCode.DOWN) {
            direction = Direction.DOWN;
        } else if (code == KeyCode.ENTER) {
        	if(this.isTimerOn == false) {
        		startTimer();
        		isTimerOn = true;
        	}
        } 
        //Activates the Bomb Method 
        else if (code == KeyCode.SPACE) { 
            if(Game.getInstance().getCountOfBombDots()>0) {
            	if (Game.getInstance().getGhosts()[0].getGhostLocation()!=null && Game.getInstance().calculateDistanceBetweenPacmanAndGhost(Game.getInstance().getGhosts()[0])<3)
            		Game.getInstance().getGhosts()[0].sendGhostHomeAfter5sec();
            	if (Game.getInstance().getGhosts()[1].getGhostLocation()!=null && Game.getInstance().calculateDistanceBetweenPacmanAndGhost(Game.getInstance().getGhosts()[1])<3)
            		Game.getInstance().getGhosts()[1].sendGhostHomeAfter5sec();
            	if (Game.getInstance().getGhosts()[2].getGhostLocation()!=null && Game.getInstance().calculateDistanceBetweenPacmanAndGhost(Game.getInstance().getGhosts()[2])<3)
            		Game.getInstance().getGhosts()[2].sendGhostHomeAfter5sec();
            		
            	Game.getInstance().setCountOfBombDots(Game.getInstance().getCountOfBombDots()-1);
            }
           	direction = Game.getCurrentDirection();
        }
        //Activates the setting screen.
        else if (code == KeyCode.ESCAPE) { 
        	try {
        	pause();
            isTimerOn = false;
        	Stage gameStage = Game.getInstance().gameStage;
            Stage stage = new Stage();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
					startTimer();
                }});
            FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/settingGameScreen.fxml"));
            Parent root = loader.load();
	        stage.setTitle("Setting");
	        stage.setScene(new Scene(root));
	        stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gameStage);
	        stage.show();
	        root.requestFocus();
        }
        catch (IOException e) {
	        e.printStackTrace();
	    }
        } else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            game.setCurrentDirection(direction);
        }
    }

    /**
     * Pause the timer
     */
    public static void pause() {
            timer.cancel();
            paused = true;
            isTimerOn = false;
    }
    
    /**
     * UnPause the timer
     */
    public static void unPause() {
            timer.purge();
            paused = false;
            isTimerOn = true;
    }
    
    
    //Checks the amount of life in the game and changes if the Pacman is eaten by a ghost and print it on the game screen
    public void setLives(int live)
    {
    	switch(live)
    	{
    		case 1:
    			Image image1 = new Image("/images/lives_1.png");
    			ImageView imgv1 = new ImageView(image1);
                imgv1.setFitHeight(35);
    			imgv1.setPreserveRatio(true);
    			this.gameOverLabel.setGraphic(imgv1);
    			break;
    		case 2:
    			Image image2 = new Image("/images/lives_2.png");
    			ImageView imgv2 = new ImageView(image2);
    			imgv2.setFitHeight(35);
    			imgv2.setPreserveRatio(true);
    			this.gameOverLabel.setGraphic(imgv2);
                break;
    		default:
    			Image image3 = new Image("/images/lives_3.png");
    			ImageView imgv3 = new ImageView(image3);
    			imgv3.setFitHeight(35);
    			imgv3.setPreserveRatio(true);
                this.gameOverLabel.setGraphic(imgv3);		
    	}
    }
    
    //Easy pop-up question screen
     public static void showEasyQuestion()
     {
         try {
     	   	Controller.pause();
     	   	FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/QuestionPopUp.fxml"));
             Parent root = loader.load();
             Stage stage = new Stage();
             stage.setTitle("Easy Question");
             stage.setScene(new Scene(root, 680, 450));
             stage.initModality(Modality.WINDOW_MODAL);
             stage.initOwner(Game.getInstance().gameStage);
             stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                 @Override
                 public void handle(WindowEvent e) {
         			Game.getInstance().setScore(Game.getInstance().getScore() - Constants.EASY_QUEST_PUNISH);
         			Game.getInstance().releaseGame();
                 }
               });
             stage.show();
             stage.setUserData(new Integer(1));
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }
     
     //Medium pop-up question screen
     public static void showMediumQuestion()
     {
         try {
     	   	Controller.pause();
     	   	FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/QuestionPopUp.fxml"));
             Parent root = loader.load();
             Stage stage = new Stage();
             stage.setTitle("Medium Question");
             stage.setScene(new Scene(root, 680, 450));
             stage.initModality(Modality.WINDOW_MODAL);
             stage.initOwner(Game.getInstance().gameStage);
             stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                 @Override
                 public void handle(WindowEvent e) {
         			Game.getInstance().setScore(Game.getInstance().getScore() - Constants.MEDIUM_QUEST_PUNISH);
         			Game.getInstance().releaseGame();
                 }
               });
             stage.show();
             stage.setUserData(new Integer(1));
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }
     
     //Hard pop-up question screen
     public static void showHardQuestion()
     {
         try {
     	   	Controller.pause();
     	   	FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/QuestionPopUp.fxml"));
             Parent root = loader.load();
             Stage stage = new Stage();
             stage.setTitle("Hard Question");
             stage.setScene(new Scene(root, 680, 450));
             stage.initModality(Modality.WINDOW_MODAL);
             stage.initOwner(Game.getInstance().gameStage);
             stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                 @Override
                 public void handle(WindowEvent e) {
         			Game.getInstance().setScore(Game.getInstance().getScore() - Constants.HARD_QUEST_PUNISH);
         			Game.getInstance().releaseGame();
                 }
               });
             stage.show();
             stage.setUserData(new Integer(1));
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }
     
    
   //ends the game if the player Won or lost in it.
    //Gives different screens if the player won or lost.
    public static void endGame() {
        //if the player lose
    	if (Game.getInstance().getScore() < 200 && Game.getInstance().gameStage != null) {
	    	try {
	    		
	        	Stage stage = Game.getInstance().gameStage;	
	     	   	FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/YouLoseGameOver.fxml"));
	     	   	Parent root = loader.load();
	            stage.setTitle("Game Over");
	            stage.setScene(new Scene(root));
	            stage.show();
	            root.requestFocus();
	        }
	        catch (Exception e) {
	        }
	    	finally
	    	{
		        pause();
		        Game.getInstance().restartGame();
		        paused = false;
	    	}
	    }
    	// if the player win
    	else {
	    	try {
	        	Stage stage = Game.getInstance().gameStage;	
	     	   	FXMLLoader loader = new FXMLLoader(Game.getInstance().getClass().getResource("/View/YouWinScreen.fxml"));
	     	   	Parent root = loader.load();
	            stage.setTitle("You Win");
	            stage.setScene(new Scene(root));
	            stage.show();
	            root.requestFocus();
	        }
	        catch (IOException e) {

	        }
	    	finally
	    	{
		        pause();
		        Game.getInstance().restartGame();
		        paused = false;
	    	}
    	}
    }
    
    //add question to the SysData in the model
	public void addQuestion(Question q) {
		sysData.getInstance().addQuestion(q);
	}
    
	//Getter and Setters
    public double getBoardWidth() {
        return PacManView.CELL_WIDTH * this.pacManView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacManView.CELL_WIDTH * this.pacManView.getRowCount();
    }

    public static String getLevelFile(int x)
    {
        return levelFiles[x];
    }

    public boolean getPaused() {
        return paused;
    }
	public static void setPaused(boolean paused) {
		Controller.paused = paused;
	}
    
    
}
