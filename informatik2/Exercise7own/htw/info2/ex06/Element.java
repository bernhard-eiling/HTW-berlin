package htw.info2.ex06;

/**
 * Stack element containing a type of data and a pointer to the next element, if existent
 * 
 * @author Alexander Hinrichs, Konstantin Vogel, Markus Bausdorf
 *
 * @param <E> data type for the element
 */
public class Element<E> {
	public E obj;
	public Element<E> pointer;

	/**
	 * initialization of the element, by setting its value and its pointer
	 * 
	 * @param str data to add
	 * @param e pointer to next element
	 */
	public Element(E str, Element<E> e) {
		this.obj = str;
		this.pointer = e;
	}

	/**
	 * set the value of the current element
	 * 
	 * @param o new data
	 */
	public void setData(E o) {
		this.obj = o;
	}

	/**
	 * set the pointer of the current element
	 * 
	 * @param e new pointer
	 */
	public void setPointer(Element<E> e) {
		this.pointer = e;
	}

	/**
	 * 
	 * @return value of the element as a string
	 */
	public String getData() {
		return this.obj.toString();
	}

	/**
	 * 
	 * @return next element
	 */
	public Element<E> getNext() {
		return this.pointer;
	}
	
	/**
	 * checks if an element has a successor
	 * 
	 * @return true or false if successor
	 */
	public Boolean hasNext() {
		if (this.pointer == null) return false;
		return true;
	}
}
