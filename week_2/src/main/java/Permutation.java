import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(StdIn.readString());
//        StdIn.readString();

        Deque<String> deque = new Deque<>();
        deque.addFirst(String.valueOf(k));

    }
}
