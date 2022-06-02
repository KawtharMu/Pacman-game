package View;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Optional;

import Controller.Controller;
import Model.Game;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class SettingScreen 
{

    @FXML
    private Button resume;

    @FXML
    private Button exit;
    
    private Stage stage;
    
    //Resume the game
    @FXML
    void resumeGame(ActionEvent event) throws AWTException 
    {
    	this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	this.stage.close();
    	Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
    }
    
    //Exit the game
    @FXML
    void exitScreen(ActionEvent event) throws AWTException, IOException {
    	// set alert type
    	this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Alert a = new Alert(AlertType.NONE);
        a.setAlertType(AlertType.CONFIRMATION);

        // set content text
        a.setContentText("It wont save your Score!");
        
        a.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	try {
            	stage.close();
        		Game.getInstance().gameStage.close();
        		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuScreen.fxml"));
                Parent root;
				root = loader.load();
                stage.setTitle("PacMan");
                stage.setScene(new Scene(root));
                stage.show();
                root.requestFocus();
                Game.getInstance().restartGame();
                Controller.getInstance().setPaused(false);
                } catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

    }
}