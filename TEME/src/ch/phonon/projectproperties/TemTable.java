package ch.phonon.projectproperties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ch.phonon.ResourceLoader;
import ch.phonon.Sound;
import ch.phonon.SoundType;
import ch.phonon.TEMAllied;
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
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

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
	 * 
	 */
	public void configureTable() {

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = rowAtPoint(evt.getPoint());
				// int col = temTable.columnAtPoint(evt.getPoint());
				self.temTableModel.setActiveItemByIndex(row + 1);
				firePropertyChange("newSelectionInTable", null, self.temTableModel);
				new Thread(new Sound(SoundType.TICK)).start();
			}

		});

		int fontSize = Integer.valueOf(ResourceLoader.getResource("TEMTable_FontSize"));
		setFont(new Font("Arial", Font.BOLD, fontSize));

		Color fontColor = new Color(
				Integer.parseInt(ResourceLoader.getResource("TEMViewSwitch_Color").substring(2), 16));

		setForeground(fontColor);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 1; i < getColumnModel().getColumnCount(); i++) {
			getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		setFillsViewportHeight(true);
		setRowHeight(100);

	}

	/**
	 * Registers the TEMView as listener to property changes
	 * The following events are delivered to the registered 
	 * {@link TEMView}: 
	 * 
	 * 
	 * @param temView
	 */
	public void registerTEMView(TEMView temView) {
		this.addPropertyChangeListener("newSelectionInTable", (PropertyChangeListener) temView);

	}

}