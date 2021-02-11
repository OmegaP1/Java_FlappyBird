package Birds;

import java.awt.Rectangle;

public class Flyingbird extends Rectangle  {

	private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 800, HEIGHT = 800;

	public Flyingbird() {
		this.setBounds(new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 30, 30));
	}
	
	public int x() {
		return this.x;
	}
	public int width() {
		return this.width;
	}
	
}
