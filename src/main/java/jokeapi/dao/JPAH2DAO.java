package jokeapi.dao;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jokeapi.gson.GsonManager;
import jokeapi.gson.IdRange;
import jokeapi.gson.LanguageData;
import jokeapi.jpa.JokeAPIJpaManager;
import jokeapi.model.Chiste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JPAH2DAO implements DAO<Chiste>{
    EntityManager jpaManager = JokeAPIJpaManager.getEntityManager(JokeAPIJpaManager.JOKE_H2);
    Gson gson = GsonManager.getInstance().getGson();

    @Override
    public Chiste get(int id) {
        return jpaManager.find(Chiste.class, id);
    }

    @Override
    public List<Chiste> getAll() {
        TypedQuery<Chiste> typedQuery = jpaManager.createQuery("select c from Chiste c", Chiste.class);
        return typedQuery.getResultList();
    }

    @Override
    public void save(Chiste chiste) {
        jpaManager.getTransaction().begin();
        jpaManager.persist(chiste);
        jpaManager.getTransaction().commit();
    }

    @Override
    public void update(Chiste chiste) {
        jpaManager.getTransaction().begin();
        jpaManager.persist(chiste);
        jpaManager.getTransaction().commit();
    }

    @Override
    public void delete(Chiste chiste) {
        jpaManager.getTransaction().begin();
        jpaManager.persist(chiste);
        jpaManager.getTransaction().commit();
    }

    @Override
    public boolean deleteById(int id) {
        delete(get(id));
        return true;
    }

    @Override
    public List<Integer> getAllIds() {
        Query query = jpaManager.createNamedQuery("getAllIds");
        return query.getResultList();
    }

    @Override
    public void downloadNextIDs() {
        jpaManager.getTransaction().begin();

        List<Integer> list = getAllIds();
        Integer lastId = 0;
        if (!list.isEmpty()){
            lastId = list.getLast();
        }

        for (int i = lastId + 1; i < lastId + 101; i++) {
            try {
                String BASE_URI = "https://v2.jokeapi.dev/joke/Any?idRange=__ID__";
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
    }

    @Override
    public void downloadAll() {
        Runnable runnable = new DownloaderDeamon();
        runnable.run();
    }

    public class DownloaderDeamon implements Runnable {
        private final String BASE_URL_WITH_LANGUAGES = "https://v2.jokeapi.dev/joke/Any?lang=__LANG__&idRange=__ID__";
        private final String ID_RANGE_URI = "https://v2.jokeapi.dev/info";
        private static final int MAX_REQUESTS_PER_MINUTE = 100;
        private int requestsMade = 0;

        @Override
        public void run() {
            IdRange idRange = getIdRange();

            idRange.getRanges().forEach((lang , range) -> {
                String url = BASE_URL_WITH_LANGUAGES.replace("__LANG__", lang);
                downloadFromURL(url, range);
            });
        }

        IdRange getIdRange(){
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URI(ID_RANGE_URI).toURL();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    System.err.println("Error al leer los datos de los ids: ");
                }

            } catch (MalformedURLException | URISyntaxException e) {
                System.err.println("Error con la URI");
                return null;
            }
            return gson.fromJson(sb.toString(), IdRange.class);
        }

        void downloadFromURL(String strUrl, List<Integer> range) {
            jpaManager.getTransaction().begin();

            for (int i = range.get(0); i < range.get(1); i++) {
                try {
                    URL url = new URI(strUrl.replace("__ID__", String.valueOf(i))).toURL();

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

                    requestsMade++;

                    if (requestsMade >= MAX_REQUESTS_PER_MINUTE) {
                        System.out.println("Límite de 100 peticiones alcanzado, esperando 60 segundos...");
                        Thread.sleep(60000); // Dormir por 60,000 milisegundos (1 minuto)
                        requestsMade = 0;
                    }

                } catch (Exception e) {
                    System.err.println("Error procesando el chiste con ID: " + i + " - " + e.getMessage());
                }
            }

            jpaManager.getTransaction().commit();
        }
    }
}
