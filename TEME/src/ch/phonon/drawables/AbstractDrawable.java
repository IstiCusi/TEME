// TODO: Correct the very ugly downcasts - that they are save 
// TODO: Check once again Drawable and AbstractDrawable on @Override etc
// TODO: Check how we can functionalize the Drawables for better control 
// of other properties like color etc. What can be part of the interface ?
// TODO: Check all types on clone(), equals ...etc Unit Tests
// TODO: Implement a correct handling of exceptions ... CloneBlaBlaException 

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
 * @author phonon
 *
 */
public abstract class AbstractDrawable implements Drawable, Positionable {
	
	static private AffineTransform viewPortTransform;
	static private TEMViewState temViewState;
	
	private LocalOrientation initialOrientationState;
	private LocalOrientation localOrientationState;
	private StarPoint starpoint;
	
	public boolean invariantRotation = false;
	
	public boolean invariantScaling  = false;
	public InvariantScalingType invariantScalingType 	 =  InvariantScalingType.BOTH;

	
	/** 
	 * calculates a {@link AffineTransform} of the viewPort rotation/pan/zoom state 
	 * based on the initial {@link AffineTransform} state and the {@link TEMViewState} 
	 * associated to the {@link TEMView}. 
	 * @param initial - initial {@link AffineTransform} state of the temView
	 * @param temviewState
	 * @return viewPortTransform - the {@link AffineTransform} obtained by the
	 * transformations related to zooming, panning and scaling of the TEMView. 
	 */
	static  public AffineTransform transformViewPort (AffineTransform initial, 
													TEMViewState temviewState) {
		
		viewPortTransform= new AffineTransform(initial);
		temViewState=temviewState;
					
		// ViewPort transformations-read from the back to front: Operator ABC *
		
			viewPortTransform.setToTranslation(temviewState.x,temviewState.y);
			viewPortTransform.rotate(Math.toRadians(temviewState.rotation), 0, 0);
			viewPortTransform.scale(temviewState.scaling, temviewState.scaling);
		
		return viewPortTransform;
	}
	
	static  public AffineTransform rotateWithViewPort (AffineTransform initial, 
			TEMViewState temviewState) {

		viewPortTransform= new AffineTransform(initial);
		temViewState=temviewState;
		
		// ViewPort transformations-read from the back to front: Operator ABC *
		
		//viewPortTransform.setToTranslation(temviewState.x,temviewState.y);
		viewPortTransform.rotate(Math.toRadians(temviewState.rotation), 0, 0);
		//viewPortTransform.scale(temviewState.scaling, temviewState.scaling);
		
		return viewPortTransform;
		}
		
	
	
	abstract void draw (Graphics2D graphicsContext, AffineTransform locationTransform);
	abstract public double getWidth () ;
	abstract public double getHeight() ;


	public AbstractDrawable(StarPoint starpoint, LocalOrientation localOrientation) {
		setStarPoint ((StarPoint)starpoint.clone());
		setInitialOrientationState((LocalOrientation) localOrientation.clone());
		setLocalOrientationState((LocalOrientation)localOrientation.clone());
	}
	
	@Override
	public void paint(Graphics2D graphicsContext, AffineTransform viewPortTransform) {
		
		LocalOrientation drawableOrientationState = this.getLocalOrientationState();
		StarPoint starpoint = this.getStarPoint();
		
		double localScaling = drawableOrientationState.getScaling();
		
		AffineTransform locationTransform = new AffineTransform(viewPortTransform);
		
		
		locationTransform.translate(	drawableOrientationState.getLocalX().getX(), 
										drawableOrientationState.getLocalX().getY());
		
		double rotationCorrection = 0.0;
		if (getInvariantRotation()==true) {
			rotationCorrection = temViewState.rotation;
		}
		
		locationTransform.rotate(Math.toRadians(drawableOrientationState.getRotation()-rotationCorrection), starpoint.getX(), starpoint.getY());
		locationTransform.translate(starpoint.getX(), starpoint.getY());
		
		double viewScaling = 1.0;
		double localScalingX =localScaling;
		double localScalingY =localScaling;
		
		
		if (getInvariantScaling()==true && this.invariantScalingType==InvariantScalingType.BOTH) {
			
			viewScaling = temViewState.scaling;
			localScalingX = localScaling/viewScaling;
			localScalingY = localScaling/viewScaling;
		}
		
		if (getInvariantScaling()==true && this.invariantScalingType==InvariantScalingType.FIXEDX) {
	
			viewScaling = temViewState.scaling;			
			localScalingX = localScaling/viewScaling;
			localScalingY = localScaling;
		}

		if (getInvariantScaling()==true && this.invariantScalingType==InvariantScalingType.FIXEDY) {
	
			viewScaling = temViewState.scaling;				
			localScalingX = localScaling;
			localScalingY = localScaling/viewScaling;
			
		}

		
		
		locationTransform.translate(-getWidth()/2.0*localScalingX, -getHeight()/2.0*localScalingY);
		locationTransform.scale(localScalingX,localScalingY);
			
		this.draw(graphicsContext, locationTransform);
		
	}
	
	
	@Override
	public LocalOrientation getLocalOrientationState() {
		return this.localOrientationState;
	}

	@Override
	public void setLocalOrientationState( LocalOrientation localOrientation) {
		this.localOrientationState=localOrientation;
	}

	@Override
	public StarPoint getStarPoint() {
		return starpoint;
	}

	@Override
	public void setStarPoint(StarPoint starPoint) {
		
			this.starpoint = (StarPoint)starPoint.clone();
		
	}	
	
	@Override
	public LocalOrientation getInitialOrientationState() {
		return initialOrientationState;
	}

	@Override
	public void setInitialOrientationState(LocalOrientation initialOrientationState) {
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
		// TODO Auto-generated method stub
		return this.invariantRotation;
	}

}
