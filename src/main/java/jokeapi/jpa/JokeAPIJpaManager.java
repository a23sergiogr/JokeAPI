package jokeapi.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;


public class JokeAPIJpaManager {
    public static final String JOKE_H2 = "JokeAPI";
    private static final Map<String, EntityManagerFactory> instancies = new HashMap<>();

    private JokeAPIJpaManager() {
    }

    private static boolean isEntityManagerFactoryClosed(String unidadPersistencia) {
        return !instancies.containsKey(unidadPersistencia) || instancies.get(unidadPersistencia)== null ||
                !instancies.get(unidadPersistencia).isOpen();
    }

    public static EntityManagerFactory getEntityManagerFactory(String unidadPersistencia) {
        if (isEntityManagerFactoryClosed(unidadPersistencia)) {
            synchronized (JokeAPIJpaManager.class) {
                if (isEntityManagerFactoryClosed(unidadPersistencia)) {
                    try {
                        instancies.put(unidadPersistencia, Persistence.createEntityManagerFactory(unidadPersistencia));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instancies.get(unidadPersistencia);
    }

    public static EntityManager getEntityManager(String persistenceUnitName) {
        return getEntityManagerFactory(persistenceUnitName).createEntityManager();
    }

    public static void close(String persistenceUnitName) {
        if (instancies.containsKey(persistenceUnitName)) {
            instancies.get(persistenceUnitName).close();
            instancies.remove(persistenceUnitName);
        }
    }
}
