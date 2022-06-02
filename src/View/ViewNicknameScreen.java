package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import Controller.Controller;
import Model.Game;

public class ViewNicknameScreen {

    @FXML
    private TextField nickname;

    @FXML
    private Button playButton;
    private Stage stage;

 
    //Starts the game
    public void startGame(ActionEvent event) throws IOException
    {
    	String nicknameNoSpaces = nickname.getText().trim();
    	if(!nickname.getText().isEmpty() && !nicknameNoSpaces.equals("")) {
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/pacman.fxml"));
	        Parent root = loader.load();
	        stage.setTitle("PacMan");
	        Controller controller = loader.getController();
	        root.setOnKeyPressed(controller);
	        double sceneWidth = controller.getBoardWidth() + 20.0;
	        double sceneHeight = controller.getBoardHeight() + 100.0;
	        stage.setScene(new Scene(root, sceneWidth, sceneHeight));
	        stage.show();
	        root.requestFocus();
	        Game.getInstance().setPlayerName(nickname.getText());
	        Game.getInstance().gameStage = stage;
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setContentText("A nickname is required");
    		nickname.setText("");
    		alert.show();
    	}
    }
    
    //Back Button
    @FXML
    void backMainMenu(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuScreen.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }

}
