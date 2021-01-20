import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
		timer.start();
		frame.getContentPane().add(countdown);

		// Other elements at the bottom
		JTextField text = new JTextField();
		text.setBounds(screen(1130), screen(1360), screen(300), screen(40));
		text.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		text.setHorizontalAlignment(JTextField.CENTER);
		frame.getContentPane().add(text);

		JLabel score = new JLabel("Score: 0");
		score.setBounds(screen(100), screen(1360), screen(300), screen(40));
		score.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		score.setForeground(Color.white);
		frame.getContentPane().add(score);

		JLabel streak = new JLabel("Streak: 0");
		streak.setBounds(screen(590), screen(1360), screen(200), screen(40));
		streak.setFont(new Font("Tahoma", Font.BOLD, screen(30)));
		streak.setForeground(Color.white);
		frame.getContentPane().add(streak);

		// Label displaying the pictures the player will be guessing
		JLabel picture = new JLabel(
				getScaledIcon(new ImageIcon(GUITest.class.getResource("/Resources/" + answers[order[count]] + ".jpg"))),
				JLabel.CENTER);
		picture.setBounds(0, 0, screen(2560), screen(1310));
		frame.getContentPane().add(picture);

		// Here the player submits his guess and proceeds if correct
		JButton guess = new JButton("Guess");
		guess.setBounds(screen(1500), screen(1360), screen(110), screen(40));
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
		skip.setBounds(screen(960), screen(1360), screen(100), screen(40));
		skip.setFont(new Font("Tahoma", Font.BOLD, screen(20)));
		skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				streakNumber = 0;
				multiplier = 1;
				timer.subtract10sec();
				count++;
				picture.setIcon(getScaledIcon(
						new ImageIcon(GUITest.class.getResource("/Resources/" + answers[order[count]] + ".jpg"))));
				streak.setText("Streak: " + streakNumber);
			}
		});
		frame.getContentPane().add(skip);

		// Setting the frame visibility to true
		frame.setVisible(true);
	}

	// Method for resizing this app for different screens
	static int screen(int original) {
		int ready = (int) (original * ratio);
		return ready;
	}

	// When the countdown is over - show score and close frame
	static void finalScore() {
		JOptionPane.showMessageDialog(null, "Your final score is: " + scoreNumber, "Score", JOptionPane.PLAIN_MESSAGE);
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
