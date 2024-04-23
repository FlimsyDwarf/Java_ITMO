package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;


    @Override
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        size++;
        enqueueImpl(element);
    }
    protected abstract void enqueueImpl(final Object element);

    @Override
    public Object dequeue() {
        assert size > 0;
        size--;
        //System.out.println(size + " deq");
        return dequeueImpl();
    }
    protected abstract Object dequeueImpl();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }
    protected abstract Object elementImpl();

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }
    protected abstract void clearImpl();
    @Override
    public boolean contains(Object element) {
        Objects.requireNonNull(element);
        if (size == 0) {
            return false;
        }
       return containsImpl(element);
    }
    protected abstract boolean containsImpl(Object element);

    @Override
    public boolean removeFirstOccurrence(Object element) {
        Objects.requireNonNull(element);
        if (size == 0) {
            return false;
        }
        return removeFirstOccurrenceImpl(element);
    }
    protected abstract boolean removeFirstOccurrenceImpl(Object element);
}
