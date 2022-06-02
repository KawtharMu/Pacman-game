package Model;

import Utils.QuestionDifficulty;

public class Question {
	
	private String question;
	private String answer1; 
	private String answer2;
	private String answer3;
	private String answer4;
	private int correctAnswer; 
	private QuestionDifficulty qDifficulty; //1 -> easy, 2 -> medium, 3 -> hard
	private String teamName;
	
	
	public Question(String question, String answer1, String answer2, String answer3, String answer4,
			int correctAnswer, QuestionDifficulty qDifficulty, String teamName) {
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.correctAnswer = correctAnswer;
		this.qDifficulty = qDifficulty;
		this.teamName = teamName;
	}


	//Getters and Setters
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public QuestionDifficulty getQDifficulty() {
		return qDifficulty;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public void setQDifficulty(QuestionDifficulty qDifficulty) {
		this.qDifficulty = qDifficulty;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getCorrectAnswer() {
		switch (this.correctAnswer) {
		case 1:
			return getAnswer1();
		case 2:
			return getAnswer2();
		case 3:
			return getAnswer3();
		case 4:
			return getAnswer4();
		}
		return "";
	}
	
	public int getCorrectAnswerNumber(){
		return this.correctAnswer;
	}
	
	
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}
