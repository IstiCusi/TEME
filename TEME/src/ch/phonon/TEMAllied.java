/**
 * 
 */
package ch.phonon;

import java.util.ArrayList;

/**
 * @author phonon
 *
 */
public class TEMAllied {
	
	private 	Drawable temPicture = null;
	private		ArrayList<DrawablePoint> pointsList = null; 
	
	public TEMAllied() {
		this.temPicture = null;
		this.pointsList = null;
	}
	
	public TEMAllied(String fileName) {
		loadTEMPicture(fileName);
	}
	
	public void loadTEMPicture (String fileName) {
		LocalOrientation initialLocalOrientation = new LocalOrientation();
		StarPoint initialStarpoint = new StarPoint(0,0);
		this.temPicture = new DrawablePicture(initialStarpoint, 
				initialLocalOrientation, fileName);
		
		this.pointsList = new ArrayList<DrawablePoint>();
	}
	

}
