package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;
    private static class Node {
        private Object element;
        private Node next;

        public Node(Object element) {
            this.element = element;
            this.next = null;
        }
    }

    @Override
    public void enqueueImpl(final Object element) {
        if (size == 1) {
            head = new Node(null);
            head.next = new Node(element);
            tail = new Node(null);
            head.next.next = tail;
        } else {
            tail.element = element;
            tail.next = new Node(null);
            tail = tail.next;
        }
    }

    @Override
    protected Object dequeueImpl() {
        Object result = head.next.element;
        head = head.next;
        head.element = null;
        if (size == 0) {
            clear();
        }
        return result;
    }

    @Override
    protected Object elementImpl() {
        return head.next.element;
    }
    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    private Node findPreviousElement(Object element) {
        Node current = head;
        while (!current.next.equals(tail)) {
            if (current.next.element.equals(element)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    protected boolean containsImpl(Object element) {
        return findPreviousElement(element) != null;
    }

    @Override
    protected boolean removeFirstOccurrenceImpl(Object element) {
        Node current = findPreviousElement(element);
        if (current == null) {
            return false;
        }
        current.next = current.next.next;
        size--;
        return true;
    }
}
