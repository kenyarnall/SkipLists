import java.lang.reflect.Array;
import java.util.Random;

@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<T>> {
    private Node<T> sentinel;
    private int size;
    private int height;
    private Random rand;

    public SkipList() {
        // how high should the initial sentinel be?
        sentinel = new Node<T>(null, 32);
        height = 0;
        rand = new Random();
    }

    public boolean add(T x) {
        Node<T> cur = sentinel;
        int r = height;
        Node<T>[] stack = (Node<T>[]) Array.newInstance(Node.class,
                sentinel.height()+1);
        int comp = 0;
        while (r >= 0) {
            while (cur.next[r] != null
                    && (comp = cur.next[r].data.compareTo(x)) < 0)
                cur = cur.next[r];
            // did we find x? Don't insert if so
            if (cur.next[r] != null && comp == 0)
                return false;
            // we're moving down a level -- remember this node
            stack[r] = cur;
            r--;
        }

        // our new node, finally
        Node<T> w = new Node<T>(x, pickHeight());

        // did our skiplist get taller? If so, the new node is the first one
        // at each of the new layers
        while (this.height < w.height()) {
            this.height++;
            stack[this.height] = sentinel;
        }

        // hook the new node into each list
        for (int i = 0; i < w.next.length; i++) {
            w.next[i] = stack[i].next[i];
            stack[i].next[i] = w;
        }
        this.size++;
        return true;
    }

    // are there other ways to phrase this?
    public T remove(T x) {
        T removed = null;
        Node<T> cur = sentinel;
        int r = this.height;
        int comp = 0;
        while (r >= 0) {
            while (cur.next[r] != null
                    && (comp = cur.next[r].data.compareTo(x)) < 0) {
                cur = cur.next[r];
            }
            // did we find it in the current list?
            if (cur.next[r] != null && comp == 0) {
                removed = cur.next[r].data;
                cur.next[r] = cur.next[r].next[r];
                if (cur == sentinel && cur.next[r] == null)
                    this.height--;  // height has gone down
            }
            r--;
        }
        if (removed != null)
            this.size--;
        return removed;
    }


    // find an element that compares equal to x, or return null
    public T find(T x) {
        var p = findPredNode(x);
        if (p.next[0] == null)
            return null;
        else
            return p.next[0].data;
    }

    private Node<T> findPredNode(T x){
        Node<T> cur = sentinel;
        int r = height;
        while (r >= 0) {
            // move right as far as possible
            while (cur.next[r] != null &&  cur.next[r].data.compareTo(x) < 0)
                cur = cur.next[r];
            // can't go right; go down
            r--;
        }
        // cur now points at the node before the spot where x is or would be
        return cur;
    }

    private int pickHeight() {
        int z = rand.nextInt();
        int k = 0;
        int m = 1;
        while ((z & m) != 0) {
            k++;
            m <<= 1;
        }
        return k;
    }

    private static class Node<T> {
        T data;
        Node<T>[] next;

        @SuppressWarnings("unchecked")
        Node(T d, int h) {
            data = d;
            next = (Node<T>[]) Array.newInstance(Node.class, h+1);
        }

        int height() {
            return next.length - 1;
        }
    }


}
