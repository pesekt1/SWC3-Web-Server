package swc3.server.PrimaryDatasource.services.tutorial;
import swc3.server.PrimaryDatasource.models.Tutorial;

import java.util.List;

public interface TutorialService2 {
    Tutorial getById(long id);
    List<Tutorial> getAll(String title);
    void create(Tutorial tutorial);
    void update(long id, Tutorial tutorial);
    void delete(long id);
    void deleteAll();
    List<Tutorial> findByPublished(boolean published);
}
