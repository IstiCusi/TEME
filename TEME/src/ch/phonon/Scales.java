/******************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch,
 * 
 * Zurich, Switzerland All Rights Reserved.
 * 
 ******************************************************************************/

/*
 * TODO: Rewrite the Scales class not to update the union every time the
 * Iterator is needed ... do it pro-actively at any changing event to raise
 * performance. At the moment, I observe no performance hit. But this pattern
 * could be similarly used for the points etc container.
 */

package ch.phonon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
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
public class Scales implements Iterable<DrawableScaleReference> {

	private List<DrawableScaleReference> inactiveScales;
	private List<DrawableScaleReference> activeScales;
	private List<DrawableScaleReference> union; // union of active and inactive

	private DrawableScaleReference lastActivated; // chosen scale (the last

	// activated)

	// --------------------- Constructors -------------------------------------

	/**
	 * Constructs the Scales value container.
	 */
	public Scales() {

		this.activeScales = new ArrayList<DrawableScaleReference>();
		this.inactiveScales = new ArrayList<DrawableScaleReference>();
		this.lastActivated = null;
	}

	/**
	 * The Scales value class is an {@link Iterable}. That means, you can loop
	 * with an foreach loop over all scales (the union of active and inactive
	 * ones).
	 * 
	 * @return Iterator used by foreach loops over ALL Scales
	 */
	@Override
	public Iterator<DrawableScaleReference> iterator() {

		updateAndGetAllScales();
		Iterator<DrawableScaleReference> unionIterator = this.union.iterator();
		return unionIterator;
	}

	// --------------------- Add and remove scales ----------------------------

	/**
	 * This method generates a new scales and places it on the position provided
	 * by the mouse (in picture coordinates - the actual mouse position needs
	 * therefore to be calculated by the
	 * {@link TEMView#getPictureCoordinates(Point2D)} function). All actual
	 * active scales are marked as inactive at this moment. The new scale is
	 * oriented horizontally around the chosen position. A general remark: The
	 * scales can be categorized in active (selected) and inactive (unselected)
	 * scales. The last activated scale is the chosen scale.
	 * 
	 * @param actualMousePosition
	 *
	 */
	public void newScale(Point2D.Double actualMousePosition) {

		moveAlltoInactive();

		int beginX = (int) actualMousePosition.getX() - 200;
		int endX = (int) actualMousePosition.getX() + 200;
		int yposition = (int) actualMousePosition.getY();

		DrawableScaleReference newScale = new DrawableScaleReference(new StarPoint(beginX, yposition), new StarPoint(
				endX, yposition));

		if (!this.activeScales.add(newScale)) {
			throw new AssertionError("Could not add an additional scale");
		}
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

	/**
	 * After chosing a scale by mouse pointer (see this
	 * {@link #chooseScale(java.awt.geom.Point2D.Double)} the chosen state is
	 * changed. This function allows to get a reference to the chosen scale.
	 * 
	 * @return chosen scale reference
	 */
	public DrawableScaleReference getChosenScale() {
		return this.lastActivated;
	}

	// -------------------- helper functions ----------------------------------

	private List<DrawableScaleReference> updateAndGetAllScales() {

		List<DrawableScaleReference> union = unifyScales();
		this.setUnion(union);
		return union;
	}

	private void setUnion(List<DrawableScaleReference> union) {
		this.union = union;

	}

	private List<DrawableScaleReference> unifyScales() {

		// TODO: Is is possible to construct something like a view,
		// so that we do not need to construct a new object
		List<DrawableScaleReference> union = new ArrayList<>();
		union.addAll(this.inactiveScales);
		union.addAll(this.activeScales);
		return union;
	}

	private void setChosenScale(DrawableScaleReference scale) {
		this.lastActivated = scale;

	}

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
	private DrawableScaleReference findFirstScaleThatContains(Point2D.Double actualMousePosition) {

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
	 * Gives back the total number of scales (active & inactive ones)
	 * 
	 * @return total number of scales
	 */
	public int size() {
		return (this.inactiveScales.size() + this.activeScales.size());
	}

}
