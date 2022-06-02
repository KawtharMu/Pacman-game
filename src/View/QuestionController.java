package View;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

import Model.SysData;

public class QuestionController    {
	 
	    public static Stage stage;

	    public static  int questNumStr=0;
	  	
	    //Back Button
	    public void BackButton(ActionEvent event) throws IOException
	    {
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuScreen.fxml"));
	        Parent root = loader.load();
	        
	        stage.setTitle("PacMan");
	        stage.setScene(new Scene(root));
	        stage.show();
	        root.requestFocus();
	    }
	    
	    //Easy Questions Screen
	    @FXML
	    void updateEasyQuestion(ActionEvent event) throws IOException {
	    	if(!SysData.getInstance().getEasyQuestions().isEmpty())
	    	{
		    	questNumStr=1;
		        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditQuestions.fxml"));
		        Parent root = loader.load();
		        stage.setTitle("PacMan");
		        stage.setScene(new Scene(root));
		        stage.show();
		        root.requestFocus();
	    	}
	    	else
	    	{
	    		Alert a = new Alert(AlertType.NONE);
		        a.setAlertType(AlertType.ERROR);
	
		        // set content text
		        a.setContentText("there are no Easy Questions!");
		        
		        a.show();
	    	}
	       

	    }

	    //Medium Questions Screen
	    @FXML
	    void updateMediumQuestion(ActionEvent event) throws IOException {
	    	if(!SysData.getInstance().getMediumQuestions().isEmpty())
	    	{
		    	questNumStr=2;
		        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditQuestions.fxml"));
		        Parent root = loader.load();
		        stage.setTitle("PacMan");
		        stage.setScene(new Scene(root));
		        stage.show();
		        root.requestFocus();
	    	}
	    	else
	    	{
	    		Alert a = new Alert(AlertType.NONE);
		        a.setAlertType(AlertType.ERROR);
	
		        // set content text
		        a.setContentText("there are no Medium Questions!");
		        
		        a.show();
	    	}
		       

	    }
	    
	    
	    //Hard Questions Screen
	    @FXML
	    void updateHardQuestion(ActionEvent event) throws IOException {
	    	if(!SysData.getInstance().getHardQuestions().isEmpty())
	    	{
		    	questNumStr=3;
		        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditQuestions.fxml"));
		        Parent root = loader.load();
		        stage.setTitle("PacMan");
		        stage.setScene(new Scene(root));
		        stage.show();
		        root.requestFocus();
	    	}
	    	else
	    	{
	    		Alert a = new Alert(AlertType.NONE);
		        a.setAlertType(AlertType.ERROR);
	
		        // set content text
		        a.setContentText("there are no Hard Questions!");
		        
		        a.show();
	    	}
	    }
	    
	    //Adds Easy Question Screen
	    @FXML
	    void addEasyQuestion(ActionEvent event) throws IOException {
	    	questNumStr=1;
	    	Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();

	        try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addQuestion.fxml"));
	                Parent root = loader.load();
	                Scene scene = new Scene(root);
	                mainStage.setScene(scene);
	                mainStage.setTitle("Test Window");
	                mainStage.show();
	              
	            }
	                catch(Exception e){}
	    }
	    
	    //Adds Medium Question Screen
	    @FXML
	    void addMediumQuestion(ActionEvent event) {
	    	questNumStr=2;
	    	Stage mainStage =  (Stage)((Node)event.getSource()).getScene().getWindow();

	        try {
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addQuestion.fxml"));
	                Parent root = loader.load();
	                Scene scene = new Scene(root);
	                mainStage.setScene(scene);
	                mainStage.setTitle("Test Window");
	                mainStage.show();
	               
	            }
	                catch(Exception e){}

	    }

	    //Adds Hard Question Screen
	    @FXML
	    void addHardQuestion(ActionEvent event) {
	    	questNumStr=3;
	    	Stage mainStage =  (Stage)((Node)event.getSource()).getScene().getWindow();

	        try {
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addQuestion.fxml"));
	                Parent root = loader.load();
	                Scene scene = new Scene(root);
	                mainStage.setScene(scene);
	                mainStage.setTitle("Test Window");
	                mainStage.show();
	              
	            }
	                catch(Exception e){}

	    }

		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage) {
			this.stage = stage;
		}	 
	    
	    
	    
}
