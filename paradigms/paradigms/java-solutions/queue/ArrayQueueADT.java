package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {

	private int head;
	private int tail;
	private int size;
	private Object[] elements;

	public ArrayQueueADT() {
		head = 0;
		tail = 0;
		size = 0;
		elements = new Object[2];
	}

	public static ArrayQueueADT create() {
		ArrayQueueADT queueADT = new ArrayQueueADT();
		return queueADT;
	}

	/*
	Pre: element != null
	Post: immutable(n) && n' == n + 1
	 */
	public static void enqueue(final ArrayQueueADT queue, final Object x) {
		Objects.requireNonNull(x);
		ensureCapacity(queue);
		queue.elements[queue.tail] = x;
		queue.tail = (queue.tail + 1) % queue.elements.length;
		queue.size++;
	}

	/*
Pre: element != null
Post: n' == n + 1 && immutable(n)
 */
	public static void push(final ArrayQueueADT queue, final Object element) {
		Objects.requireNonNull(element);
		ensureCapacity(queue);
		queue.head = moduleSubstraction(queue, queue.head);
		queue.elements[queue.head] = element;
		queue.size++;
	}

	/*
Pre: n >= 1
Post R == a[n] && immutable(n)
 */
	public static Object peek(final ArrayQueueADT queue) {
		assert queue.size > 0;
		return queue.elements[moduleSubstraction(queue, queue.tail)];
	}

	/*
	Pre: n >= 1
	Post: R == a[1] && immutable(n)
	 */
	public static Object element(final ArrayQueueADT queue) {
		assert queue.size > 0;
		return queue.elements[queue.head];
	}

	/*
Pre: n >= 1
Post: n' == n - 1 && immutable(n') && R == a[n]
 */
	public static Object remove(ArrayQueueADT queue) {
		assert queue.size > 0;
		queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
		Object result = queue.elements[queue.tail];
		queue.elements[queue.tail] = null;
		queue.size--;
		if (queue.size == 0) {
			clear(queue);
		}
		return result;
	}

	/*
	Pre: n >= 1
	Post: n' == n - 1 && R == element[1] && for i in [2; n]: a'[i - 1] == a[i]
	*/
	public static Object dequeue(final ArrayQueueADT queue) {
		assert queue.size > 0;
		Object result = queue.elements[queue.head];
		queue.elements[queue.head] = null;
		queue.size--;
		if (queue.size == 0) {
			clear(queue);
		} else {
			queue.head = (queue.head + 1) % queue.elements.length;
		}
		return result;
	}

	/*
	Pred: true
	Post: R == n && immutable(n) && n' == n
	*/
	public static int size(final ArrayQueueADT queue) {
		return queue.size;
	}

	/*
	Pred: true
	Post: R == (n == 0) && immutable(n) && n' == n
	 */
	public static boolean isEmpty(final ArrayQueueADT queue) {
		return size(queue) == 0;
	}

	/*
	Pred: true
	Post: n == 0 && size == 0
	 */
	public static void clear(final ArrayQueueADT queue) {
		queue.head = 0;
		queue.tail = 0;
		queue.size = 0;
		queue.elements = new Object[2];
	}

	/*
Pre: true
Post: R == a && immutable(n) && n' == n
 */
	public static Object[] toArray(final ArrayQueueADT queue) {
		int curHead = queue.head;
		Object[] result = new Object[size(queue)];
		int i = 0;
		while (queue.size > i) {
			result[i++] = queue.elements[curHead];
			curHead = (curHead + 1) % queue.elements.length;
		}
		return result;
	}

	private static void ensureCapacity(final ArrayQueueADT queue) {
		if (queue.size == queue.elements.length) {
			queue.elements = Arrays.copyOf(queue.elements, queue.elements.length * 2);
			if (queue.tail == queue.head) {
				System.arraycopy(queue.elements, queue.head, queue.elements, queue.head + queue.elements.length / 2, queue.elements.length / 2 - queue.head);
				queue.head += queue.elements.length / 2;
			}
		}
	}

	private static int moduleSubstraction(ArrayQueueADT queue, int val) {
		return (val - 1 + queue.elements.length) % queue.elements.length;
	}
}
