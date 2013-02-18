package rpn;

public class Node<E> {
	private Node<E> nextNode;
	private Node<E> lastNode;
	private E object;

	public Node(E obj) {
		this.object = obj;
	}

	public E getObject() {
		return object;
	}

	public Node<E> getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node<E> nextNode) {
		this.nextNode = nextNode;
	}

	public Node<E> getLastNode() {
		return lastNode;
	}

	public void setLastNode(Node<E> lastNode) {
		this.lastNode = lastNode;
	}

	public Node<E> getFrontNode() {
		if (this.getLastNode() == null) {
			return this;
		} else {
			return getLastNode().getFrontNode();
		}
	}
}