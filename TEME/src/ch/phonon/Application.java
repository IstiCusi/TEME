package ch.phonon;

import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// Next tasks
// TODO:
// IMPORTANT: Complete the DrawableCompositeImplementation
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
 */
	public static String getResource(String baseName) {
		return mainResourceBundle.getString(baseName);
	}

/** 
 * The launching and entrance point of the application
 * 	
 * @param args
 */
	public static void main(String[] args) {
		

		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
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
