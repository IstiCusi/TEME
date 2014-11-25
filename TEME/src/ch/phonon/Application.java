package ch.phonon;

import ch.phonon.SoundType;
import ch.phonon.Sound;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Next tasks

// TODO: Try to understand the Sound code. It is very interesting and hard
// to find ... maybe we can even simplify/beautify it. 

//TODO: How to decorate Drawables to obtain color, thickness etc.
//The actual model is to static

// TODO: the Tabulator switch to TEMAllieds gives back null object...
// I would like to catch the exception and treat it correspondingly

// TODO:
// Introduce possibility to show polygon number beside the point

// TODO:
// Use anti-aliasing for fonts 


// TODO Add an InvariantTranslation property to the Drawables
// Important, if we want later to add an information line similar to 
// Lightroom as last layer of the TEMView, that shows information about
// the actual view (name of tem picture, resolution, project name etc)

// IMPORTANT: Complete the DrawableCompositeImplementation
// Maybe we can simplify the Drawable interface and go more into composition

// Why does another recenterAll() lead to a small shift. Has something
// todo with a changed width of the contentPane... I think, Anyway I do
// not like the fact, that I need to apply the centerAll after the total
// TEM app is done. 

// TODO: Documentation

// TODO: probably much better way:
// http://www.oodesign.com/chain-of-responsibility-pattern.html
// http://www.thecentric.com/wiki/index.php/HMVC_Tutorial
// http://www.javaworld.com/article/2076128/design-patterns/hmvc--the-layered-pattern-for-developing-strong-client-tiers.html
// http://shulgadim.blogspot.ch/2012/01/model-view-controller-mvc-pattern_13.html
//http://stackoverflow.com/questions/1673841/examples-of-gof-design-patterns


//TODO: Check the following 
//http://stackoverflow.com/questions/14536503/how-do-i-zoom-on-swing-components-using-affinetransform
//http://docs.oracle.com/javase/tutorial/uiswing/misc/jlayer.html
//Could any item in the TEMView be a JComponent ?

// Should we use a SecurityManager ? Not necessary, but who knows


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
// 		System.setProperty("sun.java2d.trace", "timestamp,log,count");
//	    System.setProperty("sun.java2d.transaccel", "True");
//	    System.setProperty("sun.java2d.d3d", "True");
//	    System.setProperty("sun.java2d.ddforcevram", "True");
//	    System.setProperty("sun.java2d.opengl","True");
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

/** 
 * The launching and entrance point of the application
 * @param args
 */
	public static void main(String[] args) {
		
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
		    	System.out.println(Application.getResource("Window_SayBye"));
		    }
		}));
		
		
	}

}
