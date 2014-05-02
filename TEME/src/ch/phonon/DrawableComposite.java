package ch.phonon;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawableComposite implements Drawable {

	ArrayList<Drawable> drawableList;
	private LocalOrientation localOrientation; 
	
	public DrawableComposite() {
		this.drawableList = new ArrayList<Drawable>();
		this.localOrientation = new LocalOrientation();
		
		
	}
	
	public void add (Drawable drawable) {
		drawableList.add(drawable);
	}

	@Override
	public void paint(Graphics2D graphicsContext, AffineTransform transformation) {
		for(Drawable element: drawableList) {
			element.paint(graphicsContext, transformation);
		}
	}

	
	@Override
	public LocalOrientation getLocalOrientationState() {
		return this.localOrientation;
	}

	
	@Override
	public void setLocalOrientationState(LocalOrientation localOrientation) {
		this.localOrientation = localOrientation;
		
	}

	
	@Override
	public StarPoint getStarPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void setStarPoint(StarPoint starPoint) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public LocalOrientation getInitialOrientationState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInitialOrientationState(
			LocalOrientation initialOrientationState) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#getHeight()
	 */
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#setInvariantScaling(boolean)
	 */
	@Override
	public void setInvariantScaling(boolean invariantScaling) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#getInvariantScaling()
	 */
	@Override
	public boolean getInvariantScaling() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#setInvariantRotation(boolean)
	 */
	@Override
	public void setInvariantRotation(boolean invariantRotation) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#getInvariantRotation()
	 */
	@Override
	public boolean getInvariantRotation() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.phonon.Drawable#contains(int, int)
	 */
	@Override
	public boolean contains(int x, int y) {
		boolean contained = false;
		for(Drawable element: drawableList) {
			contained = contained || element.contains(x, y);
		}
		return contained;
	}

	
}	
	



