package com.weifuchow.learn;
/**
 * 
 */
public class Node {
   
    String value;
    Node last;
    Node next;
    
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Node getLast() {
		return last;
	}
	public void setLast(Node last) {
		this.last = last;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}

	
	public Node(String value, Node last, Node next) {
		this.value = value;
		this.last = last;
		this.next = next;
	}
	public Node(String value) {
		this.value = value;
	}
	public Node(String value, Node next) {
		this.value = value;
		this.next = next;
	}
	@Override
	public String toString() {
		return "Node [last=" + last + ", next=" + next + ", value=" + value + "]";
	}
	

	


}