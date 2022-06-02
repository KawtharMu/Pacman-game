package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;


public class ViewMenu {

    @FXML
    private Button gameHistoryButton;

    @FXML
    private Button howToPlayButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button playButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Nickname Screen
    public void nicknameScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/NicknameScreen.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }
    
    //TopTen Screen
    @FXML
    public void TopTenPlayer(ActionEvent event) throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/scoreBoard.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }
    
    //Questions Screen
    @FXML
    void QuestionButton(ActionEvent event) throws IOException {
    
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/QuestionType.fxml"));

        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }
    
    
    //How To Play Screen
    public void howToPlayScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HowToPlay.fxml"));

        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setScene(new Scene(root));
        stage.show();
        root.requestFocus();
    }

}
