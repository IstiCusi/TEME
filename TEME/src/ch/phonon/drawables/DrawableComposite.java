/*************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * The abstract {@link DrawableComposite} class allows to group
 * {@link Drawables} to one Drawable, what allows to construct more complex
 * shapes. It fulfills the contract of a normal {@link Drawable}. The
 * {@link #contains(int, int)} function checks if one of the {@link Drawable}s
 * contains the point. The class is abstract to inform the user/client to always
 * extend.
 * 
 * @author phonon
 *
 */
abstract public class DrawableComposite implements Drawable {

	ArrayList<Drawable> drawableList;

	/**
	 * constructs and initializes the {@link DrawableComposite}
	 */
	public DrawableComposite() {
		this.drawableList = new ArrayList<Drawable>();

	}

	/**
	 * This method is used to add {@link Drawable}s to the
	 * {@link DrawableComposite}.
	 * 
	 * @param drawable
	 */
	public void add(Drawable drawable) {
		drawableList.add(drawable);
	}

	/**
	 * This function removes by reference not by value, when the Drawables do
	 * not implement the equals method. Maybe we should add a index based
	 * removing to avoid problems. Or we organize the DrawableComposite in a
	 * Map<String, Drawable> and choose the Drawable by key name.
	 * 
	 * @param drawable
	 */
	@Deprecated
	public void remove(Drawable drawable) {
		// TODO: Type safety
		// we need to implement equals in all drawables, right ?
		// if indexof finds objects not logically but by object id,
		// than we need to clearly document this in the remove method.
		int index = drawableList.indexOf(drawable);
		drawableList.remove(index);
	}

	@Override
	public void paint(Graphics2D graphicsContext,
			AffineTransform transformation) {
		for (Drawable element : drawableList) {
			element.paint(graphicsContext, transformation);
		}
	}

	@Override
	public boolean contains(int x, int y) {
		boolean contained = false;
		for (Drawable element : drawableList) {
			contained = contained || element.contains(x, y);
		}
		return contained;
	}

}
