package jokeapi.dao;

import jokeapi.model.Chiste;

import java.util.List;

public class JPAH2DAO implements DAO<Chiste>{
    @Override
    public Chiste get(long id) {
        return null;
    }

    @Override
    public List<Chiste> getAll() {
        return List.of();
    }

    @Override
    public void save(Chiste chiste) {

    }

    @Override
    public void update(Chiste chiste) {

    }

    @Override
    public void delete(Chiste chiste) {

    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public List<Long> getAllIds() {
        return List.of();
    }
}
