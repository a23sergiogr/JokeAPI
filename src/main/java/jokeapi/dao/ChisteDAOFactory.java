package jokeapi.dao;

import jokeapi.model.Chiste;

public class ChisteDAOFactory {
    public enum TipoDao {
        JPA_H2;
    }

    public static DAO<Chiste> getBookDAO(TipoDao tipo) {
        switch (tipo) {
            case JPA_H2 -> {
                return new JPAH2DAO();
            }
        }
        return null;
    }
}
