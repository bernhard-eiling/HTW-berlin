package eiling.info2.exe6;

public class Stack<L> {
	
	private Element<L> top = null;
	
	Stack() {
		
	}

	public void push(L load) {
		top = new Element<L>(load, top);
	}
	
	public L pop() {
		try {
			Element<L> returnElement = top;
			top = top.getNext();
			return returnElement.getLoad();
		} catch (Exception e) {
			System.err.println("Stack is empty.");
			e.printStackTrace();
		}
		return null;
	}
	
	public L peek() {
		return top.getLoad();
	}
	
	public boolean isEmpty() {
		if (top == null) {
			return true;
		} else {
			return false;
		}
	}
}
