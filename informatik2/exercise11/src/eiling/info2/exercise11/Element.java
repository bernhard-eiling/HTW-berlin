package eiling.info2.exercise11;

public class Element {

	String value;
	String key;
	Element next;
	
	Element(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	String getValue() {
		return value;
	}
	
	String getKey() {
		return key;
	}
	
	Element getNext() {
		return next;
	}
}
