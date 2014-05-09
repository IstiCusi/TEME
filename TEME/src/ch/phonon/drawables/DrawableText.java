/**
 * 
 */
package ch.phonon.drawables;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;


public class DrawableText extends AbstractDrawable {

	private AttributedString aString;
	double width; 
	double height; 
	
	public DrawableText(StarPoint starpoint, LocalOrientation localOrientation, AttributedString text) {
		
		super(starpoint, localOrientation);
		this.aString = text;
		
		//TODO: 	This way to obtain the font size is not precise ...
		//			Still I think, there should be a better way
		// http://stackoverflow.com/questions/4914145/getting-string-size-in-java-without-having-a-graphics-object-available
		
		Font font = (Font)(aString.getIterator().getAttribute(TextAttribute.FONT));
		FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
		AttributedCharacterIterator aci = aString.getIterator();
		TextLayout textLayout = new TextLayout(aci,frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width= outLine.getBounds2D().getWidth();
		
		
	}
	
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
		FontRenderContext frc = graphicsContext.getFontRenderContext();
		AttributedCharacterIterator aci = aString.getIterator();
		TextLayout textLayout = new TextLayout(aci,frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width= outLine.getBounds2D().getWidth();
		
		locationTransform.translate(0, +this.height);
		AffineTransform helper = graphicsContext.getTransform();
		graphicsContext.transform(locationTransform);
		graphicsContext.drawString(aString.getIterator(), 0, 0);
		graphicsContext.setTransform(helper);
		
		
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
