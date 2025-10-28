class PrimMST {
    private List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0.0;
    private int operationsCount = 0;
    private long executionTime = 0;

    public void findMST(Graph graph) {
        long startTime = System.nanoTime();
        operationsCount = 0;

        if (graph.getVertexCount() == 0) return;

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Map<String, Edge> minEdge = new HashMap<>();

        // Start with first vertex
        String startVertex = graph.getVertices().iterator().next();
        visited.add(startVertex);
        operationsCount++;

        // Add all edges from start vertex to priority queue
        for (Edge edge : graph.getAdjacentEdges(startVertex)) {
            pq.offer(edge);
            operationsCount++;
        }

        while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = pq.poll();
            operationsCount++;

            String nextVertex = null;
            if (visited.contains(edge.getFrom()) && !visited.contains(edge.getTo())) {
                nextVertex = edge.getTo();
            } else if (visited.contains(edge.getTo()) && !visited.contains(edge.getFrom())) {
                nextVertex = edge.getFrom();
            }

            if (nextVertex != null) {
                visited.add(nextVertex);
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                operationsCount += 2;

                // Add edges from the new vertex
                for (Edge adjEdge : graph.getAdjacentEdges(nextVertex)) {
                    if (!visited.contains(adjEdge.getFrom()) || !visited.contains(adjEdge.getTo())) {
                        pq.offer(adjEdge);
                        operationsCount++;
                    }
                }
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1000000;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public double getTotalCost() { return totalCost; }
    public int getOperationsCount() { return operationsCount; }
    public long getExecutionTime() { return executionTime; }
}
