/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

//TODO: Probably one should reformulate the item as Singleton
// or does it make sense, if we assume for every TEMAllied a different
// viewing state ?!?

/**
 * The TEMViewState class represents the zooming, scaling and panning state of
 * the TEMView. It furthermore keeps track of the width and height of the
 * TEMView.
 * 
 * @author phonon
 * 
 */
public class TEMViewState {

	// TODO: We should implement the state private
	// How to keep the simple way of reading a value ?
	// better make double and not int ?

	/**
	 * cWidth contains the width of the {@link TEMView} canvas
	 */
	public int cWidth;
	/**
	 * cWidth contains the height of the {@link TEMView} canvas
	 */
	public int cHeight;

	/**
	 * scaling contains the actual scaling of the {@link TEMView}.
	 */
	public double scaling;

	/**
	 * rotation contains the actual rotation of the {@link TEMView}.
	 */
	public double rotation;

	/**
	 * contains the actual panning position x coordinate of the {@link TEMView}.
	 */
	public int x;

	/**
	 * contains the actual panning position y coordinate of the {@link TEMView}.
	 */
	public int y;

	/**
	 * The standard constructor is used to construct the unmodified state of the
	 * {@link TEMView} where the canvas shows picture coordinates (not rotated,
	 * not scaled, not panned).
	 */
	TEMViewState() {
		super();
		cWidth = 0;
		cHeight = 0;
		scaling = 1;
		rotation = 0;
		x = 0;
		y = 0;
	}

	/**
	 * This copy constructor let you obtain a new {@link TEMViewState} object
	 * using a deep copy by value from a given {@link TEMViewState} object
	 * 
	 * @param temviewState
	 */
	TEMViewState(TEMViewState temviewState) {
		setTEMViewState(temviewState);
	}

	/**
	 * Constructor that can be used to define the initial conditions of the
	 * {@link TEMViewState}. Most of the time you will use the standard
	 * constructor, that represents the unmodified state of the {@link TEMView}
	 * where the canvas shows picture coordinates.
	 * 
	 * @param cWidth
	 * @param cHeight
	 * @param scaling
	 * @param rotation
	 * @param x
	 * @param y
	 */
	TEMViewState(int cWidth, int cHeight, double scaling, double rotation,
			int x, int y) {
		this.cWidth = cWidth;
		this.cHeight = cHeight;
		this.scaling = scaling;
		this.rotation = rotation;
		this.x = x;
		this.y = y;
	}

	/**
	 * Allows to set the {@link TEMViewState} by deep copy. Be aware, that the
	 * states properties are all public and can be modified independently as
	 * well ( see e.g. {@link TEMViewState#cWidth}, {@link TEMViewState#cHeight}
	 * , {@link TEMViewState#scaling}, {@link TEMViewState#rotation},
	 * {@link TEMViewState#x} or {@link TEMViewState#y} )
	 * 
	 * 
	 * @param temviewState
	 */
	public void setTEMViewState(TEMViewState temviewState) {
		cWidth = temviewState.cWidth;
		cHeight = temviewState.cHeight;
		scaling = temviewState.scaling;
		rotation = temviewState.rotation;
		x = temviewState.x;
		y = temviewState.y;
	}

	// @Override
	// protected TEMViewState clone() {
	// // TODO Bad design -- should be not implemented by constructor
	// return new TEMViewState(this.cWidth, this.cHeight,
	// this.scaling, this.rotation, this.x, this.y);
	//
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cHeight;
		result = prime * result + cWidth;
		long temp;
		temp = Double.doubleToLongBits(rotation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(scaling);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TEMViewState other = (TEMViewState) obj;
		if (cHeight != other.cHeight)
			return false;
		if (cWidth != other.cWidth)
			return false;
		if (Double.doubleToLongBits(rotation) != Double
				.doubleToLongBits(other.rotation))
			return false;
		if (Double.doubleToLongBits(scaling) != Double
				.doubleToLongBits(other.scaling))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
