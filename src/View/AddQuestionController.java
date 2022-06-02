package View;

import java.io.IOException;
import Controller.Controller;
import Model.Question;
import Model.SysData;
import Utils.QuestionDifficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddQuestionController  {
	
	  @FXML
	private TextField txfQuestNum;
	  @FXML
	private TextField txfCA;
	  @FXML
	private TextField txfQcontent;
	  @FXML
    private TextField txfQA1;
	  @FXML
    private TextField txfQA2;
	  @FXML
    private TextField txfQA3;
	  @FXML
    private TextField txfQA4;
	  
    private Stage stage;
    public int qnum;
    

    //Returns to the Type Question Screen
    @FXML
    void backButton(ActionEvent event) throws IOException {
    	 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/QuestionType.fxml"));
	        Parent root = loader.load();
	        
	        stage.setTitle("PacMan");
	        stage.setScene(new Scene(root));
	        stage.show();
	        root.requestFocus();

    }
    
    //If Question Added, checks it before adding to the json and database
    @FXML
    void handelAddQuestionButton(ActionEvent event) {

    	Question q = validateValue(); //Calls the method
    	
		if(q!=null) {
			Controller.getInstance().addQuestion(q);
			SysData.getInstance().putQuestions(SysData.getInstance().getQuestions());
			Alert al = new Alert(AlertType.INFORMATION);
			al.setContentText("The Question Added Succussfully");
			al.show();
			
		    txfQcontent.setText("");
			txfQA1.setText("");
			txfQA2.setText("");
			txfQA3.setText("");
			txfQA4.setText("");
			txfCA.setText("");
		}
    }
    
    /**
	 * 
	 * Checks validations
	 */
	private Question validateValue() {
		String errorMsg =null;
		
		String content = txfQcontent.getText();
		String a1 = txfQA1.getText();
		String a2 = txfQA2.getText();
		String a3 = txfQA3.getText();
		String a4 = txfQA4.getText();
		String ca = txfCA.getText();
	
		try {
			qnum=QuestionController.questNumStr;
			if(qnum!=1 && qnum!=2 && qnum!=3) {
				errorMsg += "The Questions level Value Must Be 1 Or 2 Or 3.\n";
			}
			try {
				if(SysData.getInstance().getQuestions().containsKey(content))
				{
					errorMsg += "There is already a similar question, Please Edit the question if you want.\n";
				}
				if(content == null || content.isEmpty()) {
					errorMsg += "Please Enter Question Content.\n";
				}
				if(a1 == null || a1.isEmpty()) {
					errorMsg += "Please Enter The First Answer .\n";
				}
				if(a2 == null || a2.isEmpty()) {
					errorMsg += "Please Enter The Second Answer .\n";
				}
				if(a3 == null || a3.isEmpty()) {
					errorMsg += "Please Enter The Third Answer .\n";
				}
				if(a4 == null || a4.isEmpty()) {
					errorMsg += "Please Enter The Fourth Answer .\n";
				}
				QuestionDifficulty type = null;
				switch (qnum) {
				case 1:
					type = QuestionDifficulty.EASY;
					break;
				case 2:
					type = QuestionDifficulty.MEDIUM;
					break;
				case 3:
					type = QuestionDifficulty.HARD;
					break;
				default:
					type = null;
					break;
				}
				int caNum = Integer.parseInt(ca);
				if(!(caNum>=1 && caNum <=4)) {
					errorMsg += "The Correct Answer value rang is (1-4) .\n";
				}
				if(type != null && errorMsg == null ) {
					Question q = new Question(content, a1, a2, a3, a4, caNum, type, "Girraf");
					return q;
				}else {
					Alert al = new Alert(AlertType.ERROR);
					al.setTitle("Questions Forms");
					al.setHeaderText("Error Details");
					al.setContentText(errorMsg.substring(4));
					al.show();
					return null;
				}
			}catch(NumberFormatException e) {
				errorMsg += "The Correct Answer Number Value Must Be Number.\n";
				Alert al = new Alert(AlertType.ERROR);
				al.setTitle("Questions Forms");
				al.setHeaderText("Error Details");
				al.setContentText(errorMsg.substring(4));
				al.show();
			}
		}catch(NumberFormatException e) {
			errorMsg += "The Questions Number Value Must Be Number.\n";
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Questions Forms");
			al.setHeaderText("Error Details");
			al.setContentText(errorMsg.substring(4));
			al.show();
		}
		return null;
	}
	

}
