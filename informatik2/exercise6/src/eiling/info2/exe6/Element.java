package eiling.info2.exe6;

public class Element<L> {
	
	private L load;
	private Element<L> next;
	
	Element (L load, Element<L> next) {
		this.load = load;
		this.next = next;
	}

	public L getLoad() {
		return this.load;
	}
	
	public Element<L> getNext() {
		return this.next;
	}
}
