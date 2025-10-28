import java.util.Iterator;
public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    private int operationCount = 0;

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) prim(G, v);
        }
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        try { pq.insert(s, distTo[s]); } catch (Exception ex) { /* shouldn't happen */ }
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            operationCount++;
            if (marked[w]) continue;
            operationCount++;
            if (e.weight() < distTo[w] - 1e-12) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                operationCount++;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                    operationCount++;
                } else {
                    pq.insert(w, distTo[w]);
                    operationCount++;
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) mst.enqueue(e);
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges()) weight += e.weight();
        return weight;
    }

    public int getOperationCount() {
        return operationCount;
    }
}
