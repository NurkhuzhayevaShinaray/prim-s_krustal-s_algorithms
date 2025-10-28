// Union-Find data structure for Kruskal's algorithm
class UnionFind {
    private Map<String, String> parent = new HashMap<>();
    private Map<String, Integer> rank = new HashMap<>();
    private int unionCount = 0;
    private int findCount = 0;

    public void makeSet(String vertex) {
        parent.put(vertex, vertex);
        rank.put(vertex, 0);
    }

    public String find(String vertex) {
        findCount++;
        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    public void union(String vertex1, String vertex2) {
        unionCount++;
        String root1 = find(vertex1);
        String root2 = find(vertex2);

        if (!root1.equals(root2)) {
            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }

    public int getUnionCount() { return unionCount; }
    public int getFindCount() { return findCount; }
    public int getTotalOperations() { return unionCount + findCount; }
}
