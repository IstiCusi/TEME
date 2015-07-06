/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import java.util.EnumMap;

import javax.sound.sampled.AudioInputStream;

/**
 * This enumeration lists all representative standard sounds that are expected
 * to be loadable from the tar file. Each element of this enumeration is
 * representing one sound type associated with either an action or response
 * caused by a system failure to complete an action. The SoundType enum is used
 * in an {@link EnumMap} to store sound {@link AudioInputStream}s. All
 * corresponding wave files that are garanted by contract to be loaded need to
 * be referenced in the resourceBundle.properties file in the "Default Sounds"
 * section. The physical sound files are placed in the sounds subfolder of the
 * tar file.
 * 
 * <p>
 * <p>
 * The following standard sounds are registered and expected to be loaded by
 * default:
 * 
 * <p>
 * 
 * <ul>
 * <li>
 * ERROR Error sound to be played whenever a action cannot be performed
 * <li>
 * KILL Killing sound to be played when an object is deleted (e.g. a point)
 * <li>
 * PAGETURN Turning a page / canvas / changing a view
 * <li>
 * POP Pop sound to emphasize the generation of an object (e.g. a point)
 * <li>
 * WELCOME Welcome sound of the application (signature tune)
 * <li>
 * TICK Tick sound used to emphasize finishing of a task (e.g. loading a file)
 * </ul>
 * 
 * @author phonon
 * 
 */

public enum SoundType {

	/**
	 * Error sound to be played whenever a action cannot be performed
	 */
	ERROR,
	/**
	 * Killing sound to be played when an object is deleted 
	 * (for example a point)
	 */
	KILL,
	/**
	 * Turning a page / canvas / changing a view
	 */
	PAGETURN,
	/**
	 * Pop sound to emphasize the generation of an object (for example a point)
	 */
	POP,
	/**
	 * Welcome sound of the application (signature tune)
	 */
	WELCOME,
	/**
	 * Tick sound used to emphasize finishing of a task 
	 * (for example loading a file)
	 */
	TICK
}