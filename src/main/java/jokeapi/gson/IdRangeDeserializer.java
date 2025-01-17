package jokeapi.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class IdRangeDeserializer implements JsonDeserializer<IdRange> {

    @Override
    public IdRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject jokesObject = jsonObject.getAsJsonObject("jokes");
        JsonObject idRangeObject = jokesObject.getAsJsonObject("idRange");

        // Deserializar el objeto idRange como un Map
        Map<String, List<Integer>> ranges = new Gson().fromJson(idRangeObject, Map.class);

        // Crear una instancia de IdRange y asignar el Map
        IdRange idRange = new IdRange();
        idRange.setRanges(ranges);

        return idRange;
    }
}