import java.io.*;
import javax.sound.sampled.*;

public class AudioManager
{
	private static PlaybackStatus playbackStatus = PlaybackStatus.STOP;
	private static long dataSize = 0l;
	private static long dataPosition = 0l;
	private static String filePath = "";
	private static AudioInputStream audioStream = null;
	private static Clip audioClip = null;

	public static void fetchFile(String path)
	{
		filePath = path;
		
		try
		{
			audioStream = AudioSystem.getAudioInputStream(new File(filePath));
			audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(IOException exception)
		{
			Main.paintErrorScreen("Could not open file");
			return;
		}
		catch(LineUnavailableException exception)
		{
			Main.paintErrorScreen("Audio Line is not available");
			return;
		}
		catch(UnsupportedAudioFileException exception)
		{
			Main.paintErrorScreen("File format is not supported");
			return;
		}

		audioClip.setMicrosecondPosition(0l);
		dataSize = audioClip.getMicrosecondLength();
		dataPosition = audioClip.getMicrosecondPosition();
		Main.slider.setMaximum((int) dataSize);
		Main.slider.setValue((int) dataPosition);
		Main.paintPlaybackScreen();
		play();
	}

	public static void play()
	{
		if(playbackStatus == PlaybackStatus.PLAY) return;

		audioClip.setMicrosecondPosition(dataPosition);
		audioClip.start();
		playbackStatus = PlaybackStatus.PLAY;
	}

	public static void pause()
	{
		if(playbackStatus == PlaybackStatus.PAUSE) return;

		dataPosition = audioClip.getMicrosecondPosition();
		audioClip.stop();
		playbackStatus = PlaybackStatus.PAUSE;
	}

	public static void stop()
	{
		audioClip.stop();
		audioClip.close();

		playbackStatus = PlaybackStatus.STOP;
	}

	public static void dragCursor(long position)
	{
		dataPosition = position;

		if(playbackStatus == PlaybackStatus.PLAY)
		{
			audioClip.stop();
			audioClip.setMicrosecondPosition(dataPosition);
			audioClip.start();
		}
	}
}

