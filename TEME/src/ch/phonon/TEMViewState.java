/**
 * 
 */
package ch.phonon;

/**
 * @author phonon
 *
 */
public class TEMViewState {
	
	// TODO: We should implement the state private  
	// How to keep the simple way of reading a value ?
	
	public int cWidth;
	public int cHeight;
	
	public double scaling;
	public double rotation;
	public int x,y ;
	
	TEMViewState () {
		super();
		scaling  = 1;
		rotation = 0;
		x = 0;
		y =	0;
	}
	
	TEMViewState( TEMViewState temviewState ) {
		setTEMViewState(temviewState);
	}
	
	TEMViewState( int cWidth, int cHeight, double scaling, double rotation, int x, int y) {
		this.cWidth=cWidth;
		this.cHeight=cHeight;
		this.scaling=scaling;
		this.rotation=rotation;
		this.x=x;
		this.y=y;
	}
	
	public void setTEMViewState (TEMViewState temviewState) {
		cWidth=temviewState.cWidth;
		cHeight=temviewState.cHeight;
		scaling=temviewState.scaling;
		rotation=temviewState.rotation;
		x=temviewState.x;
		y=temviewState.y;
	}
	
	@Override
	protected TEMViewState clone() {
		return new TEMViewState(this.cWidth, this.cHeight, 
				this.scaling, this.rotation, this.x, this.y);
		
	}

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
