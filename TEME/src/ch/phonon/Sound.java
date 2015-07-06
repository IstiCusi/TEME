/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import java.io.IOException;
import java.util.EnumMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

//TODO: Write unit test, that checks the construction of objects, when
// when the static setStandardStreams(EnumMap) was not used before.
// This needs to be correct with the help of this unit test.
// Define maybe a own Exception and handle this situation accordingly.

//TODO: How could this interact with a SwingWorker class 
// see e.g. here https://www.youtube.com/watch?v=X5Q-Mecu_64
// and here: http://www.javaworld.com/article/2073477/swing-gui-programming/customize-swingworker-to-improve-swing-guis.html
// http://openbook.rheinwerk-verlag.de/java7/1507_09_025.html#dodtp8146cb9d-d1a1-4556-89ce-7a8f8eeff633
// In our case, the thread does not need to update any guy element, but
// would be interesting to see how this would need to be reformulated in case
// it would. I think the momentary solution does not break anything because
// there is no backpropagation from the Sound Runnable. 

/**
 * The {@link Sound} class has several static helper functions that allow to set
 * the default sounds of the application. The default sounds are associated with
 * the {@link SoundType} enum and stored internally in a EnumMap. An active
 * SoundType can be chosen without regeneration of a new instance and either
 * played with the run or by start when providing this Runnable to a Thread.
 * 
 * @author phonon
 * 
 */
public class Sound implements Runnable {

	// TODO Check if it is necessary to have a synchronized approach

	private Position curPosition;

	private final int EXTERNAL_BUFFER_SIZE = 524288;

	enum Position {
		LEFT, RIGHT, NORMAL
	};

	static private EnumMap<SoundType, AudioInputStream> standardInputStreams;

	/**
	 * This constructor allows to choose the active {@link SoundType} as first
	 * pre-defined by the static {@link #setStandardStreams(EnumMap)} function.
	 * The produced {@link Runnable} can than be fed into a Thread. When
	 * starting the Thread ({@link Thread#start()}), the associated sound of the
	 * chosen {@link SoundType} is played back on the system sound output line.
	 * Alternatively, one can start the {@link Runnable#run()} method in case
	 * you would like to run the sound in active thread.
	 * 
	 * 
	 * @param type
	 *            defines the chosen standard sound as described in
	 *            {@link SoundType}
	 * 
	 * @see Sound#Sound(AudioInputStream)
	 * 
	 */
	public Sound(SoundType type) {
		this.setActiveAudioStream(type);
	}

	/**
	 * This constructor allows to to choose an {@link AudioInputStream} (e.g.
	 * obtained by the as {link {@link ResourceLoader} class) source to be
	 * played back. The produced {@link Runnable} can than be fed into a Thread.
	 * When starting the Thread ({@link Thread#start()}), the associated sound
	 * of the chosen {@link SoundType} is played back on the system sound output
	 * line. Alternatively, one can start the {@link Runnable#run()} method in
	 * case you would like to run the sound in active thread.
	 * 
	 * 
	 * @param stream
	 *            AudioInputStream to be used
	 * 
	 * 
	 * @see Sound#Sound(SoundType)
	 * 
	 */
	public Sound(AudioInputStream stream) {
		this.setActiveAudioStream(stream);
	}

	/**
	 * Sets the standard {@link EnumMap} of {@link AudioInputStream}s that have
	 * to be present at runtime. The resources that need to be loaded into the
	 * map are explained in the {@link SoundType} class.
	 * 
	 * @param standardInputStreams
	 */
	static public void setStandardStreams(
			EnumMap<SoundType, AudioInputStream> standardInputStreams) {
		Sound.standardInputStreams = standardInputStreams;
	}

	/**
	 * Sets the standard volume as found in the resource bundle file config/
	 * resourceBundle.
	 */
	static public void setStandardVolume() {
		volume = Float.parseFloat(ResourceLoader.getResource("Volume"));
	}

	private AudioInputStream activeAudioInputStream;

	private static float volume = 1.0F;

	/**
	 * Set the active audio stream based on the preloaded standard sounds (
	 * {@link #setStandardStreams(EnumMap)} that can be chosen by the related
	 * {@link SoundType}.
	 * 
	 * @param type
	 */
	public void setActiveAudioStream(SoundType type) {
		this.activeAudioInputStream = Sound.standardInputStreams.get(type);
	}

	/**
	 * Set the active audio stream based on a a given preloaded 
	 * {@link AudioInputStream}. The {@link AudioInputStream} can be loaded
	 * by the auxillary {@link ResourceLoader} class. 
	 * @param stream
	 */
	public void setActiveAudioStream(AudioInputStream stream) {
		this.activeAudioInputStream = stream;
	}

	@Override
	public void run() {

		try {
			this.activeAudioInputStream.reset();
		} catch (IOException e1) {
			System.out.println("Sound: Problem to reset activeAudioStream");
			e1.printStackTrace();
		}
		AudioFormat format = activeAudioInputStream.getFormat();

		SourceDataLine sourceDataLine = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if (sourceDataLine.isControlSupported(FloatControl.Type.PAN)) {
			FloatControl pan = (FloatControl) sourceDataLine
					.getControl(FloatControl.Type.PAN);
			if (curPosition == Position.RIGHT)
				pan.setValue(1.0f);
			else if (curPosition == Position.LEFT)
				pan.setValue(-1.0f);
		}

		/** Set the standard volume when possible */

		if (sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl gainControl = (FloatControl) sourceDataLine
					.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}

		sourceDataLine.start();

		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

		/** s p 231 Killergame programming: Play chunks **/

		int numRead = 0;
		try {
			while ((numRead = activeAudioInputStream.read(abData, 0,
					abData.length)) >= 0) {
				int offset = 0;
				while (offset < numRead) {
					offset += sourceDataLine.write(abData, offset, numRead
							- offset);
				}
			}
		} catch (IOException e1) {
			System.out
					.println("Sound IOException: Problem to read or write to lines");
			e1.printStackTrace();
		} finally {
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
		}

	}

}
