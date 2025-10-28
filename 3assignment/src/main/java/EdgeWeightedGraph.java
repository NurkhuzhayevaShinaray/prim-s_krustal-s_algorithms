import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++) adj[v] = new ArrayList<>();
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        List<Edge> list = new ArrayList<>();
        boolean[] seen = new boolean[V];
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                int w = e.other(v);
                // add each undirected edge once
                if (v <= w) list.add(e);
            }
        }
        return list;
    }
}
