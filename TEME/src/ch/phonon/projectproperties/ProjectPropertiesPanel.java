
package ch.phonon.projectproperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;

import ch.phonon.Application;
import ch.phonon.TEMAllied;
import ch.phonon.temview.TEMView;


public class ProjectPropertiesPanel extends JPanel implements ActionListener  {

	private static final long serialVersionUID = 1L;
	private JButton 		openButton; 
	private JFileChooser 	temFileChooser;
	
	private TEMTableModel 	temTableModel;
	
	private TEMAllied 		temAllied;
	private BufferedImage 	image;
	
	//private JTable temTable;
	private JButton 		removeButton;
	private JPanel 			upperPanel;
	private JButton 		clearButton;
	private JButton 		copyButton;
	private JPanel 			middlePanel;
	private JPanel 			bottomPanel;
	private JTable 			temTable;


	
	public ProjectPropertiesPanel() {
		
		
// ------------------------- Upper Panel --------------------------------------		
		
		upperPanel = new JPanel();
		
		JTextField projectName 		= new JTextField(20);
		JTextField projectId   		= new JTextField(20);
		JTextField technology  		= new JTextField(20);
		
		JLabel	projectNameLabel 	= new JLabel("Project Name:", JLabel.RIGHT);
		JLabel	projectIdLabel		= new JLabel("Project Id  :", JLabel.RIGHT);
		JLabel	technologyLabel		= new JLabel("Technology  :", JLabel.RIGHT);
		
		projectNameLabel.setDisplayedMnemonic('N');
		projectIdLabel.setDisplayedMnemonic('I');
		technologyLabel.setDisplayedMnemonic('T');
		
		projectNameLabel.setLabelFor(projectName);
		projectIdLabel.setLabelFor(projectId);
		technologyLabel.setLabelFor(technology);
		
		upperPanel.add(projectNameLabel);
		upperPanel.add(projectName);
		
		upperPanel.add(projectIdLabel);
		upperPanel.add(projectId);
		
		upperPanel.add(technologyLabel);
		upperPanel.add (technology);
			
// ------------------- Center table --------------------------------------------
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		this.temTableModel = new TEMTableModel();
		
			this.temTable = new JTable(this.temTableModel) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int datas, int columns) {
				return false;
			}
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				
				//System.out.println("I am here");
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
			
			
			
		};
		//temTable.setPreferredScrollableViewportSize(new Dimension(1000,1000));
		temTable.setFillsViewportHeight(true);
		temTable.setRowHeight(100);
		
		//temTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		//temTable.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane(temTable);
		
		//middlePanel.add(temTable);
		middlePanel.add(scrollPane,BorderLayout.CENTER);
		
		
		

// ------------------------- bottom panel---------------------------------------
		
		bottomPanel = new JPanel();
		
		URL url =   Application.getUrl("pics/Open16.gif");
		openButton = new JButton("Open a TEM picture ...", new ImageIcon(url));
		openButton.addActionListener(this);
		
		
		temFileChooser = new JFileChooser();
		temFileChooser.addChoosableFileFilter
			(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
		temFileChooser.setAcceptAllFileFilterUsed(false);
		temFileChooser.setCurrentDirectory(new File(Application.getResource("PictureFolder").toString()));
		
		removeButton = new JButton("Remove TEM picture(s) ...");
		
		copyButton = new JButton("Duplicate TEM picture(s) ...");

		clearButton = new JButton("Clear all TEM pictures ...");
		
		LayoutManager flowLayout = new FlowLayout(3,10,10);		
		
		bottomPanel.setLayout(flowLayout);		
		bottomPanel.add(openButton);
		bottomPanel.add(removeButton);
		bottomPanel.add(copyButton);
		bottomPanel.add(clearButton);
		
// -----------------------------------------------------------------------------
		
		LayoutManager borderLayout = new BorderLayout();
		setLayout(borderLayout);
		add(upperPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);
		
		
		// TODO: Add here a table with TEM pictures (that you can load with New, Clear, Delete, Copy .. )
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == openButton) {
	            int returnVal = temFileChooser.showOpenDialog(this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = temFileChooser.getSelectedFile();    
	                System.out.println(file.getAbsolutePath());
	                
	                try {                
	        			this.image = ImageIO.read(file);
	        		} catch (IOException ex) {
	        			//TODO Add here a meaningful exception handling
	        			System.out.println("File not found");
	        			System.exit(0);
	        		}
	                this.temAllied = new TEMAllied (image, file.getName());
	                this.temTableModel.add(this.temAllied);
	                //updateRowHeights();
	                this.temTableModel.fireTableDataChanged();
	                
	                //firePropertyChange("temAlliedChange", null, this.temAllied);
	                firePropertyChange("temTableModelChange", null, this.temTableModel);
	                System.out.println("Opening: " + file.getName() + ".");
	            } else {
	            	System.out.println("Canceled");
	            }
		 }
	}

	
	// TODO: Instead we could try t implemento registerTEMInspectionPanel and
	// shift the TEMRegistration into the ProjectPropertiesPanel class.
	// Seems to be a little bit better ... but I miss the feeling of MVC. 
	// HOW ? I need help with this point.
	
	public void registerTEMView (TEMView temView) {
		this.addPropertyChangeListener("temAlliedChange", 		(PropertyChangeListener) temView);
		this.addPropertyChangeListener("temTableModelChange", 	(PropertyChangeListener) temView);
	}
	
	
	private void updateRowHeights()
	{
	    try
	    {
	        for (int row = 0; row < temTable.getRowCount(); row++)
	        {
	            int rowHeight = temTable.getRowHeight();

	            for (int column = 0; column < temTable.getColumnCount(); column++)
	            {
	                Component comp = temTable.prepareRenderer(temTable.getCellRenderer(row, column), row, column);
	                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
	            }

	            temTable.setRowHeight(row, rowHeight);
	        }
	    }
	    catch(ClassCastException e) {}
	}
	
	
}
