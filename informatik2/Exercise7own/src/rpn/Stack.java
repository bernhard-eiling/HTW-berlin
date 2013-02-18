package rpn;
public class Stack <E> {
 private Node<E> current;
public Stack () {
this.current = null;
}
public void push (E obj) {
Node<E> newNode = new Node<E> (obj);
if (current != null) {
this.current.setNextNode(newNode);
newNode.setLastNode(this.current);
}
this.current = newNode;
}

public E top (){
if (current != null) {
return current.getObject();
}
else {
return null;
}
}
public void pop () throws StackUnderflowException {
if (this.current != null) {
this.current = this.current.getLastNode();
}
else {
throw new StackUnderflowException();
}
}
public boolean isEmpty() {
return (this.current == null);
}
public String toString() {
 Node n = current;
String str = "";
int count = 0;
while (n != null) {
str = str + count +": "+ n.getObject().toString() +"\n";
n = n.getLastNode();
count++;
}
str = "Stack content: \n"+str;
return str;
}
}