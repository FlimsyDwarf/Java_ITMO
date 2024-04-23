package queue;

public interface Queue {
    /*
    Model: a[1]..a[n]

    Invariant: for i in 1 .. n a[i] != null && n >= 0
    Let immutable(n): for i in [1; n]: a[i] != null
    */
    /*
    Pre: element != null
    Post: immutable(n) && n' == n + 1
     */
    void enqueue(final Object element);

    /*
    Pre: n >= 1
    Post: n' == n - 1 && R == element[1] && for i in [2; n]: a'[i - 1] == a[i]
    */
    Object dequeue();

    /*
    Pre: n >= 1
    Post: R == a[1] && immutable(n)
    */
    Object element();

    /*
    Pred: true
    Post: R == n && immutable(n) && n' == n
    */
    int size();

    /*
    Pred: true
    Post: R == (n == 0) && immutable(n) && n' == n
     */
    boolean isEmpty();

    /*
    Pred: true
    Post: n == 0 && size == 0
     */
    void clear();

    /*
    Pred: element != null
    Post: immutable(n) && n' == n && (if element in [1; n] in a) -> R == true
    else R == false)
     */
    boolean contains(Object element);

    /*
    Pred: element != null
    Post: if element == a[i] where i min possible index -> immutable(i - 1) &&
     for j in range [i + 1; n]: a[j - 1] = a[j] && n' == n - 1
      else immutable(n) && n' == n
     */
    boolean removeFirstOccurrence(Object element);
}
