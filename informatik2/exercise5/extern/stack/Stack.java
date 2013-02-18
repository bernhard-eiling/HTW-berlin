package stack;
/**
 * An interface for stack implementation, based on the reading materials from Prof. Dr. Debora Weber-Wulff
 * 
 * http://www.f4.fhtw-berlin.de/~weberwu/info2/Handouts/StackImpl.java
 * @author Tobias Preuss (s0516424) & Niels Richter (s0517512)
 *
 */
public interface Stack {
	/**
	 * Push an object into the stack
	 * 
	 * @param o Object to push into the stack
	 */
	public void push (Object o) throws StackOverflow;
	
	/**
	 * Pop an object out of the stack
	 * 
	 * @throws StackUnderflow If stack is empty
	 */
	public void pop () throws StackUnderflow;
	
	/**
	 * Return the latest element out of the stack
	 * 
	 * @return Object
	 * @throws StackUnderflow If stack is empty
	 */
	public Object top () throws StackUnderflow;
	
	/**
	 * Checks if the stack is empty
	 * 
	 * @return Boolean
	 */
	public boolean isEmpty ();
	
	/**
	 * Emptys the stack for reinitialization
	 */
	public void Empty ();
	
	/**
	 * Generates a string to print the stack out
	 * 
	 * @return String
	 */
	public String print();
}
