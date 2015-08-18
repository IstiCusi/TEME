/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.DrawableScaleReference;
import ch.phonon.drawables.DrawableScaleReference.ActiveState;
import ch.phonon.temview.TEMView;

/**
 * 
 * The Scales class is a value class that stores a list of
 * {@link DrawableScaleReference}s. It provides functions to modify the list
 * container but also handles other essential properties of the scale as their
 * {@link ActiveState}.
 * 
 * @author phonon
 *
 */
public class Scales {

	private List<DrawableScaleReference> inactiveScales;
	private List<DrawableScaleReference> activeScales;
	private DrawableScaleReference chosenScale;

	/**
	 * Get list of associated {@link DrawableScaleReference}s
	 * 
	 * @return reference to the list of {@link DrawableScaleReference}
	 */
	public List<DrawableScaleReference> getScaleReferencesList() {

		// TODO: Is it possible to construct something like a view,
		// so that we do not need to construct a new object
		List<DrawableScaleReference> union = new ArrayList<>();
		union.addAll(this.inactiveScales);
		union.addAll(this.activeScales);

		System.out.println("Union: Number of total scales: " + union.size());

		return union;
	}

	// --------------------- Constructors -------------------------------------

	/**
	 * Constructs the Scales value container.
	 */
	public Scales() {
		this.activeScales = new ArrayList<DrawableScaleReference>();
		this.inactiveScales = new ArrayList<DrawableScaleReference>();
	}

	// --------------------- Add and remove scales ----------------------------

	/**
	 * This method generates a new scales and places it on the position provided
	 * by the mouse (in picture coordinates - the actual mouse position needs
	 * therefore to be calculated by the
	 * {@link TEMView#getPictureCoordinates(Point2D)} function). All actual
	 * active scales are marked as inactive at this moment. The new scale is
	 * oriented horizontally around the chosen position.
	 * 
	 * @param actualMousePosition
	 */
	public void newScale(Point2D.Double actualMousePosition) {

		int beginX = (int) actualMousePosition.getX() - 200;
		int endX = (int) actualMousePosition.getX() + 200;
		int yposition = (int) actualMousePosition.getY();

		moveAlltoInactive();

		DrawableScaleReference newScale = new DrawableScaleReference(new StarPoint(beginX, yposition),
				new StarPoint(endX, yposition));

		boolean success = this.activeScales.add(newScale);

		if (!success) {
			throw new AssertionError("Could not add an addtional scale");
		} else {
			System.out.println("New scale added");
		}

		System.out.println("NUmber of active scales: " + this.activeScales.size());

	}

	/**
	 * This method chooses a scale in finding the first that is reflected by the
	 * delivered mouse position in TEMView coordinates. When a scale is found,
	 * that fits and contains the mouse position it is given back. All other
	 * scales are shifted to {@link ActiveState#INACTIVE} state.
	 * 
	 * @param actualMousePosition
	 * @return first found scale that fits to the mouse position
	 */

	public DrawableScaleReference chooseScale(Point2D.Double actualMousePosition) {

		// Get reference to found scale
		DrawableScaleReference scale = findFirstScaleThatContains(actualMousePosition);

		if (scale != null) {

			moveAlltoInactive();
			this.inactiveScales.remove(scale);
			scale.setActiveState(ActiveState.ACTIVE);
			this.activeScales.add(scale);
			this.setChosenScale(scale);
			return scale;
		} else {
			this.setChosenScale(null);
			return null;
		}
	}

	// -------------------- helper functions ----------------------------------

	private void setChosenScale(DrawableScaleReference scale) {
		this.chosenScale = scale;

	}

	/**
	 * Move all stored scales into inactive mode
	 */
	private void moveAlltoInactive() {
		for (DrawableScaleReference drawableScaleReference : activeScales) {
			drawableScaleReference.setActiveState(ActiveState.INACTIVE);
		}
		inactiveScales.addAll(activeScales);
		this.activeScales = new ArrayList<DrawableScaleReference>();
	}

	/**
	 * This method finds and gives back a reference to the first found
	 * {@link DrawableScaleReference} that contains the actual mouse position
	 * provided in {@link TEMView} coordinates.
	 * 
	 * @param actualMousePosition
	 *            the mouse position in temView coordinates to allow the
	 *            {@link Drawable#contains(int, int)} check.
	 * @return the first selected Scale
	 */
	public DrawableScaleReference findFirstScaleThatContains(Point2D.Double actualMousePosition) {

		for (DrawableScaleReference drawableScaleReference : activeScales) {
			if (drawableScaleReference.contains((int) actualMousePosition.getX(), (int) actualMousePosition.getY()))
				return drawableScaleReference;
		}
		for (DrawableScaleReference drawableScaleReference : inactiveScales) {
			if (drawableScaleReference.contains((int) actualMousePosition.getX(), (int) actualMousePosition.getY()))
				return drawableScaleReference;
		}
		return null; // no scale was found at the provided position
	}

	/**
	 * After chosing a scale by mouse pointer (see this
	 * {@link #chooseScale(java.awt.geom.Point2D.Double)} the chosen state is
	 * changed. This function allows to get a reference to the chosen scale.
	 * 
	 * @return chosen scale reference
	 */
	public DrawableScaleReference getChosenScale() {
		return this.chosenScale;
	}

	/**
	 * Gives back the total number of scales (active & inactive ones)
	 * 
	 * @return total number of scales
	 */
	public int size() {
		return (this.inactiveScales.size() + this.activeScales.size());
	}

}
