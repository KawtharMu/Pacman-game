package View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import Controller.Controller;
import Controller.JsonDataManager;
import Model.Question;
import Model.SysData;
import Utils.QuestionDifficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class EditQuestionController implements Initializable{

	@FXML
	private TextField txfQA1;
	@FXML
	private TextField txfQA4;
	@FXML
	private TextField txfQA3;
	@FXML
	private TextField txfQA2;
	@FXML
	private TextField txfCA;
	@FXML
	private TextField txfQcontent;
    @FXML
    private Button BackButton;

    private Question currQuestion;
    public int questNumStr;
    private Stage stage;
    private int position;
    
    
    private ArrayList<String> easyKeys  = new ArrayList<String>();
    private ArrayList<String> mediumKeys  = new ArrayList<String>();
    private ArrayList<String> hardKeys  = new ArrayList<String>();

    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	questNumStr = QuestionController.questNumStr;
    	this.position=0;
		switch(questNumStr) {
			case 1:
				Set<String> easy = SysData.getInstance().getEasyQuestions().keySet();
				this.easyKeys = new ArrayList<String>(easy);
				currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(position));
				
				break;
			case 2:
				Set<String> medium = SysData.getInstance().getMediumQuestions().keySet();
				this.mediumKeys = new ArrayList<String>(medium);
				
				currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(position));
				break;
			case 3:
				Set<String> hard = SysData.getInstance().getHardQuestions().keySet();
				this.hardKeys = new ArrayList<String>(hard);
				currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(position));
				break;
		}
		txfQcontent.setText(currQuestion.getQuestion());
		txfQA1.setText(currQuestion.getAnswer1());
		txfQA2.setText(currQuestion.getAnswer2());
		txfQA3.setText(currQuestion.getAnswer3());
		txfQA4.setText(currQuestion.getAnswer4());
		int corect=currQuestion.getCorrectAnswerNumber();
		txfCA.setText(String.valueOf(corect));		
	}
	
  
    //Returns to the type screen
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
  

    @FXML
    void PreviousButton(ActionEvent event) {
    	SwitchDataPrevious(); //Calls a method
    	

    }
    

    @FXML
    void nextButton(ActionEvent event) throws IOException {
    	SwitchDataNext(); //Calls a method
    	
    }
    

    //Edit The new question to the database
    @FXML
    void EditButton(ActionEvent event) 
    {
		SysData.getInstance().getQuestions().remove(currQuestion.getQuestion());
		Question q = validateValue();
		if(q!=null) {
			Controller.getInstance().addQuestion(q);
			SysData.getInstance().putQuestions(SysData.getInstance().getQuestions());
		   JsonDataManager.getInstance().writeQuestionsIntoJsonFile(SysData.getInstance().getQuestions());
		   switch(questNumStr) {
			case 1:
				position=0;
				this.easyKeys.remove(currQuestion.getQuestion());
				this.easyKeys.add(q.getQuestion());
				currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(position));
				break;
			case 2:
				position=0;
				this.mediumKeys.remove(currQuestion.getQuestion());
				this.mediumKeys.add(q.getQuestion());
				currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(position));
				break;
			case 3:
				position=0;
				this.hardKeys.remove(currQuestion.getQuestion());
				this.hardKeys.add(q.getQuestion());
				currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(position));
				break;
		    }
			txfQcontent.setText(currQuestion.getQuestion());
			txfQA1.setText(currQuestion.getAnswer1());
			txfQA2.setText(currQuestion.getAnswer2());
			txfQA3.setText(currQuestion.getAnswer3());
			txfQA4.setText(currQuestion.getAnswer4());
			int corect=currQuestion.getCorrectAnswerNumber();
			txfCA.setText(String.valueOf(corect));
			Alert al = new Alert(AlertType.INFORMATION);
			al.setContentText("Edited Successfully!");
			al.show();
	    }  
	}


 
    //Deletes the Questions when pressing the button
    @FXML
    void DeleteButton(ActionEvent event) {
       String content = txfQcontent.getText();
	   questNumStr = QuestionController.questNumStr;

       
	   if( SysData.getInstance().getQuestions().containsKey(content))
	   {
			Alert al = new Alert(AlertType.CONFIRMATION);
			al.setContentText("Are you sure you want to delete this question?");
			Optional<ButtonType> result = al.showAndWait();
		    if(result.get() == ButtonType.OK) {
			   SysData.getInstance().getQuestions().remove(content);
			   SysData.getInstance().putQuestions(SysData.getInstance().getQuestions());
			   switch(questNumStr) 
			   {
				case 1:
					this.easyKeys.remove(content);
					break;
				case 2:
					this.mediumKeys.remove(content);
					break;
				case 3:
					this.hardKeys.remove(content);
					break;
			   }
			   position = 0;
			   if(!this.easyKeys.isEmpty() || !this.mediumKeys.isEmpty() || !this.hardKeys.isEmpty())
			   {
				   JsonDataManager.getInstance().writeQuestionsIntoJsonFile(SysData.getInstance().getQuestions());
				   switch(questNumStr) {
					case 1:
						currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(position));
						break;
					case 2:
						currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(position));
						break;
					case 3:
						currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(position));
						break;
				    }
					txfQcontent.setText(currQuestion.getQuestion());
					txfQA1.setText(currQuestion.getAnswer1());
					txfQA2.setText(currQuestion.getAnswer2());
					txfQA3.setText(currQuestion.getAnswer3());
					txfQA4.setText(currQuestion.getAnswer4());
					int corect=currQuestion.getCorrectAnswerNumber();
					txfCA.setText(String.valueOf(corect));
					return;
			    }
		    }
	    	if(this.easyKeys.isEmpty() && this.mediumKeys.isEmpty() && this.hardKeys.isEmpty())
	    	{
				JsonDataManager.getInstance().writeQuestionsIntoJsonFile(SysData.getInstance().getQuestions());
		    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/QuestionType.fxml"));
		        Parent root;
				try {
					root = loader.load();
					stage.setTitle("PacMan");
			        stage.setScene(new Scene(root));
			        stage.show();
			        root.requestFocus();
			        Alert al2 = new Alert(AlertType.CONFIRMATION);
			        switch(questNumStr) {
						case 1:
							al2.setContentText("There are no easy questions in the Json");
							al2.show();
							break;
						case 2:
							al2.setContentText("There are no medium questions in the Json");
							al2.show();
							break;
						case 3:
							al2.setContentText("There are no hard questions in the Json");
							al2.show();
							break;
			        }
			        
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    }
			
	    }

    
    //Next Question
	private void SwitchDataNext() {
		
		questNumStr = QuestionController.questNumStr;
		position++;
		//Checks the question's difficulty
		if(!this.easyKeys.isEmpty() || !this.mediumKeys.isEmpty() || !this.hardKeys.isEmpty())
		{
			switch(questNumStr) {
				case 1:
					if(easyKeys.size()==position || position == -1)
						position=0;
					currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(position));
					break;
				case 2:
					if(mediumKeys.size()==position || position == -1)
						position=0;
					currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(position));
					break;
				case 3:
					if(hardKeys.size()==position || position == -1)
						position=0;
					currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(position));
					break;
			}
			txfQcontent.setText(currQuestion.getQuestion());
			txfQA1.setText(currQuestion.getAnswer1());
			txfQA2.setText(currQuestion.getAnswer2());
			txfQA3.setText(currQuestion.getAnswer3());
			txfQA4.setText(currQuestion.getAnswer4());
			int corect=currQuestion.getCorrectAnswerNumber();
			txfCA.setText(String.valueOf(corect));
		}
		else
		{
			Alert a = new Alert(AlertType.NONE);
	        a.setAlertType(AlertType.ERROR);

	        // set content text
	        a.setContentText("there is no question in the json, please add first!");
		}
		
		
	}
    
	//Previous Question
	private void SwitchDataPrevious() {
		
		questNumStr = QuestionController.questNumStr;
		position--;
		//Checks the question's difficulty
		if(!this.easyKeys.isEmpty() || !this.mediumKeys.isEmpty() || !this.hardKeys.isEmpty())
		{
			switch(questNumStr) {
				case 1:
					if(position==-1 || position == this.easyKeys.size() - 1)
						position=easyKeys.size()-1;
					currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(position));
					break;
				case 2:
					Set<String> medium = SysData.getInstance().getMediumQuestions().keySet();
					ArrayList<String> mediumKeys = new ArrayList<String>(medium);
					if(position==-1 || position == -1)
						position=mediumKeys.size()-1;
					currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(position));
					break;
				case 3:
					if(position==-1 || position == -1)
						position=hardKeys.size()-1;
					currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(position));
					break;
			}
			txfQcontent.setText(currQuestion.getQuestion());
			txfQA1.setText(currQuestion.getAnswer1());
			txfQA2.setText(currQuestion.getAnswer2());
			txfQA3.setText(currQuestion.getAnswer3());
			txfQA4.setText(currQuestion.getAnswer4());
			int corect=currQuestion.getCorrectAnswerNumber();
			txfCA.setText(String.valueOf(corect));
		}
		else
		{
			Alert a = new Alert(AlertType.NONE);
	        a.setAlertType(AlertType.ERROR);

	        // set content text
	        a.setContentText("there is no question in the json, please add first!");
		}
	}
    /**
	 * 
	 * Checks for validations
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
			int qnum = QuestionController.questNumStr;
			
			if(qnum!=1 && qnum!=2 && qnum!=3) {
				errorMsg += "The Questions level Value Must Be 1 Or 2 Or 3.\n";
			}
			try {
				int caNum = Integer.parseInt(ca);
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
