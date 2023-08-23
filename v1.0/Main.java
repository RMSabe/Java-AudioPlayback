import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

public class Main
{
	public static JFrame appWindow = new JFrame();
	public static JPanel screen = new JPanel();
	public static JLabel text1 = new JLabel();
	public static JLabel text2 = new JLabel();
	public static JTextField textBox = new JTextField();
	public static JButton button1 = new JButton();
	public static JButton button2 = new JButton();
	public static JButton button3 = new JButton();
	public static JSlider slider = new JSlider();
	public static ScreenLayout screenLayout = new ScreenLayout();
	public static RuntimeStatus runtimeStatus = RuntimeStatus.MAIN;

	public static ActionListener actionManager = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			String cmd = event.getActionCommand();

			if(cmd.equals("BUTTON_1"))
			{
				switch(runtimeStatus)
				{
					case MAIN:
						AudioManager.fetchFile(textBox.getText());
						break;

					case PLAYBACK:
						AudioManager.play();
						text1.setText("Playing...");
						break;

					case ERROR:
						paintMainScreen();
						break;
				}
			}
			else if(cmd.equals("BUTTON_2"))
			{
				if(runtimeStatus != RuntimeStatus.PLAYBACK) return;

				AudioManager.pause();
				text1.setText("Paused");
			}
			else if(cmd.equals("BUTTON_3"))
			{
				if(runtimeStatus != RuntimeStatus.PLAYBACK) return;

				AudioManager.stop();
				slider.setValue(0);
				paintMainScreen();
			}
		}
	};

	public static ChangeListener changeManager = new ChangeListener()
	{
		@Override
		public void stateChanged(ChangeEvent event)
		{
			AudioManager.dragCursor((long) slider.getValue());
		}
	};

	public static void paintMainScreen()
	{
		text1.setText("Enter WAV File Directory:");
		textBox.setText("");
		button1.setText("Proceed");
		button1.setLocation(300, 200);
		button1.setSize(120, 20);

		textBox.setVisible(true);
		button1.setVisible(true);

		text2.setVisible(false);
		button2.setVisible(false);
		button3.setVisible(false);
		slider.setVisible(false);

		runtimeStatus = RuntimeStatus.MAIN;
	}

	public static void paintPlaybackScreen()
	{
		text1.setText("Playback Started");
		button1.setText("PLAY");
		button1.setLocation(310, 150);
		button1.setSize(100, 20);
		button2.setText("PAUSE");
		button2.setLocation(310, 180);
		button2.setSize(100, 20);
		button3.setText("STOP");
		button3.setLocation(310, 210);
		button3.setSize(100, 20);

		button1.setVisible(true);
		button2.setVisible(true);
		button3.setVisible(true);
		slider.setVisible(true);

		text2.setVisible(false);
		textBox.setVisible(false);

		runtimeStatus = RuntimeStatus.PLAYBACK;
	}

	public static void paintErrorScreen(String errMsg)
	{
		text1.setText("Error Occurred");
		text2.setText(errMsg);
		button1.setText("Return");
		button1.setLocation(310, 200);
		button1.setSize(100, 20);

		text2.setVisible(true);
		button1.setVisible(true);

		textBox.setVisible(false);
		button2.setVisible(false);
		button3.setVisible(false);
		slider.setVisible(false);

		runtimeStatus = RuntimeStatus.ERROR;
	}

	public static void main(String[] args)
	{
		text1.setText("");
		text1.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		text1.setForeground(Color.BLACK);
		text1.setLayout(screenLayout);
		text1.setLocation(60, 50);
		text1.setSize(600, 35);
		text1.setHorizontalAlignment(JLabel.CENTER);
		text1.setVisible(true);

		text2.setText("");
		text2.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		text2.setForeground(Color.BLACK);
		text2.setLayout(screenLayout);
		text2.setLocation(60, 100);
		text2.setSize(600, 30);
		text2.setHorizontalAlignment(JLabel.LEFT);
		text2.setVisible(false);

		textBox.setText("");
		textBox.setBackground(Color.WHITE);
		textBox.setForeground(Color.BLACK);
		textBox.setLayout(screenLayout);
		textBox.setLocation(60, 100);
		textBox.setSize(600, 20);
		textBox.setVisible(false);

		button1.setText("");
		button1.setBackground(Color.WHITE);
		button1.setForeground(Color.BLACK);
		button1.addActionListener(actionManager);
		button1.setActionCommand("BUTTON_1");
		button1.setLayout(screenLayout);
		button1.setVisible(false);

		button2.setText("");
		button2.setBackground(Color.WHITE);
		button2.setForeground(Color.BLACK);
		button2.addActionListener(actionManager);
		button2.setActionCommand("BUTTON_2");
		button2.setLayout(screenLayout);
		button2.setVisible(false);

		button3.setText("");
		button3.setBackground(Color.WHITE);
		button3.setForeground(Color.BLACK);
		button3.addActionListener(actionManager);
		button3.setActionCommand("BUTTON_3");
		button3.setLayout(screenLayout);
		button3.setVisible(false);

		slider.setMinimum(0);
		slider.addChangeListener(changeManager);
		slider.setLayout(screenLayout);
		slider.setLocation(260, 240);
		slider.setSize(200, 20);
		slider.setVisible(false);

		screen.setSize(720, 360);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setLayout(screenLayout);
		screen.add(text1);
		screen.add(text2);
		screen.add(textBox);
		screen.add(button1);
		screen.add(button2);
		screen.add(button3);
		screen.add(slider);
		screen.setVisible(true);

		paintMainScreen();

		appWindow.setTitle("JRE Wave Audio Playback");
		appWindow.setSize(720, 360);
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.add(screen);
		appWindow.setVisible(true);
	}
}

