import java.util.Iterator;

public class TestRandomizedQueue {

    public static void main(String[] args) {

        testSingle();
        testMany();
        testIterator();
    }

    private static void testIterator() {
        System.out.println("testIterator");
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();

        rQueue.enqueue(10);
        rQueue.enqueue(20);
        rQueue.enqueue(30);
        rQueue.enqueue(40);
        rQueue.enqueue(50);

        Iterator<Integer> iterator = rQueue.iterator();
        assert iterator.hasNext();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

        private static void testMany() {
        System.out.println("testMany");
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();

        rQueue.enqueue(10);
        rQueue.enqueue(20);
        rQueue.enqueue(30);

        assert rQueue.size() == 3;

        for (int i = 0; i < 10; i++) {
            rQueue.sample();
        }

        rQueue.dequeue();
        assert rQueue.size() == 2;
        rQueue.enqueue(40);
        assert rQueue.size() == 3;
        rQueue.dequeue();
        assert rQueue.size() == 2;
        rQueue.dequeue();
        assert rQueue.size() == 1;
        rQueue.dequeue();
        assert rQueue.size() == 0;

    }

    private static void testSingle() {
        System.out.println("testSingle");
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();

        rQueue.enqueue(1);
        assert !rQueue.isEmpty();
        assert rQueue.size() == 1;
        assert rQueue.sample() == 1;
        assert rQueue.dequeue() == 1;
        assert rQueue.size() == 0;
        assert rQueue.isEmpty();

        rQueue.enqueue(2);
        assert !rQueue.isEmpty();
        assert rQueue.size() == 1;
        assert rQueue.sample() == 2;
        assert rQueue.dequeue() == 2;
        assert rQueue.size() == 0;
        assert rQueue.isEmpty();
    }

}
