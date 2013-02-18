package htw.info2.ex06;

/**
 * Simple stack, without using linked lists or the Java stack
 * 
 * @author Alexander Hinrichs, Konstantin Vogel, Markus Bausdorf
 *
 * @param <E> data type for the stack
 */

public class Stack<E> {
	public Element<E> head = new Element<E>(null, null);
	Boolean hasHead = false;

	/**
	 * Adds an element to the stack by putting it on top
	 * 
	 * @param str Element to be pushed in the string
	 */
	public void push(E str) {
		if (hasHead) {
			Element<E> tmp = new Element<E>(str, head);
			head = tmp;
		} else {
			head.setData(str);
			head.setPointer(null);
			hasHead = true;
		}
	}

	/**
	 * returns the top-element and removes it from the stack
	 * 
	 * @return current top-element
	 */
	public String pop() throws Underflow {
		String retVal = "";
		
		if(!isEmpty()) {
		
		retVal = head.getData();
		if (head.getNext() == null) {
			head.setData(null);
			hasHead = false;
		} else {
			head = head.getNext();
		}
		} else {
			throw new Underflow("Underflow Exception");
		}
		return retVal;
	}
	
	/**
	 * returns the top element without removing it from the stack
	 * 
	 * @return current top-element
	 */
	public String top() {
		return head.getData();
	}

	public Boolean isEmpty() {
		if (!hasHead)
			return true;
		return false;
	}
	
	/**
	 * completely empties the stack
	 */
	public void clearStack() {
		head.setPointer(null);
		head.setData(null);
	}

	/**
	 * returns all values stored in the stack formatted as a string
	 */
	@Override
	public String toString() {
		String result = "";
		Element<E> current = head;
		if(!hasHead) return("Stack is empty!");
		do{
			result += current.getData() + ", ";
			current = current.getNext();
		} while (current.hasNext()); 
		result += current.getData();
		return result;
	}
}
