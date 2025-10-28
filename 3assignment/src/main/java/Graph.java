// Graph class to represent the transportation network
class Graph {
    private Map<String, List<Edge>> adjacencyList = new HashMap<>();
    private List<Edge> allEdges = new ArrayList<>();
    private Set<String> vertices = new HashSet<>();

    public void addEdge(String from, String to, double weight) {
        Edge edge = new Edge(from, to, weight);
        allEdges.add(edge);
        vertices.add(from);
        vertices.add(to);

        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(edge);
        adjacencyList.computeIfAbsent(to, k -> new ArrayList<>()).add(edge);
    }

    public List<Edge> getAdjacentEdges(String vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return allEdges.size();
    }
}

