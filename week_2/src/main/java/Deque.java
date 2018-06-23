import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    private class Node {

        Item item;
        Node next;
        Node prev;

        Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("zzz");
        deque.removeFirst();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            first = new Node(item);
            last  = first;
        } else {
            Node node = new Node(item);
            node.next = first;
            first.prev = node;
            first = node;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            last  = new Node(item);
            first = last;
        } else {
            Node node = new Node(item);
            node.prev = last;
            last.next = node;
            last = node;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        if (size() == 1) {
            first.item = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;

        if (size() == 1) {
            last.item = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node currentNode = isEmpty() ? null : first;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }
    }
}





