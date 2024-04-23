package queue;

import static queue.ArrayQueueADT.*;
public class ArrayQueueADTTest {
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
    public static void fill(ArrayQueueADT q, int number, boolean mod) {
        for (int i = 0; i < number; i++) {
            if (mod) {
                push(q, i);
            } else {
                enqueue(q, i);
            }
        }
    }

    public static boolean testEnqueue(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, false);
        assert size(q) == n : "Exception in testEnqueue: queue.size != " + n;
        Object[] a = toArray(q);
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testEnqueue: " + a[i] + "!=" + i;
        }
        ArrayQueueADT.clear(q);
        return true;
    }

    public static boolean testPush(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, true);
        assert size(q) == n : "Exception in testPush: queue.size != " + n;
        Object[] a = toArray(q);
        for (int i = 0; i < n; i++) {
            assert a[i].equals(n - i - 1) : "Exception in testPush: " + a[i] + "!=" + i;
        }
        ArrayQueueADT.clear(q);
        return true;
    }

    public static boolean testToArray(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, false);
        assert size(q) == n : "Exception in testToArray: queue.size != " + n;
        Object[] a = toArray(q);
        for (int i = 0; i < n; i++) {
            assert a[i].equals(i) : "Exception in testToArray: " + a[i] + "!=" + i;
        }
        ArrayQueueADT.clear(q);
        return true;
    }

    public static boolean testElementDequeue(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, false);
        assert size(q) == n : "Exception in testElementDequeue: queue.size != " + n;
        for(int i = 0; i < n; i++) {
            assert element(q).equals(i) : "Exception in testElementDequeue: " + element(q) + "!=" + i;
            assert dequeue(q).equals(i) : "Exception in testElementDequeue: removed element != "  + i;
        }
        return true;
    }

    public static boolean testPeekRemove(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, false);
        assert size(q) == n : "Exception in testPeekRemove: " + size(q) + " != " + n;
        for(int i = n - 1; i >= 0; i--) {
            assert peek(q).equals(i) : "Exception in testPeekRemove: " + peek(q) + " != " + i;
            Object element = remove(q);
            assert element.equals(i) : "Exception in testPeekRemove: " + element + " != " + i;
        }
        return true;
    }

    public static boolean testClear(int n) {
        ArrayQueueADT q = ArrayQueueADT.create();
        fill(q, n, false);
        ArrayQueueADT.clear(q);
        assert isEmpty(q) : "Exception in testClear: queue is not empty";
        return true;
    }
}