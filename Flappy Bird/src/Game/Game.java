package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Birds.Flyingbird;
import PowerUps.Imortal;
import User.Reader;
import User.User;

public class Game implements ActionListener, MouseListener, KeyListener {

	// public
	public static Game game;

	// private
	private final int WIDTH = 800, HEIGHT = 800;
	private ArrayList<Rectangle> coluns;
	private int ticks, yMotion, score;
	private JFrame app = new JFrame();
	private RenderGame render;
	private Random rand;
	private boolean gameOver, started, imortalBoolPipe = true, newImortal = true;
	private Flyingbird flyingBird;
	private User user = new User();
	private Reader reader = new Reader();
	DealWithImortal v;
	private Imortal imortal;

	public Game() {
		user.setName(JOptionPane.showInputDialog("Indique o seu user:"));
		reader.CheckNicks(user);
		Timer timer = new Timer(20, this);
		startApp();
		flyingBird = new Flyingbird();
		imortal = new Imortal();
		coluns = new ArrayList<Rectangle>();
		addColum(true);
		addColum(true);
		addColum(true);
		addColum(true);
		v = new DealWithImortal();
		v.start();
		timer.start();
	}

	public void startApp() {
		render = new RenderGame();
		rand = new Random();
		app.add(render);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(WIDTH, HEIGHT);
		app.setTitle("Flappy game");
		app.addMouseListener(this);
		app.setLocationRelativeTo(null);
		app.addKeyListener(this);
		app.setResizable(false);
		app.setVisible(true);
	}

	public void jump() {
		if (gameOver) {
			flyingBird = new Flyingbird();
			coluns.clear();
//			user.getImg().setNewColor(user.getImg().getImage("joker.jpg"), "joker.jpg");
			//acho q precisa aqui de uma thread.sleeps
			imortalBoolPipe = true;
			yMotion = 0;
			score = 0;
			imortal = new Imortal();
			addColum(true);
			addColum(true);
			addColum(true);
			addColum(true);
			gameOver = false;
			DealWithImortal.interrupted();

		}
		if (!started) {
			started = true;
		} else if (!gameOver) {

			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
		}
	}

	public void addColum(boolean start) {
		int space = 300, width = 100, height = 50 + rand.nextInt(300);

		if (start) {
			coluns.add(new Rectangle(WIDTH + width + coluns.size() * 300, HEIGHT - height - 120, width, height));
			coluns.add(new Rectangle(WIDTH + width + (coluns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		} else {
			coluns.add(new Rectangle(coluns.get(coluns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			coluns.add(new Rectangle(coluns.get(coluns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColum(Graphics g, Rectangle colum) {
		g.setColor(Color.green.darker());
//		g.drawImage(getImage("SkyScraper.jpeg"), colum.x, colum.y, colum.width, colum.height, null);
	
		g.fillRect(colum.x, colum.y, colum.width, colum.height);

	}

	public void paintImortal(Graphics g, Rectangle imortal) {
		if (imortalBoolPipe && !gameOver) {
			g.drawImage(getImage("download.png"), imortal.x, imortal.y, imortal.width, imortal.height, null);
		}
		if (!imortalBoolPipe && !gameOver) {
			g.drawImage(getImage("download.png"), 400, 700, 70, 70, null);
		}
	}
	private BufferedImage getImage(String s) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(s));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		int speed = 10;
		ticks++;

		if (started) {

			for (int i = 0; i < coluns.size(); i++) {
				Rectangle colum = coluns.get(i);
				colum.x -= speed;
			}

			imortal.x -= 10;

			if (ticks % 2 == 0 && (yMotion < 15))
				yMotion += 2;

			for (int x = 0; x < coluns.size(); x++) {
				Rectangle colum = coluns.get(x);

				if (colum.x + colum.width < 0) {
					coluns.remove(colum);

					if (colum.y == 0) {
						addColum(false);
					}

				}
			}

			flyingBird.y += yMotion;

			for (Rectangle colum : coluns) {

				if ((colum.y == 0) && (flyingBird.x() + flyingBird.width()) / 2 > colum.x + colum.width / 2 - 5
						&& (flyingBird.x() + flyingBird.width()) / 2 < colum.x + colum.width / 2 + 5) {
					score++;
				}

				if (colum.intersects(flyingBird) && imortalBoolPipe) {
					gameOver = true;
					if (flyingBird.x <= colum.x) {

						flyingBird.x = colum.x - flyingBird.height;
					} else {
						if (colum.y != 0) {
							flyingBird.y = colum.y = flyingBird.height;
						} else if (flyingBird.y < colum.height) {
							flyingBird.y = colum.height;

						}
					}
				}

			}

			if (imortal.intersects(flyingBird)) {
				imortalBoolPipe = false;
				v.setFun(false);

			}

			if (flyingBird.y > HEIGHT - 120 || flyingBird.y < 0) {
				gameOver = true;
			}

			if (flyingBird.y + yMotion >= HEIGHT - 120) {
				flyingBird.y = HEIGHT - 120 - flyingBird.height;
			}
		}
		render.repaint();
	}

	public void setImortalBoolPipe(boolean imortalBoolPipe) {
		this.imortalBoolPipe = imortalBoolPipe;
	}
	
	

	public void repaint(Graphics g) {
		if (!gameOver) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.orange);
			g.fillRect(0, HEIGHT - 120, WIDTH, 150);

			g.setColor(Color.green);
			g.fillRect(0, HEIGHT - 120, WIDTH, 20);

	
			g.drawImage(user.getImg().getImg(), flyingBird.x, flyingBird.y,flyingBird.width,flyingBird.height, null);

			for (Rectangle colum : coluns) {
				paintColum(g, colum);
			}

		}

		paintImortal(g, imortal);

		g.setColor(Color.BLACK);

		g.setFont(new Font("Arial", 1, 70));
		g.drawString(user.getName(), 0, 760);

		g.setFont(new Font("Arial", 1, 100));

		g.setColor(Color.white);

		if ((!started))
			g.drawString("Click to start!", 100, HEIGHT / 2 - 50);

		if (gameOver) {
			user.setHighScore(score);
			reader.WriteNewScore(user);
			reader.WriteColors(user);
			g.drawString("Game Over!", 75, HEIGHT / 2 - 50);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);

		}

	}

	public void HighScores(Graphics g) {

		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 600, 600);

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 40));

		g.drawString("HighScores ( top 5 )", 0, 40);

		g.setFont(new Font("Arial", 1, 20));
		int cont = 0, number = 1, five = 0;
		for (User user : reader.getListOfUsers()) {
			if (five < 5) {
				g.drawString(number + " : " + user.getName() + "-" + user.getHighScore(), 30, 100 + cont);
				cont += 60;
				number++;
				five++;
			}
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 25));
		g.drawString("Press SPACE to PLAY!", 60, 400);

	}
	
	
	public void Shop(Graphics g) {
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 800, 800);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		g.drawString("Shop", 0, 80);
		
		
		
	}
	
	public KeyListener AddKeyListener(JFrame app2) {
		KeyListener k = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					app2.setVisible(false);
					app.setVisible(true);
					score = 0;

				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		};
		return k;
	}

	public void ShowHighScores() {
		JFrame appHS = new JFrame();
		appHS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appHS.setSize(400, 500);
		appHS.setTitle("HighScores");
		appHS.setLocationRelativeTo(null);
		appHS.setResizable(true);
		RenderHighScores j = new RenderHighScores();
		appHS.add(j);
		KeyListener k = AddKeyListener(appHS);
			
		appHS.addKeyListener(k);

		app.setVisible(false);
		appHS.setVisible(true);

	}
	
	public void ShowShop() {
		JFrame appSS = new JFrame();
		appSS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appSS.setSize(800, 800);
		appSS.setTitle("Shop");
		appSS.setLocationRelativeTo(null);
		appSS.setResizable(true);
		appSS.setVisible(true);
		RenderShop s = new RenderShop();
		appSS.add(s);
		KeyListener k = AddKeyListener(appSS);
		appSS.addKeyListener(k);
		app.setVisible(false);
		appSS.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);

		}

		if (gameOver) {
			if (e.getKeyCode() == KeyEvent.VK_H) {
				ShowHighScores();
			}

			if (e.getKeyCode() == KeyEvent.VK_S) {
				ShowShop();
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public static void main(String[] args) {

		game = new Game();
	}

	private class DealWithImortal extends Thread {

		private Boolean fun = true;

		@Override
		public void run() {
			while (true) {
				if (fun) {

					if (flyingBird.x - 400 > imortal.x && newImortal) {
						imortal = new Imortal();
						newImortal = false;
						sleepFor(5000);
						newImortal = true;

					}

				} else {
					setImortalBoolPipe(false);
					sleepFor(2500);
					setImortalBoolPipe(true);
					fun = true;
					sleepFor(5000);
					imortal = new Imortal();

				}

			}

		}

		public void sleepFor(int a) {
			try {
				sleep(a);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void setFun(Boolean fun) {
			this.fun = fun;
		}
	}

}
