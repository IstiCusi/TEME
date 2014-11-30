package ch.phonon;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class ResourceLoader {

	static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage (String fileName ) {
		return Toolkit.getDefaultToolkit().getImage(
					rl.getClass().getResource("pics/"+fileName)
		);
	}
	
	public static BufferedImage getBufferedImage (String fileName) throws IOException {
		URL url = ResourceLoader.getUrl("pics/"+fileName);
		return ImageIO.read(url);
	}

	public static AudioInputStream getAudioInputStream(String fileName ) {
		
		URL url = rl.getClass().getResource("sounds/"+fileName);
		
		AudioInputStream audioInputStream = null;
		try {
			 audioInputStream = AudioSystem.getAudioInputStream(url);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AudioInputStream resetAbleAudioStream;
		resetAbleAudioStream = null;
		try {
			resetAbleAudioStream = createReusableAudioInputStream(audioInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resetAbleAudioStream;
	
	}
	
	public static AudioInputStream getAudioStreamFromResource(String resource) {
		String fileName = ResourceLoader.getResource(resource);
		AudioInputStream audioStream = ResourceLoader.getAudioInputStream(fileName);
		AudioInputStream resetAbleAudioStream;
		resetAbleAudioStream = null;
		try {
			resetAbleAudioStream = createReusableAudioInputStream(audioStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resetAbleAudioStream;
	}

	
	
	
	static ResourceBundle mainResourceBundle = ResourceBundle
			.getBundle("ch.phonon.config.resourceBundle");

	/**
	 * For the whole {@link Application} a general properties file is arranged. It
	 * can be found at path ch.phonon.config.resourceBundleProperties. Properties 
	 * stored in this file adjust various thinks like menu names, view colors etc.
	 * Any property is keyed by baseName. The value of the property is returned
	 * as string. 
	 * @param baseName
	 * @return a string of the property value	
	 * 
	 */
	
	public static String getResource(String baseName) {
		return mainResourceBundle.getString(baseName);
	}

	public static EnumMap<SoundType, AudioInputStream> getStandardInputStreams() {
		
		EnumMap<SoundType, AudioInputStream> standardAudioStreams 
		= new EnumMap<SoundType, AudioInputStream>(SoundType.class);
		
		for(SoundType value: SoundType.values()) {
				AudioInputStream stream = ResourceLoader.getAudioStreamFromResource(value.toString());
				standardAudioStreams.put(value, stream);
				
		}
		return standardAudioStreams;
		
	}
	
	private static AudioInputStream createReusableAudioInputStream(AudioInputStream ais) 
	        throws IOException, UnsupportedAudioFileException
	    {
	    
	        try
	        {
	            
	            byte[] buffer = new byte[1024 * 32];
	            int read = 0;
	            ByteArrayOutputStream baos = 
	                new ByteArrayOutputStream(buffer.length);
	            while ((read = ais.read(buffer, 0, buffer.length)) != -1)
	            {
	                baos.write(buffer, 0, read);
	            }
	            AudioInputStream reusableAis = 
	                new AudioInputStream(
	                        new ByteArrayInputStream(baos.toByteArray()),
	                        ais.getFormat(),
	                        AudioSystem.NOT_SPECIFIED);
	            return reusableAis;
	        }
	        finally
	        {
	            if (ais != null)
	            {
	                ais.close();
	            }
	        }
	    }

	/**
	 * This convinience function is used to obtain resource
	 * paths directly in the jar file of the application.
	 * @param path
	 * @return
	 */
		
		public static URL getUrl(String path) {
			URL url = Application.class.getResource(path);
			return url;
		}
	

}
