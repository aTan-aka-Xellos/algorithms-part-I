import edu.princeton.cs.algs4.StdOut;

public class TestDeque {

    public static void main(String[] args) {

        testAddRemove();
        testSingle();
    }

        private static void testSingle() {
            Deque<Integer> deque = new Deque<>();

            deque.addFirst(10);
            print(deque);
            deque.removeFirst();
            print(deque);

            deque.addFirst(100);
            print(deque);

            deque.removeLast();
            print(deque);
            deque.addLast(-10);
            print(deque);
            deque.removeFirst();
            print(deque);
        }

        private static void testAddRemove() {
            Deque<Integer> deque = new Deque<>();

            deque.addFirst(1);
            deque.addFirst(0);
            deque.addLast(2);
            deque.addFirst(-1);

            print(deque); // -1 0 1 2

            deque.addLast(3);
            deque.addFirst(-2);

            print(deque); // -2 -1 0 1 2 3

            deque.removeLast();
            deque.removeFirst();

            print(deque); // -1 0 1 2

            deque.addLast(4);
            deque.addFirst(-3);

            print(deque); // -3 -1 0 1 2 4
        }

        private static void print(Deque<Integer> deque) {
            StdOut.println("size: " + deque.size());

            for (Integer i : deque) {
                StdOut.print(i + " ");
            }
            StdOut.println();
        }
}
