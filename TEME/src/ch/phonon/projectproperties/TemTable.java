/*******************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 ******************************************************************************/

package ch.phonon.projectproperties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeListener;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ch.phonon.ResourceLoader;
import ch.phonon.soundutils.Sound;
import ch.phonon.soundutils.SoundType;
import ch.phonon.temallied.TEMAllied;
import ch.phonon.temview.TEMView;

/**
 * 
 * This {@link JTable} is the central view onto the existing {@link TEMAllied}
 * items. The table allows to register event
 * 
 * @author phonon
 *
 */
public final class TemTable extends JTable {

	private TemTable self = this;
	private static final long serialVersionUID = 1L;
	private TEMTableModel temTableModel;
	private TreeSet<Integer> setOfSelectedRows = new TreeSet<>();

	/**
	 * Get the indices set of selected rows in the TemTable.
	 * 
	 * @return set of selected indices
	 */
	public TreeSet<Integer> getSetOfSelectedRows() {
		return setOfSelectedRows;
	}

	/**
	 * This constructor builds the complete table with all his features.
	 * 
	 * @param temTableModel
	 */
	public TemTable(TEMTableModel temTableModel) {
		super(temTableModel);
		this.temTableModel = temTableModel;
		// configureTable();

	}

	@Override
	public boolean isCellEditable(int datas, int columns) {
		return false;
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {

		Component table = super.prepareRenderer(renderer, row, column);

		if (row % 2 == 0) {
			table.setBackground(Color.WHITE);
		} else {
			table.setBackground(Color.LIGHT_GRAY);
		}

		if (isCellSelected(row, column)) {
			table.setBackground(Color.GREEN);

		}
		return table;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		// TODO Auto-generated method stub
		return getValueAt(0, column).getClass();
	}

	/**
	 * This public method summarizes a very specific implementation of this
	 * class. This is done for simplicity at the moment and should later be
	 * brought in a much better design.
	 */
	public void configureTable() {

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = rowAtPoint(evt.getPoint());
				// int col = temTable.columnAtPoint(evt.getPoint());
				if (row >= 0) {
					self.temTableModel.setActiveItemByIndex(row + 1);
					firePropertyChange("newSelectionInTable", null,
							self.temTableModel);
					new Thread(new Sound(SoundType.TICK)).start();
				}
			}

		});

		int fontSize = Integer
				.valueOf(ResourceLoader.getResource("TEMTable_FontSize"));
		setFont(new Font("Arial", Font.BOLD, fontSize));

		Color fontColor = new Color(Integer.parseInt(
				ResourceLoader.getResource("TEMViewSwitch_Color").substring(2),
				16));

		setForeground(fontColor);

		DefaultTableCellRenderer centerRenderer =
				new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 1; i < getColumnModel().getColumnCount(); i++) {
			getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		setFillsViewportHeight(true);
		setRowHeight(100);

		ListSelectionModel selectionModel = this.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				// TODO For some reason ... any state change of
				// selection leads to two times running this method.

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				setOfSelectedRows.clear();

				int minIndex = lsm.getMinSelectionIndex();
				int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {
						setOfSelectedRows.add(i);
					}
				}
				// System.out.println(setOfSelectedRows.toString());
			}
		});

	}

	/**
	 * Registers the TEMView as listener to property changes The following
	 * events are delivered to the registered {@link TEMView}:
	 * 
	 * 
	 * @param temView
	 */
	public void registerTEMView(TEMView temView) {
		this.addPropertyChangeListener("newSelectionInTable",
				(PropertyChangeListener) temView);

	}

}