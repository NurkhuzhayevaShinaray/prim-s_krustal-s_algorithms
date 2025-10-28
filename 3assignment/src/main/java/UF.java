
public class UF {
    private int[] parent;
    private int[] rank;
    private int count;

    public UF(int n) {
        count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx == ry) return;
        if (rank[rx] < rank[ry]) parent[rx] = ry;
        else if (rank[ry] < rank[rx]) parent[ry] = rx;
        else {
            parent[ry] = rx;
            rank[rx]++;
        }
        count--;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int count() {
        return count;
    }
}
