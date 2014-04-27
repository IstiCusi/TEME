/**
 * 
 */
package ch.phonon;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class DrawablePicture extends AbstractDrawable {

	private BufferedImage image;
	double width, height; 

	
	DrawablePicture(StarPoint starpoint, LocalOrientation localOrientation, String pictureFileName) {
		
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
				
	}
	
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
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
	
}
