package Model;


public class Player {
	String name;
	Integer score;
	String date;
	
	public Player(String name, Integer score, String date) {
		super();
		this.name = name;
		this.score = score;
		this.date = date;
		
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", score=" + score + "]";
	}
}
