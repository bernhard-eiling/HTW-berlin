package stack;
/**
 * A stack implementation, based on the implementation of a stack as an array
 * implemented with a LinkedList, but not using the standard java-implementation
 * In our handouts were some useful examples included.
 * 
 * http://www.f4.fhtw-berlin.de/~weberwu/info2/Handouts/Handout-LinkedList.doc
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 */
public class StackList implements Stack {
	private StackListItem top;

	/**
	 * Constructor of a new StackList just sets the pointer to null
	 */
	public StackList() {
		top = null;
	}
	/**
	 * Writes the given object into the stack
	 */
	public void push(Object o) throws StackOverflow {
		StackListItem newConnect = new StackListItem(o);
		newConnect.next = top;
		top = newConnect;
	}

	/**
	 * Pops out the youngest element out of the stack
	 * if the stack is empty a StackUnderflow is thrown
	 * @throws StackUnderflow if stack is empty
	 */
	public void pop () throws StackUnderflow {
		if(top!=null) {
			top = top.next;
		} else throw new StackUnderflow ("Stack underflow");
	}

	/**
	 * Return the youngest element out of the stack and delete it from the stack
	 * @return The youngest element at the stack
	 * @throws StackUnderflow If the stack is empty the StackUnderflow will be thrown
	 */
	public Object top () throws StackUnderflow  {
		if(!isEmpty()) {
			Object output = top.data;
			pop();
			return output;
		} else throw new StackUnderflow ("Stack underflow");
	}
	
	/**
	 * Return the youngest element out of the stack
	 * @return The youngest element at the stack
	 * @throws StackUnderflow If the stack is empty the StackUnderflow will be thrown
	 */
	public Object topWpop () throws StackUnderflow  {
		if(!isEmpty()) {
			Object output = top.data;
			return output;
		} else throw new StackUnderflow ("Stack underflow");
	}

	/**
	 * Return the data of the object
	 * @return int The data of the object as an integer-value
	 */
	public Integer topInt () throws StackUnderflow  {
		if(!isEmpty()) {
			Integer output = (Integer) top.data; //Integer.valueOf(top.data.toString()) ;
			pop();
			return output;
		} else throw new StackUnderflow ("Stack underflow");
	}

	/**
	 * Checks if the stack is empty
	 * @return true of false, depending on the stack
	 */
	public boolean isEmpty () {
		return (top==null);
	}

	/**
	 * Cleans up the stack
	 */
	public void Empty () {
		top = null;
	}

	/**
	 * A toString-method for our stack, to check if all is fine
	 * @return s String in the form Top-> o1:o2:o3 <-Bottom 
	 */
	public String toString () {
		String s = "Top-> ";
		StackListItem current = top;
		while(current != null) {
			s = s + current.displayObject();
			current = current.next;
		}
		s = s + " <-Bottom";
		return s;
	}

	/**
	 * Just a call of the toString()-method
	 * @return A String which is the output of the toString-Method
	 */
	public String print() {
		return toString();
	}
}
