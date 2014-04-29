/**
 * 
 */
package ch.phonon;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;


public class DrawableText extends AbstractDrawable {

	private AttributedString aString;
	double width; 
	double height; 
	
	DrawableText(StarPoint starpoint, LocalOrientation localOrientation, AttributedString text) {
		
		super(starpoint, localOrientation);
		this.aString = text;
				
	}
	
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
		FontRenderContext frc = graphicsContext.getFontRenderContext();
		AttributedCharacterIterator aci = aString.getIterator();
		TextLayout textLayout = new TextLayout(aci,frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width= outLine.getBounds2D().getWidth();
		
		System.out.println("height:"+this.height+" width:"+this.width);
		
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
	
}
