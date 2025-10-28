// Kruskal's Algorithm Implementation
class KruskalMST {
    private List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0.0;
    private UnionFind uf;
    private long executionTime = 0;

    public void findMST(Graph graph) {
        long startTime = System.nanoTime();

        uf = new UnionFind();
        mstEdges.clear();
        totalCost = 0.0;

        // Initialize Union-Find
        for (String vertex : graph.getVertices()) {
            uf.makeSet(vertex);
        }

        // Sort all edges by weight
        List<Edge> sortedEdges = new ArrayList<>(graph.getAllEdges());
        Collections.sort(sortedEdges);

        // Process edges in sorted order
        for (Edge edge : sortedEdges) {
            String from = edge.getFrom();
            String to = edge.getTo();

            if (!uf.find(from).equals(uf.find(to))) {
                uf.union(from, to);
                mstEdges.add(edge);
                totalCost += edge.getWeight();
            }

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break;
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public double getTotalCost() { return totalCost; }
    public int getOperationsCount() { return uf.getTotalOperations(); }
    public long getExecutionTime() { return executionTime; }
}

// Main class to handle JSON I/O and algorithm execution
public class MSTAlgorithms {

    public static Graph readGraphFromJSON(String filename) throws IOException {
        Graph graph = new Graph();

        try (FileReader reader = new FileReader(filename)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray graphs = jsonObject.getJSONArray("graphs");

            // For this assignment, we'll process the first graph
            JSONObject graphData = graphs.getJSONObject(0);
            JSONArray edges = graphData.getJSONArray("edges");

            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                String from = edge.getString("from");
                String to = edge.getString("to");
                double weight = edge.getDouble("weight");

                graph.addEdge(from, to, weight);
            }
        }

        return graph;
    }
