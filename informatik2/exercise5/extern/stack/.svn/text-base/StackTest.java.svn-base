package stack;
/**
 * Just for testing if the stacks behave in the same way
 */
public class StackTest {

	/**
	 * Today a main, for a game? ;)
	 * 
	 * @param args
	 * @throws StackUnderflow 
	 * @throws StackOverflow 
	 */
	public static void main(String[] args) throws StackUnderflow, StackOverflow {

		StackArray ArrayStack = new StackArray(5);
		ArrayStack.push("1");
		ArrayStack.push("+");
		Object object = ArrayStack.top();
		ArrayStack.push("2");
		ArrayStack.push("3");
		System.out.println(ArrayStack.print());
		
		StackList ListStack = new StackList();
		ListStack.push("1");
		System.out.println(ListStack.topInt());
		ListStack.push("+");
		ListStack.push("2");
		ListStack.push("3");
		System.out.println(ListStack.print());
	}
}
