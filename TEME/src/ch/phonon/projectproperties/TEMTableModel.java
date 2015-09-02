/*******************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 ******************************************************************************/

// TODO Add functionality, that a chosen TEM picture in the table is chosen
// in the TEMView as well.
// TODO Add functionality to remove all TEMAllieds
// TODO Add functionality to remove a certain marked list of TEMAllieds

package ch.phonon.projectproperties;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import ch.phonon.LocalOrientation;
import ch.phonon.ResourceLoader;
import ch.phonon.StarPoint;
import ch.phonon.TEMAllied;
import ch.phonon.drawables.DrawableCoordinateSystem;
import ch.phonon.drawables.DrawablePicture;

/**
 * The class logically spans a table, that gives an overview about the loaded
 * TEMs and their associated information. The {@link TEMTableModel} contains a
 * list of {@link TEMAllied} that can be modified by add and remove commands.
 * Because the class is a subclass of {@link TableModel}, it can be used by a
 * {@link JTable} view. Furthermore the class allows to cycle through the
 * {@link TEMAllied} items of the list.
 * 
 * @author phonon
 * 
 */
public class TEMTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	List<TEMAllied> listOfTEMAllied;
	TEMAllied standardAllied = new TEMAllied();

	private int countOfTemAllieds = 0;
	private int activeTemAllied = 0;

	/**
	 * Once the {@link TEMTableModel} is created the list of {@link TEMAllied}s
	 * is initialized. A standard TEMAllied is created to be exposed to the
	 * user, when no TEM pictures had been loaded.
	 */
	public TEMTableModel() {
		super();
		this.listOfTEMAllied = new ArrayList<TEMAllied>();

		BufferedImage logo = null;

		/** Load the TEME Logo into the standarAllied */
		try {
			logo = ResourceLoader.getBufferedImage("logo.png");
		} catch (IOException ex) {
			System.out.println("Exception: The logo cannot be loaded");
			System.exit(0);
		}
		DrawablePicture temLogo = new DrawablePicture(new StarPoint(),
				new LocalOrientation(), logo);
		this.standardAllied.addDrawable(temLogo);

		DrawableCoordinateSystem cS = new DrawableCoordinateSystem(
				new StarPoint(), (double) 1000, (double) 1000);
		this.standardAllied.addDrawable(cS);

	}

	// -------------------- Overridden methods from TableModel ----------------

	@Override
	public int getRowCount() {
		return listOfTEMAllied.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object obj = listOfTEMAllied.get(rowIndex);

		Object value = null;
		switch (columnIndex) {
		case 0:
			value = ((TEMAllied) obj).getIcon();
			break;
		case 1:
			value = ((TEMAllied) obj).getFileName();
			break;
		case 2:
			BufferedImage image = ((TEMAllied) obj).getDrawableTEMPicture()
					.getBufferedImage();
			int width = image.getWidth();
			int height = image.getHeight();

			value = width + " x " + height + " Pixels";
			break;
		case 3:
			value = ((TEMAllied) obj).delegateScales().size();
			break;
		case 4:
			value = ((TEMAllied) obj).getPointsList().size();
			break;

		}

		return value;
	}

	@Override
	public String getColumnName(int column) {

		String value = null;
		switch (column) {
		case 0:
			// TODO: value = temAllied.getName aso
			value = "Thumbnail";
			break;
		case 1:
			value = "Filename";
			break;
		case 2:
			value = "Size";
			break;
		case 3:
			value = "Number of associated scales";
			break;
		case 4:
			value = "Number of points added";
			break;
		}

		return value;
	}

	// ------------------------ Data manipulation -----------------------------

	/**
	 * This function allows to remove items based on the indices stored in the
	 * {@link TreeSet} the reflect the chosen items in the TemTable.
	 * 
	 * @param setOfIndicesToRemove
	 *            indices of the items to remove
	 */
	public void removeTEMAllieds(TreeSet<Integer> setOfIndicesToRemove) {

		this.countOfTemAllieds =
				this.countOfTemAllieds - setOfIndicesToRemove.size();

		for (int i : setOfIndicesToRemove.descendingSet()) {
			this.listOfTEMAllied.remove(i);
		}

		this.activeTemAllied = this.listOfTEMAllied.size();

	}

	/**
	 * Allows to add a new {@link TEMAllied} to the end of the list
	 * 
	 * @param temAllied
	 */
	public void add(TEMAllied temAllied) {
		this.countOfTemAllieds++;
		this.listOfTEMAllied.add(temAllied);
		this.activeTemAllied = this.countOfTemAllieds;
	}

	// ------------------------ Inspection ------------------------------------

	/**
	 * Give back the active temAllied
	 * 
	 * @return active temAllied
	 */
	public TEMAllied getActiveItem() {

		// TODO: We need here to better model ... let us
		// check if this is a reasonable way to treat
		// the case of no TEMAllied existing.
		// The best would be to have always a dummy
		// already initialized maybe with a typical
		// background image ... than we also do not
		// need to construct a new one. Empty is also
		// a state of the model and should be treated correctly.
		System.out.println("Hallo");

		System.out.println(this.activeTemAllied);

		if (this.activeTemAllied == 0) {

			return this.standardAllied;
		}

		return this.listOfTEMAllied.get(this.activeTemAllied - 1);
	}

	/**
	 * Set the active temAllied by index and get back the corresponding active
	 * temAllied value.
	 * 
	 * @param index
	 *            of the loaded temAllied in the table
	 * @throws IndexOutOfBoundsException
	 * @return chosen and activated temAllied
	 */
	public TEMAllied setActiveItemByIndex(int index) {
		if (index > this.countOfTemAllieds || index < 1) {
			throw new IndexOutOfBoundsException();
		}
		// System.out.println("Active Index");
		this.activeTemAllied = index;
		return this.listOfTEMAllied.get(this.activeTemAllied - 1);
	}

	/**
	 * Get the next TEMAllied item relative to the active one in the list
	 * 
	 * @return next {@link TEMAllied}
	 */
	public TEMAllied getForwardItem() {

		// TODO: I think, the logic is not correct. Need to be checked again
		// Maybe it would be better to handle here an exception ...

		if (this.countOfTemAllieds == 0)
			return null;

		this.activeTemAllied = this.activeTemAllied + 1;
		if (this.countOfTemAllieds > 0
				&& this.activeTemAllied > this.countOfTemAllieds) {
			this.activeTemAllied = 1;
		}

		return this.listOfTEMAllied.get(this.activeTemAllied - 1);

	}

	/**
	 * Get the previous TEMAllied item relative to the active one in the list
	 * 
	 * @return previous {@link TEMAllied}
	 */
	public TEMAllied getBackwardItem() {

		if (this.countOfTemAllieds == 0)
			return null;

		// TODO: I think, the logic is not correct. Need to be checked again

		this.activeTemAllied = this.activeTemAllied - 1;
		if (this.activeTemAllied == 0) {
			this.activeTemAllied = this.countOfTemAllieds;
		}
		return this.listOfTEMAllied.get(this.activeTemAllied - 1);
	}

	/**
	 * Get the last TEMAllied item in the list
	 * 
	 * @return last {@link TEMAllied}
	 */
	public TEMAllied getLastItem() {

		if (this.countOfTemAllieds == 0)
			return null;
		return this.listOfTEMAllied.get(this.countOfTemAllieds - 1);
	}

	/**
	 * Get the first TEMAllied item in the list
	 * 
	 * @return first {@link TEMAllied}
	 */
	public TEMAllied getFirstItem() {
		if (this.countOfTemAllieds == 0)
			return null;
		return this.listOfTEMAllied.get(0);
	}

}
