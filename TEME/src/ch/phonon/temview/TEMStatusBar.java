/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * The TEMStatusBar informs the user about aspects seen in the TEMView as e.g.
 * the coordinate of the mouse pointer, the last added points.
 * 
 * @author phonon
 */
public class TEMStatusBar extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private String labelText;
	private JLabel statusLabel;
	private Point2D.Double addedPoint;
	private Point2D.Double coordinatePoint;

	/**
	 * The public standard constructor is visible throughout the application.
	 */
	public TEMStatusBar() {

		this.addedPoint = new Point2D.Double();
		this.coordinatePoint = new Point2D.Double();

		this.labelText = "Cross hairs: (X: ---------- , Y: ----------) Px";
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// TODO Handle loading of background in case of standard color ;
		// maybe we should check the getResource on a keyword like STANDARD

		// setBackground(new Color(Integer.parseInt(
		// ResourceLoader.getResource("TEMStatusBar_Color").substring(2),
		// 16)));

		statusLabel = new JLabel(this.labelText);
		statusLabel.setFont(new Font("monospaced", Font.PLAIN, 12));

		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(statusLabel);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("addPoint")) {
			this.addedPoint = (Point2D.Double) (evt.getNewValue());
		}

		if (evt.getPropertyName().equals("coordinateChange")) {
			this.coordinatePoint = (Point2D.Double) (evt.getNewValue());
		}
		// TODO: Check: Garbage collection of strings ;
		// TODO: can I round with primitives -- faster

		Double x = coordinatePoint.getX();
		Double y = coordinatePoint.getY();

		Double addedx = addedPoint.getX();
		Double addedy = addedPoint.getY();

		// TODO: Better logic ... before first addedPoint, this should give
		// no value back.

		this.labelText = String
				.format(
						
				"Cross hairs: (X: %-7d , Y: %-7d) Px -- Last added Point (X: %-7d , Y: %-7d) Px ",

				x.intValue(), y.intValue(), addedx.intValue(),
						addedy.intValue());

		this.statusLabel.setText(this.labelText);
	}

}
