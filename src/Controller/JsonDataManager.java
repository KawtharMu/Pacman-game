package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import Model.Player;
import Model.Question;
import Model.SysData;
import Utils.Constants;
import Utils.QuestionDifficulty;
import View.QuestionController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class JsonDataManager {
	
	private static int idQ = 0;

	private static JsonDataManager instance;

	private JsonDataManager() {}

	public static JsonDataManager getInstance() {
		if(instance == null)
			instance = new JsonDataManager();
		return instance;
	}
	/**
	 * 
	 * reads the question json file and add it to the model (SysData)
	 * 
	 */
	public HashMap<String, Question> readJSONDataFRomFile() {
		HashMap<String, Question> questionsMap = new HashMap<String, Question>();
		try(FileReader reader = new FileReader(Constants.JSON_QUESTION_FILE_PATH) ) //Constant Path to the json question file
		{
			// Deserialize / parse the json file and get the root object.
			JsonObject json = (JsonObject)Jsoner.deserialize(reader);
		    // read the json array
			JsonArray questions = (JsonArray)json.get("questions");
			// parse json array to arraylist
			questions.forEach(quest -> {
				JsonObject questObj = (JsonObject)quest;
				Question q = questValid(questObj);
				if(q != null)
				{
					questionsMap.put(q.getQuestion(), q);
				}
			});	
		}
		catch(Exception e) {
			if(idQ == 0)
			{
				idQ++;
				Alert a = new Alert(AlertType.INFORMATION);
	
		        // set content text
		        a.setContentText("There is no a Question Json File OR the Json schema is not correct!");
		        
		        a.show();
			}
		}
		return questionsMap; //Returns the questions hashmap.
	}
	
	public Question questValid(JsonObject questObj)
	{
		try
		{
			ArrayList<String> answersList = new ArrayList<>();
			int i = 0;
			String question = (String) questObj.get("question");
			int level = Integer.parseInt((String) questObj.get("level"));
			JsonArray answers =  (JsonArray) questObj.get("answers");
		    answers.forEach(answ -> {
		    	answersList.add(i,(String) answ);
		    });
			String team = (String) questObj.get("team");
		    int coorectAnswer = Integer.parseInt((String) questObj.get("correct_ans"));
		    Question q = new Question(question, answersList.get(3), answersList.get(2), 
		    		answersList.get(1), answersList.get(0), coorectAnswer, QuestionDifficulty.getDifficultybyID(level),team);
		    //checks validations
		    if(this.validateValue(q)){
		    	return q;
		    }
		    else
		    {
		    	return null;
		    }
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	
	
	
	/**
	* Write to the json file, use it when adding, deleting and changing a question
	*/
	public void writeQuestionsIntoJsonFile(HashMap<String, Question> questions) {
		JsonArray array = new JsonArray();
		//gets all the questions and adds it
		for (Question q : questions.values())
		{
			if(q!=null) {
				JsonObject obj = new JsonObject();
				obj.put("question", q.getQuestion());
				obj.put("level", QuestionDifficulty.getIDByDifficulty(q.getQDifficulty()));
				JsonArray answers = new JsonArray();
				answers.add(q.getAnswer1());
				answers.add(q.getAnswer2());
				answers.add(q.getAnswer3());
				answers.add(q.getAnswer4());
				obj.put("answers", answers);
				obj.put("correct_ans", q.getCorrectAnswerNumber()+"");
				array.add(obj);
			}
		}
		idQ++;
		JsonObject res = new JsonObject();
		res.put("questions", array);
		try (FileWriter file = new FileWriter(Constants.JSON_QUESTION_FILE_PATH)) 
		{
			file.write(res.toJson());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Reads the History Json file and returns the players(For the top ten screen)
	 * 
	 */
	public ArrayList<Player> readHistoryDataFRomFile() {
		HashMap<String, String> histsMap = new HashMap<String, String>();
		ArrayList<Player> players = new ArrayList<Player>();
		try(FileReader reader = new FileReader(Constants.JSON_HISTORY_FILE_PATH) ){
			// Deserialize / parse the json fileand get the root object.
			JsonObject json = (JsonObject)Jsoner.deserialize(reader);
		    // read the json array
			JsonArray questions = (JsonArray)json.get("Histories");
			// parse json array to arraylist
			questions.forEach(hist -> {
				JsonObject hObj = (JsonObject)hist;
				String name = (String) hObj.get("Player Name");
				String score =(String) hObj.get("Player Score");
				String date = (String) hObj.get("Player Date");
				Player p = new Player(name, Integer.parseInt(score), date);
				players.add(p);
				histsMap.put(name, score);
			});		
		}catch(Exception e) {
			Alert a = new Alert(AlertType.INFORMATION);
			
	        // set content text
	        a.setContentText("There is no History Json File OR the Json schema is not correct!");
	        
	        a.show();
		}
		return players;
	}
	/**
	* When player finishes the game, it gets the name and score and write it onto the json file.
	* we erase the json's info and adds the new one
	*/
	public void writeHistoryIntoJsonFile() {
		try(FileReader reader = new FileReader(Constants.JSON_HISTORY_FILE_PATH) ){
			// Deserialize / parse the json fileand get the root object.
			JsonObject json = (JsonObject)Jsoner.deserialize(reader);
		    // read the json array
			JsonArray data = (JsonArray)json.get("Histories");
			data.clear();
			for(Player p : SysData.getInstance().getHistoryGamesForShow()) {
				data.add(saveMethod(p.getName(), p.getScore().toString(), p.getDate()));
			}
			JsonObject res = new JsonObject();                     
			res.put("Histories", data);
			writeJsonObject(res);
				
		}catch(FileNotFoundException e) {
			JsonArray data = new JsonArray();
			for(Player p : SysData.getInstance().getHistoryGamesForShow()) {
				data.add(saveMethod(p.getName(), p.getScore().toString(), p.getDate()));
			}
			JsonObject res = new JsonObject();
			res.put("Histories", data);
			writeJsonObject(res);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Saves the Player's info.
	 */
	public JsonObject saveMethod(String playerName, String score, String date) {
		JsonObject obj = new JsonObject();
		obj.put("Player Name", playerName);
		obj.put("Player Score", score);
		obj.put("Player Date", date);
		return obj;
	}
	/**
	 * 
	 * Writes to the Json file, we call it from the write history method
	 */
	public void writeJsonObject(JsonObject obj) {
		try (FileWriter file = new FileWriter(Constants.JSON_HISTORY_FILE_PATH)) {
			file.write(obj.toJson());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Checks validation of each question in the json file
	private boolean validateValue(Question q) {
		try {
			int qnum = Integer.parseInt(QuestionDifficulty.getIDByDifficulty(q.getQDifficulty()));
			
			if(qnum!=1 && qnum!=2 && qnum!=3) {
				return false;
			}
			int caNum = q.getCorrectAnswerNumber();
			String content = q.getQuestion();
			String a1 = q.getAnswer1(), a2 = q.getAnswer2(), a3 = q.getAnswer3(), a4 = q.getAnswer4();
			if(content == null || content.isEmpty()) {
				return false;
			}
			if(a1 == null || a1.isEmpty()) {
				return false;
			}
			if(a2 == null || a2.isEmpty()) {
				return false;
			}
			if(a3 == null || a3.isEmpty()) {
				return false;
			}
			if(a4 == null || a4.isEmpty()) {
				return false;
			}
			if(!(caNum>=1 && caNum <=4)) {
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
