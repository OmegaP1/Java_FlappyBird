package User;

public class User {

	private int highScore;
	private String name;
	private Image img;

	public User() {
		img = new Image();
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public User(String name, int highScore) {
		this.highScore = highScore;
		this.name = name;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
