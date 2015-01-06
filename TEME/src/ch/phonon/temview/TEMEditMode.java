/**
 * 
 */
package ch.phonon.temview;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

import ch.phonon.ResourceLoader;

/**
 * @author phonon
 *
 */
public class TEMEditMode {

	private EnumMap<TEMEditType, Cursor> cursors = new EnumMap<TEMEditType, Cursor>(TEMEditType.class);
	private TEMEditType activeType;
	
	TEMEditMode () {
		
		try {

			this.activeType = TEMEditType.POINT;
			
			BufferedImage curBufferedImage = ResourceLoader.getBufferedImage("Cross.gif");
			Cursor bufferedCursor = Toolkit.getDefaultToolkit()
					.createCustomCursor(curBufferedImage, new Point(32, 32),
							"Cross");
			this.cursors.put(TEMEditType.POINT, bufferedCursor);

			curBufferedImage = ResourceLoader.getBufferedImage("Cross_Scale.png");
			bufferedCursor = Toolkit.getDefaultToolkit()
					.createCustomCursor(curBufferedImage, new Point(32, 32),
							"Scale");
			this.cursors.put(TEMEditType.SCALE, bufferedCursor);

		} catch (IOException e) {
			System.out.println("Exception: Standard Cursors can not be loaded");
			e.printStackTrace();
		}
				
	}
	
	public TEMEditType getActiveEditType() {
		return activeType;
	}
	
	public TEMEditType cycleToNextEditType() {
		activeType = activeType.getNext();
		return activeType;
	}

	public TEMEditType cycleToPreviousEditType() {
		activeType = activeType.getPrevious();
		return activeType;
	}
	public void switchToActiveEditTyp (TEMEditType type) {
		this.activeType = type;
	}

	public Cursor getActiveCursor() {
		return cursors.get(activeType);
	}
	
		
}
