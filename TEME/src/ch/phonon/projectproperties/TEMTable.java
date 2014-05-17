/**
 * 
 */
package ch.phonon.projectproperties;

import java.util.ArrayList;
import java.util.List;

import ch.phonon.TEMAllied;

import javax.swing.table.AbstractTableModel;


/**
 * @author phonon
 *
 */


public class TEMTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	List<TEMAllied> listOfTEMAllied;
	
	public TEMTable() {
		super();
		this.listOfTEMAllied = new ArrayList<TEMAllied>();

		
	}
	
	@Override
	public int getRowCount() {
		return listOfTEMAllied.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		//System.out.println(columnIndex);
		
		
		
		Object obj = listOfTEMAllied.get(rowIndex);
		
		
		//TODO: How to obtain elements ?
		
		
        Object value = null;
        switch (columnIndex) {
            case 0:
            	
                value = "col1";
                break;
            case 1:
                value =  ((TEMAllied)obj).getFileName();
                break;
            case 2:
                value =  "col3";
                break;
            case 3:
                value =  "col4";
                break;

        }
        return value;
	}

	@Override
	public String getColumnName(int column) {
	
		String value = null;
		switch (column) {
        case 0:
        	//TODO: value = temAllied.getName aso
            value = "Thumbnail";
            break;
        case 1:
            value =  "Filename";
            break;
        case 2:
            value = "Size";
            break;
        case 3:
            value =  "Number of associated polygons";
            break;

    }
		return value;
	}
	
	public void add (TEMAllied temAllied) {
		this.listOfTEMAllied.add(temAllied);
	}
	


}
