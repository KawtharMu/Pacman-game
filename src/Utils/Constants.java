package Utils;

public class Constants {
	
	public static final String JSON_QUESTION_FILE_PATH="src/Questions.json";
	/**
	 * 
	 */
	public static final String JSON_HISTORY_FILE_PATH="src/History.json";
	/**
	/**
	 *  The PacDot score.
	 */
	public static final int PECK_SCORE = 1;
	/**
	 * Score when the user answered the Easy Question correctly.
	 */
	public static final int EASY_QUEST_SCORE = 1;
	/**
	 * Score when the user answered the Medium Question correctly.
	 */
	public static final int MEDIUM_QUEST_SCORE = 2;
	/**
	 * Score when the user answered the Hard Question correctly.
	 */
	public static final int HARD_QUEST_SCORE = 3;
	/**
	 * Score to decrease when the user answer wrong the Easy Question.
	 */
	public static final int EASY_QUEST_PUNISH = 10;
	/**
	 * Score to decrease when the user answer wrong the Medium Question.
	 */
	public static final int MEDIUM_QUEST_PUNISH = 20;
	/**
	 * Score to decrease when the user answer wrong the Hard Question .
	 */
	public static final int HARD_QUEST_PUNISH = 30;
}
