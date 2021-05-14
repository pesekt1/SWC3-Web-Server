package swc3.server.PrimaryDatasource.controller.JDBC.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> getAll();

    List<T> getAllVulnerable(String filter);

    void create(T t);

    Optional<T> getById(int id);

    void update(T t, int id);

    void delete(int id);

}