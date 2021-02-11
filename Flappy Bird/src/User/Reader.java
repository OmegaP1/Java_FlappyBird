package User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Reader implements Comparator<User> {

	public ArrayList<User> ListOfUsers = new ArrayList<User>();
	public User user;
	public File file = new File("HighScores.txt");
	public File file2 = new File("Cores.txt");

	public Reader() {
		ReadFile();
		CheckColors(user);
	}

	public Reader(int a) {

	}

	public void ReadFile() {
		Scanner scan;
		try {
			scan = new Scanner(file);
			String line;
			String[] t = new String[2];
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				t = line.split("-");
				ListOfUsers.add(new User(t[0], Integer.parseInt(t[1])));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	//n funcionaa
	public String CheckColors(User user) {
		Scanner scan2;
		String line, result = null;
		try {
			scan2 = new Scanner(file2);
			while (scan2.hasNextLine()) {
//			for (User useer : ListOfUsers) {
//				if (user.getName().equals(useer.getName())) {
				line = scan2.nextLine();
				System.out.println(line);
				String[] t = line.split("-");

				for (int i = 1; i < t.length; i++) {
					result = result + t[i];
					System.out.println(t[i]);
//					}
//				}
				}
			}
			scan2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void WriteColors(User user) {

		try {
			PrintWriter print = new PrintWriter(file2);
			for (User useer : ListOfUsers) {
				//apaga o ficheiro a toa
				print.write(useer.getName() + "-" + WriteColor(user, CheckColors(useer)) + System.lineSeparator());
			}
			print.close();
		} catch (FileNotFoundException e) {
		}
	}

	// erro
	public String WriteColor(User user, String t) {
		for (int a = 0; a <= user.getImg().getImageList().size(); a++) {
			t = t + "-";
			t = user.getImg().getImageList().entrySet().iterator().next().getValue();
		}
		return t;

	}

	public void WriteNewScore(User user) {

		if (CheckNicks(new User(user.getName(), user.getHighScore()))) {
			ListOfUsers.add(new User(user.getName(), user.getHighScore()));
		}
		Collections.sort(ListOfUsers, new Reader(0));
		try {
			PrintWriter print = new PrintWriter(file);
			for (User useer : ListOfUsers) {
				print.write(useer.getName() + "-" + useer.getHighScore() + System.lineSeparator());
			}
			print.close();
		} catch (FileNotFoundException e) {
		}
	}

	public ArrayList<User> getListOfUsers() {
		return ListOfUsers;
	}

	public Boolean CheckNicks(User user) {
		for (User useer : ListOfUsers) {
			if (user.getName().equals(useer.getName())) {
				if (user.getHighScore() > useer.getHighScore()) {
					useer.setHighScore(user.getHighScore());
				}
				return false;
			}
		}
		return true;
	}

	@Override
	public int compare(User a, User b) {
		return b.getHighScore() - a.getHighScore();
	}

}
