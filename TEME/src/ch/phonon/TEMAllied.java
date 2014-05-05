/**
 * 
 */
package ch.phonon;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author phonon
 *
 */
public class TEMAllied {
	
	private 	Drawable drawableTEMPicture;
	private		ArrayList<Drawable> drawableList; 
	private		ArrayList<DrawablePoint> pointsList; 
	
	/**
	 * @return the drawables
	 */
	public ArrayList<Drawable> getDrawables() {
		return drawableList;
	}

	/**
	 * @param drawableList the drawables to set
	 */
	public void setDrawableList (ArrayList<Drawable> drawableList) {
		this.drawableList = drawableList;
	}

	public TEMAllied() {
		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();
	}
	
	public TEMAllied(String fileName) {
		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();
		loadTEMPicture(fileName);
	}
	
	public void loadTEMPicture (String fileName) {
		LocalOrientation initialLocalOrientation = new LocalOrientation();
		StarPoint initialStarpoint = new StarPoint(0,0);
		this.drawableTEMPicture = new DrawablePicture(initialStarpoint, 
				initialLocalOrientation, fileName);		
	}
	
	public void addPoint (DrawablePoint point) {
		this.pointsList.add(point);
	}
	
	
	public void removePoint (int x, int y) {
		
		//TODO: I do not like this index decl. 
		//Is there not a better way ?
//		int index = -1;
//		for (DrawablePoint element : getPointsList()) {
//			if (element.contains(x,y)) {
//					index = getPointsList().indexOf(element);
//					break;
//			}
//		}		
//		if (index > -1) {
//				this.pointsList.remove(index);
//		}
		
		ArrayList<DrawablePoint> listOfPoints = getPointsList();
		for (Iterator<DrawablePoint> iterator = listOfPoints.iterator(); iterator.hasNext(); ) {
			DrawablePoint element = iterator.next();
			if (element.contains(x,y)) {
			    iterator.remove();
			    break;
			}
		}
		
	}
	
	
	

	public Drawable getTemPicture() {
		return drawableTEMPicture;
	}

	
	public void setTemPicture(Drawable temPicture) {
		this.drawableTEMPicture = temPicture;
	}

	/**
	 * @return the pointsList
	 */
	public ArrayList<DrawablePoint> getPointsList() {
		return pointsList;
	}

	/**
	 * @param pointsList the pointsList to set
	 */
	public void setPointsList(ArrayList<DrawablePoint> pointsList) {
		this.pointsList = pointsList;
	}

	
	

}
