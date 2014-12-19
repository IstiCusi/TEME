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

/** 
 * The {@link Sound} class has several static helper functions that allow
 * to set the default sounds of the application. The default sounds are
 * associated with the {@link SoundType} enum and stored internally in a 
 * EnumMap. An active SoundType can be chosen without regeneration of a 
 * new instance and either played with the run or by start when providing
 * this Runnable to a Thread.
 * 
 * @author phonon
 *
 */
public class Sound implements Runnable {
	
	//TODO Check if it is necessary to have a synchronized approach
	
	private Position curPosition;
    
    private final int EXTERNAL_BUFFER_SIZE = 524288;
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };

	
    static private EnumMap<SoundType, AudioInputStream> standardInputStreams;
	
	public Sound(SoundType type) {
		this.setActiveAudioStream(type);
	}
	
	public Sound(AudioInputStream stream) {
		this.setActiveAudioStream(stream);
	}
    
  
	static public void setStandardStreams (EnumMap<SoundType, AudioInputStream> standardInputStreams) {
		Sound.standardInputStreams = standardInputStreams;
	}

	static public void setStandardVolume( ) {
		volume = Float.parseFloat(ResourceLoader.getResource("Volume"));
	}
	
	private AudioInputStream activeAudioInputStream;

	private static float volume=1.0F;
    
	public void setActiveAudioStream (SoundType type) {
		this.activeAudioInputStream=Sound.standardInputStreams.get(type);
	}

	public void setActiveAudioStream (AudioInputStream stream) {
		this.activeAudioInputStream=stream;
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
        	FloatControl gainControl =
        	           (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        	float range = gainControl.getMaximum() - gainControl.getMinimum();
        	float gain = (range * volume) + gainControl.getMinimum();
        	gainControl.setValue(gain);
        }
        
        sourceDataLine.start();
        
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        /** s p 231 Killergame programming: Play chunks **/
        
        int numRead = 0;
        try {
			while ((numRead = activeAudioInputStream.read(abData, 0, abData.length)) >=0) {
				int offset = 0;
				while (offset < numRead) {
					offset += sourceDataLine.write(abData,offset,numRead-offset);
				}
			}
		} catch (IOException e1) {
			System.out.println("Sound: Problem to read or write to lines");
			e1.printStackTrace();
		} finally {
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();
		}
                 
    } 



}
