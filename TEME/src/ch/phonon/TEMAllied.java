/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

//TODO Check if members are all initialized (icon e.g.) and make this more
// robust -- no unidentified state should be possible. Write unit tests.

package ch.phonon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.DrawableCoordinateSystem;
import ch.phonon.drawables.DrawablePicture;
import ch.phonon.drawables.DrawablePoint;
import ch.phonon.drawables.Drawables;

// TODO The TEMAllied component should also store the viewing state in 
// the TEMView so that when the user changes to the old view of another
// picture can go on working from his old settings 

/**
 * 
 * TEMAllied stores all information generated or related to TEM pictures loaded.
 * The picture is so to say the seeding entity of all associated containers. The
 * class allows to store polygons, scales, pictures, picture information.
 * 
 * @author phonon
 * 
 */
public class TEMAllied {

	private DrawablePicture drawableTEMPicture;
	private ArrayList<Drawable> drawableList;
	private ArrayList<DrawablePoint> pointsList;
	private String name;
	private String information = "";
	private ImageIcon icon;

	/**
	 * Get all {@link Drawable} associated to this {@link TEMAllied}.
	 * 
	 * @return all drawables
	 */
	public ArrayList<Drawable> getDrawables() {
		return drawableList;
	}

	/**
	 * Set a List of drawables to be associated with the loaded TEM picture
	 * 
	 * @param drawableList
	 *            the drawables to set
	 */
	public void setDrawableList(ArrayList<Drawable> drawableList) {
		this.drawableList = drawableList;
	}

	/**
	 * Initializes any empty TEMAllied container that can is used as a first
	 * view onto the temView before another TEMAllied instance is displayed. The
	 * information associated to this standard instance informs the user about,
	 * that there is no picture loaded.
	 */
	public TEMAllied() {
		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();
		this.information = "No picture loaded";
		this.name = "no name";
	}

	/**
	 * 
	 * Initializes an empty {@link TEMAllied} container, loads one picture
	 * from the hard disk and extends the information string directly about the
	 * file name by concatenation.
	 * 
	 * @param fileName
	 *            of the TEM picture to be loaded into the TEMAllied container
	 */
	public TEMAllied(String fileName) {

		File file = new File(fileName);
		this.name = file.getName();
		this.information = this.information + this.name + "\n";

		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();

		// TODO: This is duplicate code... add a class to keep it similar to
		// the empty TEMView.
		DrawableCoordinateSystem cS = new DrawableCoordinateSystem(
				new StarPoint(), (double) 1000, (double) 1000);
		addDrawable(cS);

		loadTEMPicture(fileName);
	}

	/**
	 * Initializes the {@link TEMAllied} container and directly loads it with a
	 * basic TEM picture. The file name added is concatenated to the general
	 * information prefix.
	 * 
	 * @param image
	 * @param fileName
	 */
	public TEMAllied(BufferedImage image, String fileName) {

		this.name = fileName;
		this.information = this.information + this.name + "\n";
		generateIcon(image);

		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();

		DrawableCoordinateSystem cS = new DrawableCoordinateSystem(
				new StarPoint(), (double) 1000, (double) 1000);
		addDrawable(cS);

		LocalOrientation initialLocalOrientation = new LocalOrientation();
		StarPoint initialStarpoint = new StarPoint(0, 0);
		this.drawableTEMPicture = new DrawablePicture(initialStarpoint,
				initialLocalOrientation, image);

	}

	/**
	 * This private function generates an small sized icon of an height of 100
	 * pixels from the reference {@link BufferedImage}. This function is
	 * indented to be directly used after loading the picture.
	 * 
	 * @param image
	 *            original reference image to be used to produce the icon.
	 * 
	 * @see this#loadTEMPicture(String)
	 */
	private void generateIcon(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		// TODO: Review this scaling
		// TODO: Review if it is necessary to always regenerate
		// TODO: the icons

		double scalingFactor = 1;
		scalingFactor = 100.0 / height;
		this.setIcon(new ImageIcon(image.getScaledInstance(
				(int) (width * scalingFactor), (int) (height * scalingFactor),
				java.awt.Image.SCALE_SMOOTH)));
	}

	/**
	 * This private function Loads TEM image by file name into the
	 * {@link TEMAllied} container. Other information is not updated.
	 * 
	 * @param fileName
	 */
	private void loadTEMPicture(String fileName) {
		LocalOrientation initialLocalOrientation = new LocalOrientation();
		StarPoint initialStarpoint = new StarPoint(0, 0);
		this.drawableTEMPicture = new DrawablePicture(initialStarpoint,
				initialLocalOrientation, fileName);
		generateIcon(this.drawableTEMPicture.getBufferedImage());
	}

	/**
	 * Adds an additional {@link DrawablePoint} to the list of points associated
	 * with the TEM picture.
	 * 
	 * @param point
	 *            to be added to the associated list of points
	 */
	public void addPoint(DrawablePoint point) {
		this.pointsList.add(point);
	}

	/**
	 * 
	 * Removes an {@link Drawable} point that's coordinates in tem view space
	 * are given by the x and y coordinate.
	 * 
	 * @param x
	 *            coordinate in the tem view canvas
	 * @param y
	 *            coordinate in the tem view canvas
	 * @return success of operation
	 */
	public boolean removePoint(int x, int y) {

		// TODO: The deletion could be shifted to the repaint
		// section. This can be significantly faster, because
		// we have just to loop once.
		// What would be the best design in this case.

		boolean success = false;

		ArrayList<DrawablePoint> listOfPoints = getPointsList();
		for (Iterator<DrawablePoint> iterator = listOfPoints.iterator(); iterator
				.hasNext();) {
			DrawablePoint element = iterator.next();
			if (element.contains(x, y)) {
				iterator.remove();
				success = true;
				break; // delete only one not several that fullfill the
						// condition
			}
		}
		return success;
	}

	/**
	 * Adds an additional {@link Drawable} to the list of {@link Drawables} that
	 * should be plotted on top of the TEM picture. the last added is on top.
	 * 
	 * @param drawable
	 *            to be added.
	 */
	public void addDrawable(Drawable drawable) {
		drawableList.add(drawable);
	}

	/**
	 * Get back reference to the {@link DrawablePicture} TEM picture
	 * 
	 * @return TEM picture
	 */
	public DrawablePicture getDrawableTEMPicture() {
		return this.drawableTEMPicture;
	}

	// /**
	// * Sets the TEM picture
	// * @param temPicture
	// */
	// public void setTemPicture(DrawablePicture temPicture) {
	// this.drawableTEMPicture = temPicture;
	// }

	/**
	 * Get reference ot he point list associated with the TEM picture
	 * 
	 * @return point list
	 */
	public ArrayList<DrawablePoint> getPointsList() {
		return pointsList;
	}

	/**
	 * Sets a complete point list
	 * 
	 * @param pointsList
	 */
	public void setPointsList(ArrayList<DrawablePoint> pointsList) {
		this.pointsList = pointsList;
	}

	/**
	 * Get the file name of the loaded tem picture.
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return this.name;
	}

	/**
	 * Get back the actual representative information associated with the TEM
	 * picture.
	 * @return information 
	 */
	public String getInformation() {
		// TODO Auto-generated method stub
		return this.information;
	}

	/**
	 * @return the icon
	 */
	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

}
