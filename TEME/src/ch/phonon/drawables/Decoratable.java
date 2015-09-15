/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

/**
 * Interface that any {@link Drawable} needs to implement, when it should be
 * allowed to be decorated with certain shape independent properties as e.g. the
 * color, the line thickness, type, the font, the size of the font etc.
 * 
 * @author phonon
 *
 * @param <T>
 */
public interface Decoratable<T extends Decorations> {

	/**
	 * 
	 * This function is used to apply {@link DrawableShapeDecorations} that
	 * allow to adjust typical non shape related properties like color, line
	 * style.
	 * 
	 * @param d
	 *            {@link Decorations} to use
	 */
	public void applyDecorations(T d);

	/**
	 * This function is used to obtain a {@link Decorations} delegate from the
	 * {@link Decoratable} objects (All {@link Drawable} implementions derived
	 * from {@link AbstractDrawable} should implement this interface to
	 * declutter their own class files.
	 * 
	 * @return Decoration instance used by the Drawables, that implement the
	 *         {@link Decoratable} interface.
	 */
	public T getDecorations();

}
