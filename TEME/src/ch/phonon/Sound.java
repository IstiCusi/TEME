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


public class Sound implements Runnable {
	
	//TODO Check if it is necessary to have a synchronized approach
	
	private Position curPosition;
    
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };

	
    static public  EnumMap<SoundType, AudioInputStream> standardClips;

    public Sound() {
    	
	}
	
	public Sound(SoundType type) {
		this.setActiveAudioStream(type);
	}
	
	public Sound(AudioInputStream stream) {
		this.setActiveAudioStream(stream);
	}
    
  
	static public void setStandardStreams (EnumMap<SoundType, AudioInputStream> standardClips) {
		Sound.standardClips = standardClips;
	}

	private AudioInputStream activeAudioInputStream;
    
	public void setActiveAudioStream (SoundType type) {
		this.activeAudioInputStream=Sound.standardClips.get(type);
	}

	public void setActiveAudioStream (AudioInputStream stream) {
		this.activeAudioInputStream=stream;
	}

	@Override
	public void run() {

		try {
				this.activeAudioInputStream.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        AudioFormat format = activeAudioInputStream.getFormat();
       
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { 
            FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = activeAudioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally {
        	System.out.println("Huhu");
            auline.drain();
            auline.close();
        } 
        
        //auline.stop();
 
    } 



}
