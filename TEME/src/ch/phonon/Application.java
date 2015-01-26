/*******************************************************************************
 * Start the program from the command line e.g. like:
 * 
 *  /usr/lib/jvm/java-7-oracle/bin/java 
 * 	-classpath /home/phonon/Development/workspace37/TEME_CleanUp/bin 
 * 	ch.phonon.Application
 ******************************************************************************/


//TODO: Check for all drawables, if after dimension changes with setters
//		you change as well the internal primitive types!!!! Very important
// 		TODO. Do not forget to do this.

package ch.phonon;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** The <code>Application</code> class represents the main entrance point 
 * of the application. Because Swing is not tread-save, all the execution of
 * the Swing code is {@code invokedLater()}, what means, that the thread 
 * (Event Dispatch Thread) is running in an asynchronous manner. The
 * user does not know, when code execution is continued. 
 *    
 *  
 * @author phonon
 *
 */


public class Application {
	
	static {
//		System.setProperty("sun.java2d.trace", "timestamp,log,count");
//	    System.setProperty("sun.java2d.transaccel", "True");
//	    System.setProperty("sun.java2d.d3d", "True");
//	    System.setProperty("sun.java2d.ddforcevram", "True");
//	    System.setProperty("sun.java2d.opengl","True");
	}


/** 
 * The launching and entrance point of the application
 * @param args
 */
	public static void main(String[] args) {
		
		/** Load the standard sound volume from the properties file */
		Sound.setStandardVolume();
		
		/** Load the standard sound streams as defined in the properties file */
		Sound.setStandardStreams(ResourceLoader.getStandardInputStreams());
		
		/** Pay the welcome sound in a separate thread */
		new Thread(new Sound(SoundType.WELCOME)).start();

		/** Define the Look and File - TODO:  Define per system */
		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Exception: standard look and feel not found");
			e.printStackTrace();
		}
		
		/** Invoke the Swing application on a separate thread */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			
				new MainFrame();
			}
		});		
		
		/** Always say good bye */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	System.out.println(ResourceLoader.getResource("Window_SayBye"));
		    }
		}));
		
		
	}

}
