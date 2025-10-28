
public class PrimTest {
    public static void main(String[] args) {
        EdgeWeightedGraph g = new EdgeWeightedGraph(5);
        g.addEdge(new Edge(0, 1, 4));
        g.addEdge(new Edge(0, 2, 3));
        g.addEdge(new Edge(1, 2, 2));
        g.addEdge(new Edge(1, 3, 5));
        g.addEdge(new Edge(2, 3, 7));
        g.addEdge(new Edge(3, 4, 2));
        g.addEdge(new Edge(2, 4, 6));

        long start = System.nanoTime();
        PrimMST prim = new PrimMST(g);
        long end = System.nanoTime();

        System.out.println("Prim MST edges:");
        for (Edge e : prim.edges()) {
            System.out.println(e);
        }
        System.out.printf("Total cost = %.2f%n", prim.weight());
        System.out.println("Operation count = " + prim.getOperationCount());
        System.out.printf("Execution time = %.3f ms%n", (end - start) / 1_000_000.0);
    }
}
