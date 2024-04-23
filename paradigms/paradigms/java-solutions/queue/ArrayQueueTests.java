package queue;
public class ArrayQueueTests {

    public static void main(String[] args) {
        int numberOfElements = 10;
        if (testToArray(numberOfElements)) {
            System.out.println("TestToArray: PASSED");
        }
        if (testEnqueue(numberOfElements)) {
            System.out.println("TestEnqueue: PASSED");
        }
        if (testElementDequeue(numberOfElements)) {
            System.out.println("TestElementDequeue: PASSED");
        }
        if (testPush(numberOfElements)) {
            System.out.println("TestPush: PASSED");
        }
        if (testPeekRemove(numberOfElements)) {
            System.out.println("TestPeekRemove: PASSED");
        }
        if (testClear(numberOfElements)) {
            System.out.println("TestClear: PASSED");
        }
    }

    public static void fill(ArrayQueue queue, int number, boolean mod) {
        for (int i = 0; i < number; i++) {
            if (mod) {
                queue.push(i);
            } else {
                queue.enqueue(i);
            }
        }
    }

    public static boolean testEnqueue(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, false);
        assert q.size() == n : "Exception in testEnqueue: queue.size != " + n;
        Object[] a = q.toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testEnqueue: " + a[i] + "!=" + i;
        }
        q.clear();
        return true;
    }

    public static boolean testPush(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, true);
        assert q.size() == n : "Exception in testPush: queue.size != " + n;
        Object[] a = q.toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(n - i - 1) : "Exception in testPush: " + a[i] + "!=" + i;
        }
        q.clear();
        return true;
    }

    public static boolean testToArray(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, false);
        assert q.size() == n : "Exception in testToArray: queue.size != " + n;
        Object[] a = q.toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testToArray: " + a[i] + "!=" + i;
        }
        q.clear();
        return true;
    }

    public static boolean testElementDequeue(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, false);
        assert q.size() == n : "Exception in testElementDequeue: queue.size != " + n;
        for(int i = 0; i < n; i++) {
            assert q.element().equals(i) : "Exception in testElementDequeue: " + q.element() + "!=" + i;
            assert q.dequeue().equals(i) : "Exception in testElementDequeue: removed element != "  + i;
        }
        return true;
    }

    public static boolean testPeekRemove(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, false);
        assert q.size() == n : "Exception in testPeekRemove: " + q.size() + " != " + n;
        for(int i = n - 1; i >= 0; i--) {
            assert q.peek().equals(i) : "Exception in testPeekRemove: " + q.peek() + " != " + i;
            Object element = q.remove();
            assert element.equals(i) : "Exception in testPeekRemove: " + element + " != " + i;
        }
        return true;
    }

    public static boolean testClear(int n) {
        ArrayQueue q = new ArrayQueue();
        fill(q, n, false);
        q.clear();
        assert q.isEmpty() : "Exception in testClear: queue is not empty";
        return true;
    }
}