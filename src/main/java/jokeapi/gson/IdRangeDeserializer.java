package jokeapi.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IdRangeDeserializer implements JsonDeserializer<IdRange> {

    @Override
    public IdRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject jokesObject = jsonObject.getAsJsonObject("jokes");
        JsonObject idRangeObject = jokesObject.getAsJsonObject("idRange");

        Map<String, List<Integer>> ranges = new Gson().fromJson(idRangeObject, Map.class);

        for (Map.Entry<String, List<Integer>> entry : ranges.entrySet()) {
            List<Integer> updatedList = new ArrayList<>();
            for (Object value : entry.getValue()) {
                if (value instanceof Double) {
                    updatedList.add(((Double) value).intValue());
                } else {
                    updatedList.add((Integer) value);
                }
            }
            entry.setValue(updatedList);
        }

        IdRange idRange = new IdRange();
        idRange.setRanges(ranges);

        return idRange;
    }
}