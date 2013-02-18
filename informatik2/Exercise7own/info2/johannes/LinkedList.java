package info2.johannes;

import java.util.Iterator;

public class LinkedList<E> {

	Node<E> firstNode;
	
	
	
	public LinkedList() {
	super();
	this.firstNode = null;
}

	public Node<E> getFirstNode(){		
		return firstNode;
	}
	
	public boolean isLastNodeReached(Node<E> current){
		
		if (current.getNext() == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void appendFirst(Node<E> newNode){
		if (firstNode == null){
			firstNode = newNode;
		}
		else{
			newNode.setNext(firstNode);
			firstNode.setPrev(newNode);
			firstNode = newNode;
		}
	}
	
	public void appendLast(Node<E> lastNode){
		Node<E> current = firstNode; 
		while (current.getNext() != null){
			current = current.getNext();			
		}
		if (current.getNext() == null){
			lastNode.setPrev(current);
			current.setNext(lastNode);
		}
	}
	
	public boolean isEmpty(){
		if (firstNode == null){
			return true;
		}
		else {return false;}
	}
	
	public void removeFirst(){
		Node<E> second = firstNode.getNext();
		firstNode = second;
	}
}
