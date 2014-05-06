package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawableComposite implements Drawable {

	ArrayList<Drawable> drawableList;
	
	public DrawableComposite() {
		this.drawableList = new ArrayList<Drawable>();
		
		
	}
	
	public void add (Drawable drawable) {
		drawableList.add(drawable);
	}
	
	public void remove (Drawable drawable) {
		//TODO: Type safety
		int index = drawableList.indexOf(drawable);
		drawableList.remove(index);
	}

	@Override
	public void paint(Graphics2D graphicsContext, AffineTransform transformation) {
		for(Drawable element: drawableList) {
			element.paint(graphicsContext, transformation);
		}
	}

	public boolean contains(int x, int y) {
		boolean contained = false;
		for(Drawable element: drawableList) {
			contained = contained || element.contains(x, y);
		}
		return contained;
	}

	
}	
	



