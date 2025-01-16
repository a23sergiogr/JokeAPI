package jokeapi.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import jokeapi.model.Chiste;

import java.io.IOException;

public class ChisteTypeAdapter extends TypeAdapter<Chiste> {
    @Override
    public void write(JsonWriter jsonWriter, Chiste chiste) throws IOException {

    }

    @Override
    public Chiste read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Chiste chiste = new Chiste();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "id" -> chiste.setId(jsonReader.nextInt());
                case "category" -> chiste.setCategoria(jsonReader.nextString());
                case "type" -> chiste.setTipo(jsonReader.nextString());
                case "lang" -> chiste.setLenguaje(jsonReader.nextString());
                case "joke", "setup" -> chiste.setChiste(jsonReader.nextString());
                case "delivery" -> chiste.setRespuesta(jsonReader.nextString());
                case "flags" -> {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                        switch (jsonReader.nextName()){
                            case "nsfw" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("nsfw");
                            }
                            case "religious" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("religious");
                            }
                            case "political" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("political");
                            }
                            case "racist" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("racist");
                            }
                            case "sexist" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("sexist");
                            }
                            case "explicit" -> {
                                if(jsonReader.nextBoolean()) chiste.addFlag("explicit");
                            }
                        }
                    }
                    jsonReader.endObject();
                }
            }
        }
        jsonReader.endObject();
        return chiste;
    }
}
