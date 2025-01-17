package jokeapi;

import jokeapi.dao.ChisteDAOFactory;
import jokeapi.dao.DAO;
import jokeapi.jpa.JokeAPIJpaManager;
import jokeapi.model.Chiste;

public class Main {
    public static void main(String[] args) {
        DAO<Chiste> dao = ChisteDAOFactory.getBookDAO(ChisteDAOFactory.TipoDao.JPA_H2);
        dao.downloadAll();
        JokeAPIJpaManager.close(JokeAPIJpaManager.JOKE_H2);

//        Map<String, String> properties = new HashMap<>();
//        properties.put("javax.persistence.jdbc.url", "jdbc:h2:.\\src\\main\\resources\\BBDD\\JokeApi;DB_CLOSE_ON_EXIT=TRUE;DATABASE_TO_UPPER=FALSE;FILE_LOCK=NO");
//        properties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
//        properties.put("javax.persistence.jdbc.user", "");
//        properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
//        properties.put("javax.persistence.jdbc.password", "");
//        properties.put("jakarta.persistence.lock.timeout", "100");
//        properties.put("jakarta.persistence.query.timeout", "100");
//        properties.put("jakarta.persistence.validation.mode", "NONE");
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("newPersistence", properties);
    }
}
