package View;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import Model.Game;
import Model.Question;
import Model.SysData;
import Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;



public class QuestionPopUpView implements Initializable {
    
    
    @FXML
    private Button submitButton;

    @FXML
    private TextArea questionArea;
    
    @FXML
    private ToggleGroup group1;

    @FXML
    private RadioButton answer1RButton;

    @FXML
    private RadioButton answer2RButton;

    @FXML
    private RadioButton answer3RButton;

    @FXML
    private RadioButton answer4RButton;

    private Question currQuestion;
    private int selectedAnswer;
    private int whichQuestionAte;
    private Stage stage;

    
    //Intiate when the pacman eats a question dot
    //Initiate the question dot's type to know which question to put on screen
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		whichQuestionAte = Game.getInstance().whichQuestionAte;
		Random rand = new Random();
		int randIndex = 0;
		switch(whichQuestionAte) {
			case 1:
				Set<String> easy = SysData.getInstance().getEasyQuestions().keySet();
				ArrayList<String> easyKeys = new ArrayList<String>(easy);
				randIndex = rand.nextInt(easyKeys.size());
				currQuestion = SysData.getInstance().getEasyQuestions().get(easyKeys.get(randIndex));
				break;
			case 2:
				Set<String> medium = SysData.getInstance().getMediumQuestions().keySet();
				ArrayList<String> mediumKeys = new ArrayList<String>(medium);
				randIndex = rand.nextInt(mediumKeys.size());
				currQuestion = SysData.getInstance().getMediumQuestions().get(mediumKeys.get(randIndex));
				break;
			case 3:
				Set<String> hard = SysData.getInstance().getHardQuestions().keySet();
				ArrayList<String> hardKeys = new ArrayList<String>(hard);
				randIndex = rand.nextInt(hardKeys.size());
				currQuestion = SysData.getInstance().getHardQuestions().get(hardKeys.get(randIndex));
				break;
		}
		questionArea.setWrapText(true);
		questionArea.setText(currQuestion.getQuestion());
		answer1RButton.setWrapText(true);
		answer1RButton.setText(currQuestion.getAnswer1());
		answer2RButton.setWrapText(true);
		answer2RButton.setText(currQuestion.getAnswer2());
		answer3RButton.setWrapText(true);
		answer3RButton.setText(currQuestion.getAnswer3());
		answer4RButton.setWrapText(true);
		answer4RButton.setText(currQuestion.getAnswer4());
	}
	
		
    
    @FXML
    void answer1Action(ActionEvent event) {
    	selectedAnswer = 1;
    }

    @FXML
    void answer2Action(ActionEvent event) {
    	selectedAnswer = 2;
    }

    @FXML
    void answer3Action(ActionEvent event) {
    	selectedAnswer = 3;
    }

    @FXML
    void answer4Action(ActionEvent event) {
    	selectedAnswer = 4;
    }
    
    
    //Checks the answer (Right, Wrong, or Close the screen)
    @FXML
    void submitAction(ActionEvent event) throws AWTException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Alert alert = new Alert(AlertType.INFORMATION);    	
    	Optional<ButtonType> result = null;
    	//Checks if the player was right
    	if(selectedAnswer == currQuestion.getCorrectAnswerNumber()) {
    		switch(whichQuestionAte) {
			case 1:
				alert.setContentText("Correct answer, well done!\r\n" + 
						"You got 1 point");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() + Constants.EASY_QUEST_SCORE);
				break;
			case 2:
				alert.setContentText("Correct answer, well done!\r\n" + 
						"You got 2 points");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() + Constants.MEDIUM_QUEST_SCORE);
				break;
			case 3:
				alert.setContentText("Correct answer, well done!\r\n" + 
						"You got 3 points");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() + Constants.HARD_QUEST_SCORE);
				break;
    		}
    	}
    	//Wrong Answer
    	else {
    		switch(whichQuestionAte) {
			case 1:
				alert.setContentText("Wrong answer, try next time!\r\n" + 
						"The correct answer is:\r\n" + 
						currQuestion.getCorrectAnswer() +
						"\r\n You lost 10 points");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() - Constants.EASY_QUEST_PUNISH);
				break;
			case 2:
				alert.setContentText("Wrong answer, try next time!\r\n" + 
						"The correct answer is:\r\n" + 
						currQuestion.getCorrectAnswer() +
						"\r\n You lost 20 points");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() - Constants.MEDIUM_QUEST_PUNISH);
				break;
			case 3:
				alert.setContentText("Wrong answer, try next time!\r\n" + 
						"The correct answer is:\r\n" + 
						currQuestion.getCorrectAnswer() +
						"\r\n You lost 30 points");
				result = alert.showAndWait();
				Game.getInstance().setScore(Game.getInstance().getScore() - Constants.HARD_QUEST_PUNISH);
				break;
    		}
    	}
    		//Unpause the game
    		stage.close();
    		Robot robot = new Robot();
    		robot.keyPress(KeyEvent.VK_ENTER);
    		robot.keyRelease(KeyEvent.VK_ENTER);
    	//}

    }
    
    
    
}