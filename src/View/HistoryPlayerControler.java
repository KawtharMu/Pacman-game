package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import Model.Player;
import Model.SysData;


public class HistoryPlayerControler implements Initializable {
	
	
	 @FXML private Button Back;
	 @FXML private Label name1, name2, name3, name4, name5, name6, name7, name8, name9, name10;
	 @FXML private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
	 @FXML private Label date1, date2, date3, date4, date5, date6, date7, date8, date9, date10;
	 
 	private Stage stage;
    private Scene scene;
    private Parent root;
    
    private ArrayList<Player> topTen;

    //Initiate the Top Ten Screen
	@Override
	public void initialize(URL url, ResourceBundle rb) 
	{
		 Label [] nicknames = {name1, name2, name3, name4, name5, name6, name7, name8, name9, name10};
		 Label [] scores = {score1, score2, score3, score4, score5, score6, score7, score8, score9, score10};
		 Label [] dates = {date1, date2, date3, date4, date5, date6, date7, date8, date9, date10};
		 SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy  HH:mm");
		 Date date = new Date(System.currentTimeMillis());
		this.topTen = SysData.getInstance().getHistoryGamesForShow();
		ArrayList <String> names = new ArrayList<>();
		//Gets only the Top Ten Players and do a for loop on them
		if(this.topTen.size() < 10)
		{
			for(int i = 0; i < this.topTen.size(); i++)
			{
				nicknames[i].setText(topTen.get(i).getName());
				scores[i].setText(topTen.get(i).getScore().toString());
				dates[i].setText(topTen.get(i).getDate().toString());
				
			}
		}else
		{
			for(int i = 0; i < 10; i++)
			{
				nicknames[i].setText(topTen.get(i).getName());
				scores[i].setText(topTen.get(i).getScore().toString());
				dates[i].setText(topTen.get(i).getDate().toString());
				
			}
		}
	}
	
	//Back Button
    public void BackToMainMenu(ActionEvent event) throws IOException
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
