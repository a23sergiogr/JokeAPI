package jokeapi.dao;

import java.util.List;

public interface DAO<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
    public boolean deleteById(int id);
    public List<Integer> getAllIds();
    public void downloadNextIDs();
    public void downloadAll();
}
