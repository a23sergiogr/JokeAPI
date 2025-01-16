package jokeapi;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jokeapi.gson.GsonManager;
import jokeapi.model.Chiste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    private static String BASE_URI = "https://v2.jokeapi.dev/joke/Any?idRange=__ID__";
    public static void main(String[] args) {
        saveAll();
    }

    public static void saveAll(){
        EntityManager jpaManager = Persistence.createEntityManagerFactory("JokeAPI").createEntityManager();
        Gson gson = GsonManager.getInstance().getGson();

        jpaManager.getTransaction().begin();

        for (int i = 0; i < 150; i++) {
            try {
                URL url = new URI(BASE_URI.replace("__ID__", String.valueOf(i))).toURL();

                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    System.err.println("Error al leer los datos de la API para ID: " + i);
                    continue;
                }

                Chiste chiste = gson.fromJson(sb.toString(), Chiste.class);

                if (chiste != null && chiste.getChiste() != null) {
                    jpaManager.persist(chiste);
                }

            } catch (Exception e) {
                System.err.println("Error procesando el chiste con ID: " + i + " - " + e.getMessage());
            }
        }

        jpaManager.getTransaction().commit();
        jpaManager.close();
    }

    public static void saveOne(String id){
        Gson gson = GsonManager.getInstance().getGson();

        URL url = null;
        try {
            url = new URI(BASE_URI.replace("__ID__", id)).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            System.err.println("Error con la URL");
        }

        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            System.err.println("Error al coger datos de la api");
        }
        Chiste chiste = gson.fromJson(sb.toString(), Chiste.class);
        System.out.println(chiste);

        //EntityManagerFactory managerFactory = JokeAPIJpaManager.getEntityManagerFactory(JokeAPIJpaManager.JOKE_H2);
        //EntityManager jpaManager = managerFactory.createEntityManager();
        //EntityManager jpaManager = JokeAPIJpaManager.getEntityManager("JokeAPI");
        EntityManager jpaManager = Persistence.createEntityManagerFactory("JokeAPI").createEntityManager();
        jpaManager.getTransaction().begin();
        jpaManager.merge(chiste);
        jpaManager.getTransaction().commit();
    }
}
