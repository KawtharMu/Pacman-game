package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.json.simple.JsonObject;
import org.junit.jupiter.api.Test;
import Controller.JsonDataManager;
import Model.Game;
import Utils.Direction;
import Utils.QuestionDifficulty;

class testCase1 {

	//Checks if direction 1 is RIGHT
	@Test
	void test1() {
		Direction d = Game.intToDirection(1);
		assertEquals(d, Direction.RIGHT);
	}
	
	//Checks if it adds the Game's Info to the Json
	@Test
	void test2() {
		JsonObject obj = JsonDataManager.getInstance().saveMethod("me", "200", "20-12-2021 13:15");
		assertEquals(obj.toString(), "{Player Score=200, Player Name=me}");
	}
	
	//Test 3 is HARD
	@Test
	void test3() {
		QuestionDifficulty qd = QuestionDifficulty.getDifficultybyID(3);
		assertEquals(qd, QuestionDifficulty.HARD);
		
	}
	
	//Test 2 is MEDIUM
	@Test
	void test4() {
		String qd = QuestionDifficulty.getIDByDifficulty(QuestionDifficulty.MEDIUM);
		assertEquals(qd, "2");
		
	}
	
	//Test 1 is Easy
	@Test
	void test5() {
		String qd = QuestionDifficulty.getIDByDifficulty(QuestionDifficulty.EASY);
		assertEquals(qd, "1");
		
	}
	
}
