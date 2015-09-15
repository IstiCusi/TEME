/*******************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *******************************************************************************/

package ch.phonon.drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/**
 * The <code>DrawableBox</code> class implements the {@link AbstractDrawable}.
 * and represents a rectangular shape.
 * 
 * @author phonon
 *
 */

public class DrawableBox extends AbstractDrawable
		implements Decoratable<DrawableShapeDecorations> {

	// ------------------------------- State------------------------------------

	double x = 0, y = 0;
	double width = 10, height = 10;
	private Rectangle2D box =
			new Rectangle2D.Double(this.x, this.y, width, height);

	private DrawableShapeDecorations decorations =
			DrawableShapeDecorations.STANDARD_DECORATIONS;

	AffineTransform locationTransform;

	// ----------------------------Constructors --------------------------------

	/**
	 * Constructs a standard drawable box at the origin in standard
	 * {@link LocalOrientation}. The box constructed is 10x10 pixel of size. The
	 * {@link Decorations} are the given by the
	 * {@link DrawableShapeDecorations#STANDARD_DECORATIONS}
	 */
	public DrawableBox() {

		super(new StarPoint(), new LocalOrientation());

	}

	/**
	 * Constructs a drawable box and initializes it (red color)
	 * 
	 * @param starpoint
	 *            global position of the {@link DrawableBox}
	 * @param localOrientation
	 *            local orientation of the {@link DrawableBox}
	 * @param width
	 *            width of the {@link DrawableBox}
	 * @param height
	 *            height of the {@link DrawableBox}
	 */
	public DrawableBox(StarPoint starpoint, LocalOrientation localOrientation,
			int width, int height) {

		super(starpoint, localOrientation);

		this.width = width;
		this.height = height;
		this.box = new Rectangle2D.Double(this.x, this.y, width, height);

	}

	/**
	 * 
	 * Constructs a box, that can be set excentric concerning it's center of
	 * mass. The usage of this excentricity is, that distances used here keep
	 * fixed in cases of Drawables that do not scale in size.
	 * 
	 * @param starpoint
	 *            global position of the {@link DrawableBox}
	 * @param localOrientation
	 *            local orientation of the {@link DrawableBox}
	 * @param x
	 *            coordinate x of the center of mass (excentricity)
	 * @param y
	 *            coordinate y of the center of mass (excentricity)
	 * @param width
	 *            of the box in picture coordinates
	 * @param height
	 *            of the box in picture coordinates
	 */
	public DrawableBox(StarPoint starpoint, LocalOrientation localOrientation,
			int x, int y, int width, int height) {

		super(starpoint, localOrientation);

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.box = new Rectangle2D.Double(this.x, this.y, width, height);

	}

	// ----------------------- Drawable contract -------------------------------

	@Override
	public void draw(Graphics2D graphicsContext,
			AffineTransform locationTransform) {

		this.locationTransform = locationTransform;

		graphicsContext.setColor(decorations.getColor());
		graphicsContext.setStroke(decorations.getStroke());
		Shape rotatedBox = locationTransform.createTransformedShape(this.box);
		if (this.decorations.isFilled()) {
			graphicsContext.fill(rotatedBox);
		}
		graphicsContext.draw(rotatedBox);

	}

	@Override
	public boolean contains(int x, int y) {

		return this.locationTransform.createTransformedShape(this.box)
				.contains(x, y);
	}

	// ----------------------- Decoratable contract ----------------------------

	@Override
	public void applyDecorations(DrawableShapeDecorations d) {
		this.decorations = d;

	}

	/**
	 * Returns a delegate from the used {@link DrawableShapeDecorations} of this
	 * {@link DrawableBox}.
	 * 
	 * @return {@link DrawableShapeDecorations}
	 * 
	 */
	@Override
	public DrawableShapeDecorations getDecorations() {
		return this.decorations;
	}

	// ----------------------- Setter and Getters ------------------------------

	/**
	 * sets the width of the box
	 * 
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
		// TODO Is it necessary to generate a new object or is it somehow
		// possible to modify the existing box.
		this.box =
				new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	/**
	 * sets the height of the box
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
		this.box =
				new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	/**
	 * get filling status of box. Is it filled (true) or empty (false)
	 * 
	 * @return filling status of the {@link Drawable}
	 * @deprecated getDecorations Should be used and the color set there.
	 */

	public boolean isFilled() {
		// TODO Auto-generated method stub
		return this.decorations.isFilled();
	}

	/**
	 * Sets the color of the drawable box.
	 * 
	 * @param color
	 * @deprecated getDecorations Should be used and the color set there.
	 */
	public void setColor(Color color) {
		this.decorations.setColor(color);
	}

	/**
	 * set the filling status of the box. When filled is true, than the drawn
	 * box is filled.
	 * 
	 * @param filled
	 * @deprecated
	 */
	public void setFilled(boolean filled) {
		this.decorations.setFilled(filled);
	}

}
