/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables.primitives;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.orientation.LocalOrientation;
import ch.phonon.drawables.orientation.StarPoint;

/**
 * The {@link DrawablePicture} class provides a {@link Drawable} picture, that
 * can be drawn like other {@link Drawable}s. The pictures can be either
 * directly provided as {@link BufferedImage}s or loaded by providing a file
 * name.
 * 
 * @author phonon
 * 
 */
public class DrawablePicture extends AbstractDrawable {

	private Rectangle2D box;
	private BufferedImage image;
	double width, height;
	AffineTransform locationTransform;

	/**
	 * This constructor constructs a {@link DrawablePicture} based on a
	 * centering {@link StarPoint} given in picture coordinates, a local
	 * orientation and a file name.
	 * 
	 * @param starpoint
	 *            center of the picture in picture coordinates
	 * @param localOrientation
	 *            local orientation (scale and rotation)
	 * @param pictureFileName
	 *            file name of the picture to be loaded
	 */
	public DrawablePicture(StarPoint starpoint,
			LocalOrientation localOrientation, String pictureFileName) {

		super(starpoint, localOrientation);

		try {
			this.image = ImageIO.read(new File(pictureFileName));
		} catch (IOException ex) {
			System.out.println("File not found");
			System.exit(0);
		}

		System.out.println("OK -- File was found");

		this.width = image.getWidth();
		this.height = image.getHeight();

		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);

	}

	/**
	 * This constructor constructs a {@link DrawablePicture} based on a
	 * centering {@link StarPoint} given in picture coordinates, a local
	 * orientation and a {@link BufferedImage} already loaded in memory.
	 * 
	 * @param starpoint
	 *            center of the picture in picture coordinates
	 * @param localOrientation
	 *            local orientation (scale and rotation)
	 * @param image
	 *            {@link BufferedImage} to be used.
	 */
	public DrawablePicture(StarPoint starpoint,
			LocalOrientation localOrientation, BufferedImage image) {

		super(starpoint, localOrientation);

		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();

		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);

	}

	@Override
			public void draw(Graphics2D graphicsContext,
					AffineTransform locationTransform) {
		this.locationTransform = locationTransform;
		graphicsContext.drawRenderedImage(image, locationTransform);

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
		return this.locationTransform.createTransformedShape(this.box)
				.contains(x, y);
	}

	/**
	 * Gives back the reference of the associated {@link BufferedImage}
	 * 
	 * @return reference to the associated image
	 */
	public BufferedImage getBufferedImage() {
		return this.image;
	}

}
