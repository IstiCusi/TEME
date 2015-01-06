
package ch.phonon.temview;

public enum TEMEditType {
	
	POINT, SCALE ;
	
	public TEMEditType getNext() {
		return values()[(ordinal()+1) % values().length];
	}
	
	public TEMEditType getPrevious() {
		return values()[(this.ordinal() + values().length -1) % values().length];
	}
	
}