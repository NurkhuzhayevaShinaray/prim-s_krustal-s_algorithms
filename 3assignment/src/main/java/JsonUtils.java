import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtils {
    public static JSONObject buildAlgorithmResult(PrimMST prim, java.util.List<String> names, long startNano, long endNano) {
        JSONObject res = new JSONObject();
        JSONArray mstEdges = new JSONArray();
        for (Edge e : prim.edges()) {
            JSONObject edgeJson = new JSONObject();
            int v = e.either();
            int w = e.other(v);
            edgeJson.put("from", names.get(v));
            edgeJson.put("to", names.get(w));
            if (Math.floor(e.weight()) == e.weight()) edgeJson.put("weight", (long)e.weight());
            else edgeJson.put("weight", e.weight());
            mstEdges.add(edgeJson);
        }
        res.put("mst_edges", mstEdges);
        double totalCost = prim.weight();
        if (Math.floor(totalCost) == totalCost) res.put("total_cost", (long) totalCost);
        else res.put("total_cost", totalCost);
        res.put("operations_count", prim.getOperationCount());
        res.put("execution_time_ms", (endNano - startNano) / 1_000_000.0);
        return res;
    }

    public static JSONObject buildAlgorithmResult(KruskalMST kr, java.util.List<String> names, long startNano, long endNano) {
        JSONObject res = new JSONObject();
        JSONArray mstEdges = new JSONArray();
        for (Edge e : kr.edges()) {
            JSONObject edgeJson = new JSONObject();
            int v = e.either();
            int w = e.other(v);
            edgeJson.put("from", names.get(v));
            edgeJson.put("to", names.get(w));
            if (Math.floor(e.weight()) == e.weight()) edgeJson.put("weight", (long)e.weight());
            else edgeJson.put("weight", e.weight());
            mstEdges.add(edgeJson);
        }
        res.put("mst_edges", mstEdges);
        double totalCost = kr.weight();
        if (Math.floor(totalCost) == totalCost) res.put("total_cost", (long) totalCost);
        else res.put("total_cost", totalCost);
        res.put("operations_count", kr.getOperationCount());
        res.put("execution_time_ms", (endNano - startNano) / 1_000_000.0);
        return res;
    }
}


