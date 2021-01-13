import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUITest {
	// Created for 1440p
	public static void main(String[] args) {
		// Initializing main frame
		JFrame frame = new JFrame("Picture Guesser");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);

		// Label with countdown timer
		JLabel countdown = new JLabel();
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		countdown.setFont(new Font("Tahoma", Font.BOLD, 60));
		countdown.setForeground(Color.white);
		countdown.setBounds(2330, 1320, 230, 120);
		countdown.setText(format.format(0));
		frame.getContentPane().add(countdown);

		// Other elements at the bottom
		JTextField text = new JTextField();
		text.setBounds(1130, 1360, 300, 40);
		frame.getContentPane().add(text);

		JButton guess = new JButton("Guess");
		guess.setBounds(1500, 1360, 100, 40);
		guess.setFont(new Font("Tahoma", Font.BOLD, 20));
		frame.getContentPane().add(guess);

		JButton skip = new JButton("Skip");
		skip.setBounds(960, 1360, 100, 40);
		skip.setFont(new Font("Tahoma", Font.BOLD, 20));
		frame.getContentPane().add(skip);

		JLabel score = new JLabel("Score: 0");
		score.setBounds(100, 1360, 300, 40);
		score.setFont(new Font("Tahoma", Font.BOLD, 30));
		score.setForeground(Color.white);
		frame.getContentPane().add(score);

		JLabel streak = new JLabel("Streak: 0");
		streak.setBounds(590, 1360, 200, 40);
		streak.setFont(new Font("Tahoma", Font.BOLD, 30));
		streak.setForeground(Color.white);
		frame.getContentPane().add(streak);

		// Label displaying the pictures the player will be guessing
		JLabel picture = new JLabel(new ImageIcon(GUITest.class.getResource("/Resources/dipper.png")), JLabel.CENTER);
		picture.setBounds(0, 0, 2560, 1310);
		frame.getContentPane().add(picture);

		// Setting the frame visibility to true
		frame.setVisible(true);
	}

}
