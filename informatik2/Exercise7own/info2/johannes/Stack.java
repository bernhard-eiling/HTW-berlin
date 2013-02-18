package info2.johannes;

import java.io.IOException;
import java.util.ArrayList;



public class Stack<E> {
	
	private LinkedList a;
	private ArrayList<E> stackToPrint = new ArrayList();
	

	public Stack(){
		a = new LinkedList();
	}

	public void push(E o){		
		Node n = new Node(o);
		a.appendFirst(n);
		stackToPrint.add(o);
	}
	
	public void pop() throws NullPointerException {	
		
		try{	
		a.removeFirst();
		stackToPrint.remove(stackToPrint.size()-1);
		}
		catch (NullPointerException e){
			System.out.println("Stack underflow! The Stack is empty and you tried to pop!");
		}
	}

	
	public E top(){
		Node<E> n = a.getFirstNode();
		return n.getData();
	}
	
	public boolean isEmpty(){
		return a.isEmpty();
	}
	
	public String toString(){	
		String result = "";
		for (E a : stackToPrint){
			result += a.toString() + "\n";
		}		
		return result;
	}
	

}