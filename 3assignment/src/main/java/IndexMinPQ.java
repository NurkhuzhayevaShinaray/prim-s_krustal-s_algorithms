
public class IndexMinPQ<Key extends Comparable<Key>> {
    private int maxN;
    private int n;
    private int[] pq;
    private int[] qp;
    private Key[] keys;

    @SuppressWarnings("unchecked")
    public IndexMinPQ(int maxN) {
        this.maxN = maxN;
        keys = (Key[]) new Comparable[maxN];
        pq = new int[maxN + 1];
        qp = new int[maxN];
        for (int i = 0; i < maxN; i++) qp[i] = -1;
        n = 0;
    }

    public boolean isEmpty() { return n == 0; }

    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    public void insert(int i, Key key) {
        validateIndex(i);
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public void decreaseKey(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) throw new IllegalArgumentException("index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0) throw new IllegalArgumentException("new key is not smaller");
        keys[i] = key;
        swim(qp[i]);
    }

    public int delMin() {
        if (n == 0) throw new RuntimeException("Priority queue underflow");
        int minIndex = pq[1];
        exch(1, n--);
        sink(1);
        qp[minIndex] = -1;
        keys[minIndex] = null;
        pq[n+1] = -1;
        return minIndex;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/1, k)) {
            exch(k/2, k);
            k = k/2;
        }
        while (k > 1 && keys[pq[k/2]].compareTo(keys[pq[k]]) > 0) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && keys[pq[j]].compareTo(keys[pq[j+1]]) > 0) j++;
            if (keys[pq[k]].compareTo(keys[pq[j]]) <= 0) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int a, int b) {
        return keys[pq[a]].compareTo(keys[pq[b]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException("index out of bounds");
    }
}

