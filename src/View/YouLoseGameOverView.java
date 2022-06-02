package View;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class YouLoseGameOverView {

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button playButton;
    
    private Stage stage;

    
    //Return to Menu screen
    @FXML
    void MainMenuAction(ActionEvent event) throws IOException {
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuScreen.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }
    
    //Return to Nickname Screen
    @FXML
    void playAgainAction(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/NicknameScreen.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }

}