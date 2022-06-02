package Utils;

//Question Dufficulty Score and and punishment
public enum QuestionDifficulty {
	EASY("Easy",0),MEDIUM("Medium",1),HARD("Hard",2);
	private String name;
	private int score;
	private int punish;
	private QuestionDifficulty(String name,int difficulty) {
		this.name = name;
		switch (difficulty) {
		case 0:
			this.score = Constants.EASY_QUEST_SCORE;
			this.punish = Constants.EASY_QUEST_PUNISH;
			break;
		case 1:
			this.score = Constants.MEDIUM_QUEST_SCORE;
			this.punish = Constants.MEDIUM_QUEST_PUNISH;
			break;
		case 2:
			this.score = Constants.HARD_QUEST_SCORE;
			this.punish = Constants.HARD_QUEST_PUNISH;
			break;
		}
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public int getPunish() {
		return punish*-1;
	}
	public static QuestionDifficulty getDifficultybyID(int id) {
		if(id == 1) {
			return QuestionDifficulty.EASY;
		}else if(id == 2){
			 return QuestionDifficulty.MEDIUM;
		}else if(id == 3){
			return QuestionDifficulty.HARD;
		}else {
			return QuestionDifficulty.EASY; 
		}
	}

		
	public static String getIDByDifficulty(QuestionDifficulty difficulty) 
	{
		if(difficulty == QuestionDifficulty.EASY) {
			return "1";
		}else if(difficulty == QuestionDifficulty.MEDIUM){
			return "2";
		}else if(difficulty == QuestionDifficulty.HARD){
			return "3";
		}else {
			return "1";
		}
	}
		
}
	 
