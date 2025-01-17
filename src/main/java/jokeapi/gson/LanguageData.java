package jokeapi.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageData {
    @SerializedName("defaultLanguage")
    private String defaultLanguage;

    @SerializedName("jokeLanguages")
    private List<String> jokeLanguages;

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public List<String> getJokeLanguages() {
        return jokeLanguages;
    }
}