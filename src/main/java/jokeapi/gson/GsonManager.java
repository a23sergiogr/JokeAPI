package jokeapi.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jokeapi.model.Chiste;
import jokeapi.profesor.GsonJokeManager;

public class GsonManager {
    public static final String API_URL = "https://v2.jokeapi.dev/joke/";
    private static GsonManager instance;
    private final Gson gson;

    private GsonManager(){
        gson = new GsonBuilder()
                .registerTypeAdapter(Chiste.class, new ChisteDeserializer())
                .registerTypeAdapter(IdRange.class, new IdRangeDeserializer())
                .setPrettyPrinting()
                .create();
    }

    public static GsonManager getInstance(){
        if (instance == null) {
            synchronized (GsonJokeManager.class) {
                if (instance == null) {
                    instance = new GsonManager();
                }
            }
        }
        return instance;
    }

    public Gson getGson(){
        return gson;
    }
}
