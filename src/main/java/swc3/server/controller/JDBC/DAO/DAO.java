package swc3.server.controller.JDBC.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> list();

    List<T> listVulnerable(String filter);

    void create(T t);

    Optional<T> get(int id);

    void update(T t, int id);

    void delete(int id);

}