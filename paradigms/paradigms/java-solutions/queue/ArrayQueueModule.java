package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {

	/*
	Model: a[1]..a[n]

	Invariant: for i in 1 .. n a[i] != null && n >= 0
	Let immutable(n): for i in [1; n]: a[i] != null
	 */
	private static int head = 0;
	private static int tail = 0;
	private static int size = 0;
	private static Object[] elements = new Object[2];

	/*
	Pre: element != null
	Post: immutable(n) && n' == n + 1
	 */
	public static void enqueue(final Object element) {
		Objects.requireNonNull(element);
		ensureCapacity();
		elements[tail] = element;
		tail = (tail + 1) % elements.length;
		size++;
	}

	/*
	Pre: element != null
	Post: n' == n + 1 && immutable(n)
	 */
	public static void push(final Object element) {
		Objects.requireNonNull(element);
		ensureCapacity();
		head = moduleSubstraction(head);
		elements[head] = element;
		size++;
	}

	/*
	Pre: n >= 1
	Post R == a[n] && immutable(n)
	 */
	public static Object peek() {
		assert size > 0;
		return elements[moduleSubstraction(tail)];
	}


	/*
	Pre: n >= 1
	Post: n' == n - 1 && immutable(n') && R == a[n]
	 */
	public static Object remove() {
		assert size > 0;
		tail = moduleSubstraction(tail);
		Object result = elements[tail];
		elements[tail] = null;
		size--;
		if (size == 0) {
			clear();
		}
		return result;
	}

	/*
	Pre: n >= 1
	Post: n' == n - 1 && R == element[1] && for i in [2; n]: a'[i - 1] == a[i]
	*/
	public static Object dequeue() {
		assert size > 0;
		Object result = elements[head];
		elements[head] = null;
		size--;
		if (size == 0) {
			clear();
		} else {
			head = (head + 1) % elements.length;
		}
		return result;
	}

	/*
	Pre: n >= 1
	Post: R == a[1] && immutable(n)
	 */
	public static Object element() {
		assert size > 0;
		return elements[head];
	}

	/*
	Pred: true
	Post: R == n && immutable(n) && n' == n
	*/
	public static int size() {
//		if (head == -1) {
//			return 0;
//		}
//		if (tail == -1) {
//			return elements.length - head;
//		}
//		return head <= tail ? tail - head : tail + elements.length - head;
		return size;
	}

	/*
	Pre: true
	Post: R == a && immutable(n) && n' == n
	 */
	public static Object[] toArray() {
		int curHead = head;
		Object[] result = new Object[size()];
		int i = 0;
		while (i < size) {
			result[i++] = elements[curHead];
			curHead = (curHead + 1) % elements.length;
		}
		return result;
	}

	/*
	Pred: true
	Post: R == (n == 0) && immutable(n) && n' == n
	 */
	public static boolean isEmpty() {
		return size() == 0;
	}

	/*
	Pred: true
	Post: n == 0 && size == 0
	 */
	public static void clear() {
		head = 0;
		tail = 0;
		size = 0;
		elements = new Object[2];
	}

	private static void ensureCapacity() {
		if (size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
			if (tail == head) {
				System.arraycopy(elements, head, elements, head + elements.length / 2, elements.length / 2 - head);
				head += elements.length / 2;
			}
		}
	}

	private static int moduleSubstraction(int val) {
		return (val - 1 + elements.length) % elements.length;
	}
}
