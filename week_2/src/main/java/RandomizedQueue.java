import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int numberOfItems = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfItems;
    }

    private void resize(int capacity) {

        Item[] temp = (Item[]) new Object[capacity];

        System.arraycopy(items, 0, temp, 0, numberOfItems);
        items = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (items.length == numberOfItems) {
            resize(numberOfItems * 2);
        }

        items[numberOfItems] = item;
        numberOfItems++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int indexToGet = StdRandom.uniform(numberOfItems);
        Item item = items[indexToGet];
        items[indexToGet] = items[numberOfItems - 1];
        items[numberOfItems - 1] = null;
        numberOfItems--;

        if (numberOfItems > 0 && numberOfItems == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }



    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(numberOfItems)];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private final RandomizedQueue<Item> itemsToIterate;

        private ListIterator() {
            itemsToIterate = new RandomizedQueue<>();

            for (int i = 0; i < numberOfItems; i++) {
                itemsToIterate.enqueue(items[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !itemsToIterate.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return itemsToIterate.dequeue();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        rQueue.enqueue("zzz");
    }
}
