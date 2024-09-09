//-----------------------------------------------------
// Title: Bag Class
// Author: Ahmet Can Öztürk
// Assignment: 1
// Description: This class is an implementation of the Bag structure; in the Graph structure, I hold the adj-list by bag instead of the default array.
//-----------------------------------------------------
import java.util.Iterator;

public class Bag<Integer> implements Iterable<Integer> {

	private Node first; // first node in list

	private class Node {
		Integer item;
		Node next;
	}

	public void add(Integer item) { // same as push() in Stack
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
	}

	public Iterator<Integer> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Integer> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Integer next() {
			Integer item = current.item;
			current = current.next;
			return item;
		}
	}
}
