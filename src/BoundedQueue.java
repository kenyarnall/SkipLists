public class BoundedQueue<T> {
    private T[] data;
    private int start;
    private int n;

    public BoundedQueue(int sz) {
        data = (T[]) new Object[sz];
        n = 0;
        start = 0;
    }

    public void add(T x) {
        if (n+1 > data.length)
            throw new IndexOutOfBoundsException();

        data[(start + n) % data.length] = x;
        n++;
    }

    public T remove() {
        if (n == 0)
            throw new IndexOutOfBoundsException();
        T ret = data[start];
        data[start] = null;
        start = (start + 1) % data.length;
        n--;
        return ret;
    }
}
