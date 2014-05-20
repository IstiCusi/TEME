/**
 * 
 */
package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;


public class DrawablePicture extends AbstractDrawable {

	private Rectangle2D box;	
	private BufferedImage image;
	double width, height; 
	AffineTransform locationTransform;

	
	public DrawablePicture(StarPoint starpoint, LocalOrientation localOrientation, String pictureFileName) {
		
		super(starpoint, localOrientation);
		
		try {                
			this.image = ImageIO.read(new File(pictureFileName));
		} catch (IOException ex) {
			System.out.println("File not found");
			System.exit(0);
		}
		
		System.out.println("OK -- File was found");
		
		this.width=image.getWidth();
		this.height=image.getHeight();
		
		this.box= new Rectangle2D.Double(0, 0, this.width, this.height);
				
	}
	
	public DrawablePicture(StarPoint starpoint, LocalOrientation localOrientation, BufferedImage image) {
		
		super(starpoint, localOrientation);
		this.image = image;	
		this.width=image.getWidth();
		this.height=image.getHeight();
		
		this.box= new Rectangle2D.Double(0, 0, this.width, this.height);
				
	}
	
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		this.locationTransform = locationTransform;
		graphicsContext.drawRenderedImage(image,locationTransform);
		
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
		return this.locationTransform.createTransformedShape(this.box).contains(x, y);
	}
	
	public BufferedImage getBufferedImage() {
		return this.image;
	}
	
}
