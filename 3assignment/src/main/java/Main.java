import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "C:\\Users\\user\\IdeaProjects\\prim-s_krustal-s_algorithms\\3assignment\\src\\main\\resources\\input_example.json";
        String outputPath = "C:\\Users\\user\\IdeaProjects\\prim-s_krustal-s_algorithms\\3assignment\\src\\main\\resources\\output_example.json";

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
            file.write(prettyPrintJSON(output.toJSONString()));
            file.flush();
        }

        System.out.println("Finished. Results written to " + outputPath);
    }

    public static String prettyPrintJSON(String json) {
        StringBuilder pretty = new StringBuilder();
        int indent = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                pretty.append(c);

                if (i == 0 || json.charAt(i - 1) != '\\') {
                    inQuotes = !inQuotes;
                }
            } else if (!inQuotes) {
                switch (c) {
                    case '{':
                    case '[':
                        pretty.append(c).append("\n");
                        indent++;
                        pretty.append("  ".repeat(indent));
                        break;
                    case '}':
                    case ']':
                        pretty.append("\n");
                        indent--;
                        pretty.append("  ".repeat(indent)).append(c);
                        break;
                    case ',':
                        pretty.append(c).append("\n");
                        pretty.append("  ".repeat(indent));
                        break;
                    case ':':
                        pretty.append(": ");
                        break;
                    default:
                        if (!Character.isWhitespace(c)) {
                            pretty.append(c);
                        }
                }
            } else {
                pretty.append(c);
            }
        }
        return pretty.toString();
    }
}