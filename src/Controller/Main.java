package Controller;

import Model.SysData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

	//Opens the menu.
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("PacMan");
        primaryStage.show();
        Image icon = new Image("/images/paco.png");
        primaryStage.getIcons().add(icon);
        SysData.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
