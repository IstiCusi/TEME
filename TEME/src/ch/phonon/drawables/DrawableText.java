/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

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
import ch.phonon.TextOrientation;
import ch.phonon.temview.TEMView;

/**
 * The {@link DrawableText} class is used to place text items to the
 * {@link TEMView}. The standard orientation of the text item is, that it is
 * centered around the vector pointing to it.
 * 
 * @author phonon
 */
public class DrawableText extends AbstractDrawable {

	/**
	 * The orientation can be chosen by the items of the {@link TextOrientation}
	 * {@link Enum}. It is measured relative to the {@link LocalOrientation}.
	 */
	public TextOrientation textOrientation = TextOrientation.CENTER;

	private AttributedString aString;
	double width;
	double height;

	/**
	 * The text item represented by the {@link AttributedString} parameter
	 * <code>text</code> is prepared by this constructor to be drawn at the
	 * position identified by the <code>starpoint</code> and
	 * <code>localOrientation</code> parameter. By default the text orientation
	 * about this location is centered. This can be changed by adjusting the
	 * {@link #textOrientation} member.
	 * 
	 * @param starpoint
	 *            {@link StarPoint} location to the text item
	 * @param localOrientation
	 *            {@link LocalOrientation} of the text item
	 * @param text
	 */
	public DrawableText(StarPoint starpoint, LocalOrientation localOrientation,
			AttributedString text) {

		super(starpoint, localOrientation);
		this.aString = text;

		// TODO: This way to obtain the font size is not precise ...
		// Still I think, there should be a better way
		// http://stackoverflow.com/questions/4914145/getting-string-size-in-java-without-having-a-graphics-object-available

		Font font =
				(Font) (aString.getIterator().getAttribute(TextAttribute.FONT));
		FontRenderContext frc =
				new FontRenderContext(font.getTransform(), true, true);
		AttributedCharacterIterator aci = aString.getIterator();
		// TODO: Eclipse claims, that there is a hidden null pointer exception
		// happening ?!?!
		TextLayout textLayout = new TextLayout(aci, frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width = outLine.getBounds2D().getWidth();

	}

	@Override
			void draw(Graphics2D graphicsContext,
					AffineTransform locationTransform) {

		FontRenderContext frc = graphicsContext.getFontRenderContext();
		AttributedCharacterIterator aci = aString.getIterator();
		TextLayout textLayout = new TextLayout(aci, frc);
		Shape outLine = textLayout.getOutline(null);
		this.height = outLine.getBounds2D().getHeight();
		this.width = outLine.getBounds2D().getWidth();

		if (textOrientation == TextOrientation.CENTER) {
			locationTransform.translate(0, +this.height);
		} else if (textOrientation == TextOrientation.LEFT) {
			locationTransform.translate(this.width / 2, +this.height);
		}

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
		// A text never contains ...
		return false;
	}

}
