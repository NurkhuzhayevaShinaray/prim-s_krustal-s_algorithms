import java.util.Arrays;

public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;

    private double weight;
    private Queue<Edge> mst = new Queue<>();
    private int operationCount = 0;

    public KruskalMST(EdgeWeightedGraph G) {
        Edge[] edges = new Edge[G.E()];
        int t = 0;
        for (Edge e : G.edges()) edges[t++] = e;
        Arrays.sort(edges); operationCount += edges.length * (int)(Math.log(Math.max(1, edges.length))/Math.log(2) + 1);

        UF uf = new UF(G.V());
        for (int i = 0; i < edges.length && mst.size() < G.V() - 1; i++) {
            Edge e = edges[i];
            int v = e.either();
            int w = e.other(v);

            operationCount++;
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                operationCount++;
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst; }

    public double weight() {
        return weight; }

    public int getOperationCount() {
        return operationCount; }
}
