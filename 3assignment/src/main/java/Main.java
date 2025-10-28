import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "data/input_example.json";
        String outputPath = "data/output_example.json";

        JSONParser parser = new JSONParser();
        JSONObject input = (JSONObject) parser.parse(new FileReader(inputPath));
        JSONArray graphs = (JSONArray) input.get("graphs");

        JSONArray results = new JSONArray();

        for (Object gObj : graphs) {
            JSONObject gJson = (JSONObject) gObj;
            long id = (long) gJson.get("id");
            JSONArray nodes = (JSONArray) gJson.get("nodes");
            JSONArray edges = (JSONArray) gJson.get("edges");
            Map<String, Integer> index = new HashMap<>();
            List<String> names = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i++) {
                String name = (String) nodes.get(i);
                index.put(name, i);
                names.add(name);
            }

            EdgeWeightedGraph graph = new EdgeWeightedGraph(nodes.size());
            for (Object eObj : edges) {
                JSONObject e = (JSONObject) eObj;
                String from = (String) e.get("from");
                String to = (String) e.get("to");
                double weight = ((Number) e.get("weight")).doubleValue();
                int v = index.get(from);
                int w = index.get(to);
                graph.addEdge(new Edge(v, w, weight));
            }

            JSONObject result = new JSONObject();
            result.put("graph_id", id);

            JSONObject inputStats = new JSONObject();
            inputStats.put("vertices", graph.V());
            inputStats.put("edges", graph.E());
            result.put("input_stats", inputStats);


            long start = System.nanoTime();
            PrimMST prim = new PrimMST(graph);
            long end = System.nanoTime();
            JSONObject primJson = JsonUtils.buildAlgorithmResult(prim, names, start, end);
            result.put("prim", primJson);


            start = System.nanoTime();
            KruskalMST kr = new KruskalMST(graph);
            end = System.nanoTime();
            JSONObject krJson = JsonUtils.buildAlgorithmResult(kr, names, start, end);
            result.put("kruskal", krJson);

            results.add(result);
        }

        JSONObject output = new JSONObject();
        output.put("results", results);

        try (FileWriter file = new FileWriter(outputPath)) {
            file.write(output.toJSONString());
            file.flush();
        }

        System.out.println("Finished. Results written to " + outputPath);
    }
}
