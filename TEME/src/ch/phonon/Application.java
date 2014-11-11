package ch.phonon;

import java.net.URL;
import ch.phonon.SoundType;
import ch.phonon.Sound;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Next tasks

//TODO: Try to understand the Sound code. It is very interesting and hard
// to find ... maybe we can even simplify/beautify it. 

//DONE The playStdSound below only work for some reason
// in separate threads for several times. Playing 
// a second time only works in the thead or if always a new
// Sound instance is generated. Why ?
//this.sound.playStdSound(SoundType.ERROR);
// In general the idea was to load all sounds when the TEMView is
// initalized and not always ... So how ?
// Solution found Clip is not perfectly work

// TODO: the Tabulator switch to TEMAllieds gives back null object...
// I would like to catch the exception and treat it correspondingly

// TODO: Go on with TEMTableModel.java
// the container and the relation to the TEMView needs to be implemented.
// How to switch between different loaded TEM pictures

// TODO:
// Write container, that keeps track about TEMPicture (Drawable) and it's 
// polygons and it's points added 
// TODO:
// Introduce different variants of point drawable composites 
// Introduce possibility gto show polygon number beside the point

//TODO: How to decorate Drawables to obtain color, thickness etc.
// The actual model is to static

//TODO Add an InvariantTranslation property to the Drawables
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
 * This convinience function is used to obtain resource
 * paths directly in the jar file of the application.
 * @param path
 * @return
 */
	public static URL getUrl(String path) {
		URL url = Application.class.getResource(path);
		return url;
	}

/** 
 * The launching and entrance point of the application
 * @param args
 */
	public static void main(String[] args) {
		
		Sound.setStandardStreams(ResourceLoader.getStandardInputStreams());
		
		new Thread(new Sound(SoundType.WELCOME)).start();

		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Exception: standard look and feel not found");
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			
				new MainFrame();
			}
		});		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	System.out.println(Application.getResource("Window_SayBye"));
		    }
		}));
		
		
		
	}

}
