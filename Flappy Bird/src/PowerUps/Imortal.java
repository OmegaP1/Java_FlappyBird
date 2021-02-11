package PowerUps;

import java.awt.Rectangle;
import java.util.Random;

public class Imortal extends Rectangle  {

	private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 800, HEIGHT = 800;
	public Random RandomA= new Random(), RandomB = new Random();

	public Imortal() {
		this.setBounds(new Rectangle(650 + RandomB.nextInt(5000), RandomA.nextInt(580), 100, 100));
	}
	
}
