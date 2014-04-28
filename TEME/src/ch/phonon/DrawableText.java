/**
 * 
 */
package ch.phonon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;


public class DrawableText extends AbstractDrawable {

	private String text;
	private Font   font;
	private FontRenderContext frc = null;
	private TextLayout textLayout = null;
	
	// TODO: Take out the standard values after fix of DrawableText
	double width = 104; 
	double height=22; 

	
	DrawableText(StarPoint starpoint, LocalOrientation localOrientation, String text) {
		
		super(starpoint, localOrientation);

		this.text = text;
		this.font = new Font("Helvetica",Font.PLAIN,30);
		this.width=104;
		this.height=22;
		
//		this.width=image.getWidth();
//		this.height=image.getHeight();
				
	}
	
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
		// ---------------------------------------------------------------------
		
//		frc = graphicsContext.getFontRenderContext();
//		textLayout = new TextLayout(text,font,frc);
//		
//		Shape outLine0 = textLayout.getOutline(null);
//		
//		this.width=outLine0.getBounds2D().getWidth();
//		this.height=outLine0.getBounds2D().getHeight();
//		
//		locationTransform.translate(0, +this.height);
//		Shape outLine = textLayout.getOutline(locationTransform);
//				
//		graphicsContext.setColor(Color.YELLOW);
//		graphicsContext.draw(outLine);
		
		// ----------------------------------------------------------------------

//		frc = graphicsContext.getFontRenderContext();
//		AttributedString aString = new AttributedString(text);
//		aString.addAttribute(TextAttribute.FONT, font);
//		aString.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);
//		AttributedCharacterIterator aci = aString.getIterator();
//		TextLayout textLayout = new TextLayout(aci,frc);
//		Shape outLine = textLayout.getOutline(null);
//		this.height = outLine.getBounds2D().getHeight();
//		this.width= outLine.getBounds2D().getWidth();
//		
//		locationTransform.translate(0, +this.height);
//		AffineTransform helper = graphicsContext.getTransform();
//		graphicsContext.setTransform(locationTransform);
//		graphicsContext.drawString(aString.getIterator(), 0, 0);
//		graphicsContext.setTransform(helper);
//		
		// ---------------------------------------------------------------------
		
		frc = graphicsContext.getFontRenderContext();
		AttributedString aString = new AttributedString(text);
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);
		AttributedCharacterIterator aci = aString.getIterator();
		TextLayout textLayout = new TextLayout(aci,frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width= outLine.getBounds2D().getWidth();
		
		System.out.println("height:"+this.height+" width:"+this.width);
		
		locationTransform.translate(0, +this.height);
		AffineTransform helper = graphicsContext.getTransform();
		graphicsContext.setTransform(locationTransform);
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
