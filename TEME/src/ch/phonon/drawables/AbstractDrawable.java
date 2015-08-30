/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

// TODO: Correct the very ugly downcast - that they are save 
// TODO: Check once again Drawable and AbstractDrawable on @Override etc
// TODO: Check how we can functionalize the Drawables for better control 
// see DrawableProperties
// of other properties like color etc. What can be part of the interface ?
// TODO: Check all types on clone(), equals ...etc Unit Tests
// TODO: Implement a correct handling of exceptions ... CloneBlaBlaException 

// TODO:  make transformViewPort a wrapper class including the temViewState
// and the corresponding AffineTransform. Makes it probably much easier to 
// handle. The paint method can directly access than the temViewState and does
// not need to get it from a different source. 

package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import ch.phonon.InvariantScalingType;
import ch.phonon.LocalOrientation;
import ch.phonon.Positionable;
import ch.phonon.StarPoint;
import ch.phonon.temview.TEMView;
import ch.phonon.temview.TEMViewState;

/**
 * This abstract class implements some standard methods, that can be used by any
 * {@link Drawable} implementation.
 * 
 * @author phonon
 * 
 */
public abstract class AbstractDrawable implements Drawable, Positionable {

	static private AffineTransform viewPortTransform;
	static private TEMViewState temViewState;

	private LocalOrientation initialOrientationState;
	private LocalOrientation localOrientationState;
	private StarPoint starpoint;

	/**
	 * invariantRotation flag: When true, rotation is independent of
	 * temViewState
	 */
	private boolean invariantRotation = false;

	/**
	 * invariantScaling flag: When true, scaling is independent of temViewState
	 */
	private boolean invariantScaling = false;

	/**
	 * invariantScalingType flag: Distinguishes different variants of
	 * invariantScaling
	 */
	public InvariantScalingType invariantScalingType = InvariantScalingType.BOTH;

	/**
	 * calculates a {@link AffineTransform} of the viewPort rotation/pan/zoom
	 * state based on the initial {@link AffineTransform} state and the
	 * {@link TEMViewState} associated to the {@link TEMView}.
	 * 
	 * @param initial
	 *            the initial {@link AffineTransform} state of the temView
	 * @param temviewState
	 *            the pan/zoom/rotation state of the {@link TEMView}
	 * @return viewPortTransform - the {@link AffineTransform} obtained by the
	 *         transformations related to zooming, panning and scaling of the
	 *         TEMView.
	 */
	static public AffineTransform transformViewPort(AffineTransform initial,
			TEMViewState temviewState) {

		viewPortTransform = new AffineTransform(initial);
		temViewState = temviewState;

		// ViewPort transformations-read from the back to front: Operator ABC *

		viewPortTransform.setToTranslation(temviewState.x, temviewState.y);
		viewPortTransform.rotate(Math.toRadians(temviewState.rotation), 0, 0);
		viewPortTransform.scale(temviewState.scaling, temviewState.scaling);

		return viewPortTransform;
	}

	/**
	 * Rotates an initial <code>AffineTransform</code> about the viewPort's
	 * rotation. Zoom/Pan states are neglected, only the rotation applied to the
	 * initial transformation.
	 * 
	 * @param initial
	 *            <code>AffineTransform</code>
	 * @param temviewState
	 *            pan/zoom/rotation state of the <code>TEMView</code>
	 * @return new representation of the view based on the
	 *         <code>temviewState</code>
	 */
	static public AffineTransform rotateWithViewPort(AffineTransform initial,
			TEMViewState temviewState) {

		viewPortTransform = new AffineTransform(initial);
		temViewState = temviewState;
		viewPortTransform.rotate(Math.toRadians(temviewState.rotation), 0, 0);
		return viewPortTransform;
	}

	abstract void draw(Graphics2D graphicsContext,
			AffineTransform locationTransform);

	@Override
	abstract public double getWidth();

	@Override
	abstract public double getHeight();

	/**
	 * This <b>main</b> constructor needs to be called by all implementing
	 * classes and sets the starPoint, the <b>initial</b> and the <b>actual</b>
	 * local orientation of the {@link Drawable}.
	 * 
	 * @param starpoint
	 * @see StarPoint
	 * @param localOrientation
	 * @see LocalOrientation
	 */
	public AbstractDrawable(StarPoint starpoint,
			LocalOrientation localOrientation) {
		setStarPoint((StarPoint) starpoint.clone());
		setInitialOrientationState((LocalOrientation) localOrientation.clone());
		setLocalOrientationState((LocalOrientation) localOrientation.clone());
	}

	/**
	 * This paint method implements and specializes the paint method in
	 * considering the invariant rotation and scaling in case of Drawable items
	 * that one need to keep the position but should not scale or rotate with
	 * the viewPortTranspform. To specify the invariance of the item, you need
	 * to use the {@link AbstractDrawable#setInvariantRotation(boolean)} or /and
	 * the {@link AbstractDrawable#setInvariantScaling(boolean)} function. In
	 * the case of invariant scaling, one can set certain constraints concerning
	 * the invariance of certain axis using the {@link InvariantScalingType}
	 * using the member {@link AbstractDrawable#invariantScalingType} of the
	 * class.
	 * 
	 * @param graphicsContext
	 *            the graphic context to paint into.
	 * @param viewPortTransform
	 *            the actual transformation of the viewport.
	 * @see ch.phonon.drawables.Drawable#paint(java.awt.Graphics2D,
	 *      java.awt.geom.AffineTransform)
	 */
	@Override
	public void paint(Graphics2D graphicsContext,
			AffineTransform viewPortTransform) {

		LocalOrientation drawableOrientationState = this
				.getLocalOrientationState();
		StarPoint starpoint = this.getStarPoint();

		double localScaling = drawableOrientationState.getScaling();

		AffineTransform locationTransform = new AffineTransform(
				viewPortTransform);

		locationTransform.translate(
				drawableOrientationState.getLocalX().getX(),
				drawableOrientationState.getLocalX().getY());

		double rotationCorrection = 0.0;
		if ( getInvariantRotation() ) {
			rotationCorrection = temViewState.rotation;
		}

		locationTransform.rotate(
				Math.toRadians(drawableOrientationState.getRotation()
						- rotationCorrection), starpoint.getX(),
				starpoint.getY());
		locationTransform.translate(starpoint.getX(), starpoint.getY());

		double viewScaling = 1.0;
		double localScalingX = localScaling;
		double localScalingY = localScaling;

		if ( getInvariantScaling() 
				&& this.invariantScalingType == InvariantScalingType.BOTH) {

			viewScaling = temViewState.scaling;
			localScalingX = localScaling / viewScaling;
			localScalingY = localScaling / viewScaling;
		}

		if ( getInvariantScaling() 
				&& this.invariantScalingType == InvariantScalingType.FIXEDX) {

			viewScaling = temViewState.scaling;
			localScalingX = localScaling / viewScaling;
			localScalingY = localScaling;
		}

		if ( getInvariantScaling() 
				&& this.invariantScalingType == InvariantScalingType.FIXEDY) {

			viewScaling = temViewState.scaling;
			localScalingX = localScaling;
			localScalingY = localScaling / viewScaling;

		}

		locationTransform.translate(-getWidth() / 2.0 * localScalingX,
				-getHeight() / 2.0 * localScalingY);
		locationTransform.scale(localScalingX, localScalingY);

		this.draw(graphicsContext, locationTransform);

	}

	@Override
	public LocalOrientation getLocalOrientationState() {
		return this.localOrientationState;
	}

	@Override
	public void setLocalOrientationState(LocalOrientation localOrientation) {
		this.localOrientationState = localOrientation;
	}

	@Override
	public StarPoint getStarPoint() {
		return starpoint;
	}

	@Override
	public void setStarPoint(StarPoint starPoint) {

		this.starpoint = (StarPoint) starPoint.clone();

	}

	/**
	 * @return x component of the {@link StarPoint}
	 */
	public double getX() {
		return this.starpoint.getX();
	}

	/**
	 * @return y component of the {@link StarPoint}
	 */
	public double getY() {
		return this.starpoint.getY();
	}

	/**
	 * @param x
	 *            set x component of the {@link StarPoint}
	 */
	public void setX(double x) {
		this.starpoint.setX(x);
	}

	/**
	 * @param y
	 *            set y component of the {@link StarPoint}
	 */
	public void setY(double y) {
		this.starpoint.setY(y);
	}

	@Override
	public LocalOrientation getInitialOrientationState() {
		return initialOrientationState;
	}

	@Override
	public void setInitialOrientationState(
			LocalOrientation initialOrientationState) {
		this.initialOrientationState = initialOrientationState;
	}

	@Override
	public void setInvariantScaling(boolean invariantScaling) {
		this.invariantScaling = invariantScaling;
	}

	@Override
	public boolean getInvariantScaling() {
		return this.invariantScaling;
	}

	@Override
	public void setInvariantRotation(boolean invariantRotation) {
		this.invariantRotation = invariantRotation;

	}

	@Override
	public boolean getInvariantRotation() {
		return this.invariantRotation;
	}

}
