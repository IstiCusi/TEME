/**
 * 
 */
package ch.phonon.drawables;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.InvariantScalingType;
import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;



//TODO: We need to check, when the begin and end rectangles 
// are on minimal distance.==> Exception 

public class DrawableScaleReference extends DrawableComposite {

	public DrawableScaleReference(StarPoint begin , StarPoint end) {
		super();
		
		double sizeOfGrip = 20.0;
		
		double distance = StarPoint.getDistance(begin, end);
		
		StarPoint relativeVector = StarPoint.getDifference(begin, end);
		StarPoint unitVector = StarPoint.getUnitVector(relativeVector);
		
		System.out.println(relativeVector);
		System.out.println(unitVector);
		
		double angle = StarPoint.getAngle(new StarPoint(10,0),relativeVector);
		
		double xMiddle = begin.getX()+relativeVector.getX()/2.0;
		double yMiddle = begin.getY()+relativeVector.getY()/2.0;
		
		StarPoint DrawableMiddleStarPoint = new StarPoint (xMiddle, yMiddle);
		
		
		LocalOrientation localOrientationOuterBox = new LocalOrientation(new Point2D.Double(0,0),angle,1.0);
		
		DrawableBox outerBox = new DrawableBox(DrawableMiddleStarPoint, localOrientationOuterBox, (int)(Math.round(distance)+2*sizeOfGrip), (int)sizeOfGrip);
		outerBox.setColor(new Color(0x890ADF));
		
		StarPoint starPointLeftGrip = StarPoint.getDifference(StarPoint.getScaleVector(unitVector, sizeOfGrip/2),begin);
		LocalOrientation localOrientationLeftGrip = new LocalOrientation(new Point2D.Double(0,0),angle,1.0);		
		DrawableBox leftGrip = new DrawableBox(starPointLeftGrip, localOrientationLeftGrip, (int)(sizeOfGrip), (int)(sizeOfGrip)-4);
		leftGrip.setColor(new Color(0xFFEA00));
		
		
		StarPoint starPointRightGrip = StarPoint.getDifference(StarPoint.getScaleVector(unitVector, -sizeOfGrip/2),end);
		LocalOrientation localOrientationRightGrip = new LocalOrientation(new Point2D.Double(0,0),angle,1.0);
		DrawableBox rightGrip = new DrawableBox(starPointRightGrip, localOrientationRightGrip, (int)(sizeOfGrip), (int)(sizeOfGrip)-4);
		rightGrip.setColor(new Color(0xFFEA00));
		
		//TODO: Length of the ScaleReference should scale with the temView state, the thickness however should stay the same.
		
		outerBox.setInvariantRotation(false);
		outerBox.setInvariantScaling(true);
		outerBox.invariantScalingType=InvariantScalingType.FIXEDY;
		
		leftGrip.setInvariantRotation(false);
		leftGrip.setInvariantScaling(true);
		leftGrip.invariantScalingType=InvariantScalingType.FIXEDY;
		
		rightGrip.setInvariantRotation(false);
		rightGrip.setInvariantScaling(true);
		rightGrip.invariantScalingType=InvariantScalingType.FIXEDY;
		
		this.add(outerBox);
		this.add(leftGrip);
		this.add(rightGrip);
		
	}

	
	
}
