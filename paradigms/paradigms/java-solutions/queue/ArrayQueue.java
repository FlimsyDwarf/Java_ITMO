package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
	private int head;
	private int tail;
	private Object[] elements;

	public ArrayQueue() {
		head = 0;
		tail = 0;
		elements = new Object[2];
	}
	@Override
	public void enqueueImpl(final Object element) {
		ensureCapacity();
		elements[tail] = element;
		tail = (tail + 1) % elements.length;
	}

	/*
	Pre: element != null
	Post: n' == n + 1 && immutable(n)
	 */
	public void push(final Object element) {
		Objects.requireNonNull(element);
		size++;
		ensureCapacity();
		head = moduleSubtraction(head);
		elements[head] = element;
	}

	/*
	Pre: n >= 1
	Post R == a[n] && immutable(n)
 	*/
	public Object peek() {
		assert size > 0;
		return elements[moduleSubtraction(tail)];
	}

	/*
	Pre: n >= 1
	Post: n' == n - 1 && immutable(n') && R == a[n]
	 */
	public Object remove() {
		assert size > 0;
		size--;
		tail = moduleSubtraction(tail);
		Object result = elements[tail];
		elements[tail] = null;
		if (size == 0) {
			clear();
		}
		return result;
	}

	/*
	Pre: n >= 1
	Post: R == a[1] && immutable(n)
	 */
	@Override
	public Object elementImpl() {
		return elements[head];
	}

	/*
	Pre: n >= 1
	Post: n' == n - 1 && R == element[1] && for i in [2; n]: a'[i - 1] == a[i]
	*/
	@Override
	protected Object dequeueImpl() {
		Object result = elements[head];
		elements[head] = null;
		if (size == 0) {
			clear();
		} else {
			head = (head + 1) % elements.length;
		}
		return result;
	}


	@Override
	protected void clearImpl() {
		head = 0;
		tail = 0;
		elements = new Object[2];
	}

	/*
	Pre: true
	Post: R == a && immutable(n) && n' == n
	 */
	public Object[] toArray() {
		int curHead = head;
		Object[] result = new Object[size()];
		for (int i = 0; i < size; i++){
			result[i] = elements[curHead];
			curHead = (curHead + 1) % elements.length;
		}
		return result;
	}

	@Override
	protected boolean removeFirstOccurrenceImpl(Object element) {
		int id = findElement(element);
		if (id == -1) {
			return false;
		}
		elements[id] = null;
		if (head <= id) {
			System.arraycopy(elements, head, elements, head + 1, id - head);
			head = (head + 1) % elements.length;
		} else {
			System.arraycopy(elements, id + 1, elements, id, tail - id - 1);
			tail = moduleSubtraction(tail);
		}
		size--;
		return true;
	}

	@Override
	protected boolean containsImpl(Object element) {
		return findElement(element) != -1;
	}

	private int findElement(Object element) {
		for (int i = head; i < size + head; i++) {
			if (elements[i % elements.length].equals(element)) {
				return i % elements.length;
			}
		}
		return -1;
	}

	private void ensureCapacity() {
		if (size - 1 == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
			if (tail == head) {
				System.arraycopy(elements, head, elements, head + elements.length / 2, elements.length / 2 - head);
				head += elements.length / 2;
			}
		}
	}
	private int moduleSubtraction(int val) {
		return (val - 1 + elements.length) % elements.length;
	}

}
