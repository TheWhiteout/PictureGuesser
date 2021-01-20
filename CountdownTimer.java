
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.Timer;

public class CountdownTimer implements ActionListener {
	private Timer timer;
	private final static long duration = 30000;
	private long bonusTime = 0;
	private long startTime = -1;
	private long clockTime = 0;
	JLabel countdown = new JLabel();
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss");

	// Constructor for different screens
	CountdownTimer(double ratio) {
		timer = new Timer(10, this);
		countdown.setFont(new Font("Tahoma", Font.BOLD, GUITest.screen(60)));
		countdown.setForeground(Color.white);
		countdown.setBounds(GUITest.screen(2330), GUITest.screen(1320), GUITest.screen(230), GUITest.screen(120));
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void add3sec() {
		bonusTime += 3000;
	}

	public void subtract10sec() {
		bonusTime -= 10000;
		if (clockTime >= duration + bonusTime) {
			clockTime = duration + bonusTime;
			countdown.setText(format.format(duration + bonusTime - clockTime));
			stop();
			GUITest.finalScore();
		}
	}

	// While the timer is running this method will be executing
	public void actionPerformed(ActionEvent e) {
		if (startTime < 0) {
			startTime = System.currentTimeMillis();
		}
		long now = System.currentTimeMillis();
		clockTime = now - startTime;
		if (clockTime >= duration + bonusTime) {
			clockTime = duration + bonusTime;
			stop();
			GUITest.finalScore();
		}
		countdown.setText(format.format(duration + bonusTime - clockTime));
	}
}
