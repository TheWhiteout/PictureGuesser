import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GUITest {

	// Getting screen size
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double ratio = screenSize.getHeight() / 1440;

	// Other fields
	static String guessText;
	static int count = 0;
	static int multiplier = 1;
	static int streakNumber = 0;
	static int scoreNumber = 0;
	static JFrame frame = new JFrame("Picture Guesser");

	public static void main(String[] args) {

		String[] answers = { "ball", "bear", "bottle", "briefcase", "camera", "cat", "chair", "computer", "couch",
				"cup", "deer", "doctor", "dog", "duck", "elephant", "fork", "fox", "fridge", "glass", "glasses",
				"goose", "grass", "hamster", "headphones", "horse", "keyboard", "knife", "laptop", "lion", "microwave",
				"monitor", "mouse", "notebook", "oven", "paper", "pen", "pencil", "penguin", "phone", "plate", "purse",
				"ruler", "shoes", "speakers", "spoon", "suit", "swan", "table", "tea", "toilet", "tree", "wallet",
				"watch", "water", "wolf" };

		// Randomizing the possible answers
		int[] order = new int[55];
		Random random = new Random();
		for (int i = 0; i < 55; i++) {
			order[i] = random.nextInt(55);
			for (int j = 0; j < i; j++) {
				if (order[i] == order[j]) {
					i--;
					break;
				}
			}
		}

		// Initializing main frame
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);

		// Label with countdown timer
		JLabel countdown = new JLabel();
		CountdownTimer timer = new CountdownTimer(ratio);
		frame.getContentPane().add(timer.countdown);
		frame.getContentPane().add(countdown);

		// Other elements at the bottom
		JTextField text = new JTextField();
		setBoundsScreen(text, 1130, 1360, 300, 40);
		text.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		text.setHorizontalAlignment(JTextField.CENTER);
		frame.getContentPane().add(text);

		JLabel score = new JLabel("Score: 0");
		setBoundsScreen(score, 100, 1360, 300, 40);
		score.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		score.setForeground(Color.white);
		frame.getContentPane().add(score);

		JLabel streak = new JLabel("Streak: 0");
		setBoundsScreen(streak, 590, 1360, 200, 40);
		streak.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		streak.setForeground(Color.white);
		frame.getContentPane().add(streak);

		// Label displaying the pictures the player will be guessing
		JLabel picture = new JLabel(
				getScaledIcon(new ImageIcon(GUITest.class.getResource("/Resources/" + answers[order[count]] + ".jpg"))),
				JLabel.CENTER);
		setBoundsScreen(picture, 0, 0, 2560, 1310);
		frame.getContentPane().add(picture);

		// Here the player submits his guess and proceeds if correct
		JButton guess = new JButton("Guess");
		setBoundsScreen(guess, 1500, 1360, 110, 40);
		guess.setFont(new Font("Tahoma", Font.BOLD, screen(20)));
		guess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guessText = text.getText();
				text.setText("");
				if (guessText.toLowerCase().equals(answers[order[count]])) {
					count++;
					streakNumber++;
					scoreNumber += multiplier;
					if (streakNumber % 10 == 0) {
						multiplier++;
					}
					picture.setIcon(getScaledIcon(
							new ImageIcon(GUITest.class.getResource("/Resources/" + answers[order[count]] + ".jpg"))));
					score.setText("Score: " + scoreNumber);
					streak.setText("Streak: " + streakNumber);
					timer.add3sec();
				} else {
					streakNumber = 0;
					multiplier = 1;
					streak.setText("Streak: " + streakNumber);
				}
			}
		});
		frame.getContentPane().add(guess);

		// Skipping the current picture for a penalty
		JButton skip = new JButton("Skip");
		setBoundsScreen(skip, 960, 1360, 100, 40);
		skip.setFont(new Font("Tahoma", Font.BOLD, screen(20)));
		skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setText("");
				streakNumber = 0;
				multiplier = 1;
				try {
					timer.subtract10sec();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				count++;
				picture.setIcon(getScaledIcon(
						new ImageIcon(GUITest.class.getResource("/Resources/" + answers[order[count]] + ".jpg"))));
				streak.setText("Streak: " + streakNumber);
			}
		});
		frame.getContentPane().add(skip);

		// Initializing start frame
		JFrame startframe = new JFrame("Picture Guesser");
		startframe.setSize(500, 300);
		startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startframe.setResizable(false);
		startframe.getContentPane().setLayout(null);
		startframe.getContentPane().setBackground(Color.BLACK);

		JLabel title = new JLabel("Picture Guesser");
		title.setBounds(0, 0, 500, 100);
		title.setFont(new Font("Tahoma", Font.BOLD, 50));
		title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		startframe.getContentPane().add(title);

		JLabel description = new JLabel("Guess what is in the picture before the time runs out!");
		description.setBounds(0, 100, 500, 100);
		description.setFont(new Font("Tahoma", Font.BOLD, 17));
		description.setForeground(Color.white);
		description.setHorizontalAlignment(JLabel.CENTER);
		startframe.getContentPane().add(description);

		// Show main frame, start timer and close start frame
		JButton start = new JButton("Start");
		start.setBounds(200, 200, 100, 40);
		start.setFont(new Font("Tahoma", Font.BOLD, 20));
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
				timer.start();
				startframe.dispose();
			}
		});
		startframe.add(start);

		// Setting the start frame visibility to true
		startframe.setVisible(true);
	}

	// Methods for resizing this app for different screens
	static int screen(int original) {
		int ready = (int) (original * ratio);
		return ready;
	}

	static void setBoundsScreen(JComponent component, int x, int y, int width, int height) {
		component.setBounds(screen(x), screen(y), screen(width), screen(height));
	}

	// When the countdown is over - show score and close frame
	static void finalScore() throws IOException {
		// Reading the high score from a file and updating it
		File file = new File((GUITest.class.getResource("Resources/highscore.txt") + "").substring(6));
		FileReader fileReader = new FileReader(file);
		char[] digits = new char[4];
		fileReader.read(digits);
		fileReader.close();
		int highscore = Integer.parseInt(new String(digits).trim());

		// Setting the colors and font for the JOptionPane
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("OptionPane.messageForeground", Color.WHITE);
		UIManager.put("OptionPane.font", new Font("Tahoma", Font.BOLD, 20));

		if (scoreNumber > highscore) {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(scoreNumber + "");
			fileWriter.close();
			JOptionPane.showMessageDialog(null, "NEW  HIGH SCORE!\nYour final score is: " + scoreNumber, "Score",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Your final score is: " + scoreNumber + "\nHigh score is: " + highscore,
					"Score", JOptionPane.PLAIN_MESSAGE);
		}
		frame.dispose();
	}

	// Method for rescaling pictures bigger than the label
	public static ImageIcon getScaledIcon(ImageIcon icon) {
		Dimension imgSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = screen(2560);
		int bound_height = screen(1310);
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}
		return new ImageIcon(icon.getImage().getScaledInstance(new_width, new_height, Image.SCALE_DEFAULT));
	}
}
