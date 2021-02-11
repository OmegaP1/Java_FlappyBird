package Game;

import java.awt.Graphics;
import javax.swing.JPanel;

public class RenderGame extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		Game.game.repaint(g);

	}

}
