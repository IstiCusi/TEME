import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.text.AttributedString;

import ch.phonon.DrawableBox;
import ch.phonon.DrawableLine;
import ch.phonon.DrawablePicture;
import ch.phonon.DrawableText;
import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

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
		initialStarpoint = new StarPoint(0, 0);
		Font font = new Font("Helvetica",Font.PLAIN,30);
		AttributedString aString = new AttributedString("TEMEVIEW");
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);

		DrawableText text = new DrawableText(initialStarpoint, 
											  initialLocalOrientation, aString);
		drawableList.add(text);
		
		
		Point2D.Double p1 = new Point2D.Double(0, 0);
		Point2D.Double p2 = new Point2D.Double(0, 800);
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 45,1.0);
		initialStarpoint = new StarPoint(0,0);
		DrawableLine line= new DrawableLine(initialStarpoint, 
											  initialLocalOrientation, p1, p2);
		line.setColor(new Color(0,0,255));
		drawableList.add(line);
