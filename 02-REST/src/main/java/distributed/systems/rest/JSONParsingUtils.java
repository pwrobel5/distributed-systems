package distributed.systems.rest;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class JSONParsingUtils {
    public static TreeMap<Object, Object> parseJSON(String queryResult, String entryName) {
        Genson genson = new Genson();
        Map<String, Object> firstPart = genson.deserialize(queryResult, Map.class);

        if (!(Boolean) firstPart.get("success")) return null;
        Object symbolsSet = firstPart.get(entryName);
        if (symbolsSet instanceof HashMap<?, ?>) {
            return new TreeMap<>((HashMap<?, ?>) symbolsSet);
        } else return null;
    }
}
