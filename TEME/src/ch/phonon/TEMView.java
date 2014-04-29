/**
 * 
 */
package ch.phonon;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * The {@link TEMView} is a {@link JPanel}, that is used to show various
 * {@link Drawable} objects. 
 * @author phonon
 *
 */
public class TEMView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	TEMAdapter adapter;
	private AffineTransform initial;
	private AffineTransform viewPortTransform;
	private ArrayList<Drawable> drawableList; // contains all Drawables 
	
	private int id; 
	private HashMap<Integer,Point2D> points; 
	
	// temViewState contains global rotation/zoom/pan state of all Drawables
	
	private TEMViewState temViewState;
	
	TEMView  () { 
	
		// Initialize state of the TEMView
		
		configureEnvironment();	// configure properties of TEMView (Color...)
		
		this.temViewState = new TEMViewState();
		this.adapter = new TEMAdapter(this);
		this.points 	= new HashMap<Integer, Point2D>();
		this.id 		= 0;		
				
		StarPoint initialStarpoint = null;
		LocalOrientation initialLocalOrientation = null;
				
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 0,1.0);
		
		// Populate Drawables in ArrayList 
		
		this.drawableList = new ArrayList<Drawable>();
		
		
		initialStarpoint = new StarPoint(0,0);
		DrawablePicture temPicture = new DrawablePicture(initialStarpoint, 
						initialLocalOrientation, "./pics/CoordinateChecker.png");
		drawableList.add(temPicture);
		
		
		initialStarpoint = new StarPoint(0,0);
		DrawableBox aBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 800, 600);
		drawableList.add(aBox);
		
		
		initialStarpoint = new StarPoint(400,300);
		DrawableBox bBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 400, 300);
		drawableList.add(bBox);

		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 90,1.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox cBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 400, 300);
		drawableList.add(cBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,0.5);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox dBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		dBox.setColor(new Color(0,255,0));
		drawableList.add(dBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,1.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox eBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		eBox.setColor(new Color(0,255,0));
		drawableList.add(eBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,2.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox fBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		fBox.setColor(new Color(50,0,120));
		drawableList.add(fBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 90,2.0);
		initialStarpoint = new StarPoint(400, 300);
		Font font = new Font("Helvetica",Font.PLAIN,30);
		AttributedString aString = new AttributedString("TEME");
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);

		DrawableText text = new DrawableText(initialStarpoint, 
											  initialLocalOrientation, aString);
		drawableList.add(text);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 0,2.0);
		initialStarpoint = new StarPoint(0,0);
		DrawableBox gBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 50, 50);
		gBox.setColor(new Color(50,0,120));
		drawableList.add(gBox);
		
		
		setVisible(true);	
		repaint(500);
		
		// Add Listeners to View
		
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);		
		this.addKeyListener(this.adapter);
		
		//requestFocusInWindow(true);
		
		
		
		//centerAll();
	}
	
	public void centerAll() {
		System.out.println(">>>>>>>>>>>centerAll<<<<<<<<<<<<");
		
		System.out.println(this.getWidth());
		System.out.println(this.getHeight());
		System.out.println(temViewState.x);
		System.out.println(temViewState.y);
		
		System.out.println(">>>>>>>>>>>---------<<<<<<<<<<<<");
		
		temViewState.cWidth  =  this.getWidth();
		temViewState.cHeight =  this.getHeight();
		temViewState.x = temViewState.cWidth/2;
		temViewState.y = temViewState.cHeight/2;
		temViewState.scaling = 1;
		temViewState.rotation = 0;
		this.repaint();
	}

	public TEMViewState getTEMViewState() {
		return temViewState;
	}

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent( g );
		Graphics2D g2D = (Graphics2D) g;
		this.initial = g2D.getTransform();
		
		
		for (Drawable drawable : drawableList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort 
						(initial, this.temViewState);
			drawable.paint(g2D, this.viewPortTransform);
		    
		}
		
		
		//TODO: This repaint() i need just because of the DrawableText item.
		// I have absolutly no clue, why.... it solves the problem. But not
		// rigorously ... I have the feeling, that it has something to do with
		// the width and height not known in DrawableText constructor.
		// Therefore I take it out, before I do not find an explanation.
		// repaint();
		
		

	}

	private void configureEnvironment() {
		
		setOpaque(true);
		setFocusable(true);
		setBorder(BorderFactory.createLineBorder(Color.white));		
		setBackground( new Color (
				Integer.parseInt(
						Application.getResource("TEMView_Color").substring(2),16)
				)
		);

		try {
			BufferedImage curBufferedImage = ImageIO.read(new File("pics/Cross.gif"));
			Cursor bufferedCursor = Toolkit.getDefaultToolkit().createCustomCursor(curBufferedImage, new Point(32,32), "Cross");
			setCursor(bufferedCursor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
	
	public void setTEMViewState(TEMViewState temViewState) {
		this.temViewState = temViewState;
	}
	
	public void addStatusBar (TEMStatusBar statusBar) {
		this.addPropertyChangeListener("coordinateChange", (PropertyChangeListener) statusBar);
		this.addPropertyChangeListener("addPoint", (PropertyChangeListener) statusBar);
	}

	/**
	 * 
	 */
	public void fireCoordinatePropertyChange(Point2D point) {
		Point2D pt = new Point2D.Double();
		 try {
			this.viewPortTransform.inverseTransform(point, pt);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.firePropertyChange("coordinateChange", null, pt);
		
	}
	
public void addPoint ( Point2D point) {
		
		this.id++;
		Point2D pt = new Point2D.Double();
		 try {
			this.viewPortTransform.inverseTransform(point, pt);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		points.put(id, pt);
		System.out.println("OriginalPictureCorr.: X:"+pt.getX()+"  Y:"+pt.getY());
		
		//TODO: Points and other TEMView related items need to keep size independent of temView scale
		
		LocalOrientation initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 45,1.0);
		StarPoint initialStarpoint = new StarPoint(pt.getX(),pt.getY());
		DrawableBox pPoint = new DrawableBox(initialStarpoint, initialLocalOrientation, 10, 10);
		pPoint.setColor(new Color(0,255,255));
		pPoint.setInvariantRotation(true);
		pPoint.setInvariantScaling(true);
		this.drawableList.add(pPoint);
		
		this.firePropertyChange("addPoint", null, pt);
		
		repaint();
		
		

	}

}
