import java.util.Arrays;

public class IntList {
    private int[] arr;
    private int size;

    public IntList() {
        arr = new int[1];
        size = 0;
    }

    public IntList(int size) {
        arr = new int[size];
        this.size = size - 1;
    }

    public int size() {
        return size;
    }

    public void changeAt(int id, int newVal) {
        while (arr.length <= id) {
            expand();
        }
        arr[id] = newVal;
        if (size < id) {
            size = id + 1;
        }
    }

    public int getAt(int id) {
        while (arr.length <= id) {
            expand();
        }
        return arr[id];
    }

    private void expand() {
        arr = Arrays.copyOf(arr, arr.length * 2);
    }

    public void add(int x) {
        if (size >= arr.length) {
            expand();
        }
        arr[size] = x;
        size++;
    }

}