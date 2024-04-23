package queue;

import static queue.ArrayQueueModule.*;
public class ArrayQueueModuleTest {
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
    public static void fill(int number, boolean mod) {
        for (int i = 0; i < number; i++) {
            if (mod) {
                push(i);
            } else {
                enqueue(i);
            }
        }
    }

    public static boolean testEnqueue(int n) {
        fill(n, false);
        assert size() == n : "Exception in testEnqueue: queue.size != " + n;
        Object[] a = toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testEnqueue: " + a[i] + "!=" + i;
        }
        clear();
        return true;
    }

    public static boolean testPush(int n) {
        fill(n, true);
        assert size() == n : "Exception in testPush: queue.size != " + n;
        Object[] a = toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(n - i - 1) : "Exception in testPush: " + a[i] + "!=" + i;
        }
        clear();
        return true;
    }

    public static boolean testToArray(int n) {
        fill(n, false);
        assert size() == n : "Exception in testToArray: queue.size != " + n;
        Object[] a = toArray();
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testToArray: " + a[i] + "!=" + i;
        }
        clear();
        return true;
    }

    public static boolean testElementDequeue(int n) {
        fill(n, false);
        assert size() == n : "Exception in testElementDequeue: queue.size != " + n;
        for(int i = 0; i < n; i++) {
            assert element().equals(i) : "Exception in testElementDequeue: " + element() + "!=" + i;
            assert dequeue().equals(i) : "Exception in testElementDequeue: removed element != "  + i;
        }
        return true;
    }

    public static boolean testPeekRemove(int n) {
        fill(n, false);
        assert size() == n : "Exception in testPeekRemove: " + size() + " != " + n;
        for(int i = n - 1; i >= 0; i--) {
            assert peek().equals(i) : "Exception in testPeekRemove: " + peek() + " != " + i;
            Object element = remove();
            assert element.equals(i) : "Exception in testPeekRemove: " + element + " != " + i;
        }
        return true;
    }

    public static boolean testClear(int n) {
        fill(n, false);
        clear();
        assert isEmpty() : "Exception in testClear: queue is not empty";
        return true;
    }

}