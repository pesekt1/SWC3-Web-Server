package swc3.server.PrimaryDatasource.services.tutorial;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import swc3.server.PrimaryDatasource.models.Tutorial;

import java.util.List;

public interface TutorialService {
    ResponseEntity<Tutorial> getById(long id);
    ResponseEntity<List<Tutorial>> getAll(String title);
    ResponseEntity<Tutorial> create(Tutorial tutorial);
    ResponseEntity<Tutorial> update(long id, Tutorial tutorial);
    ResponseEntity<HttpStatus> delete(long id);
    ResponseEntity<HttpStatus> deleteAll();
    ResponseEntity<List<Tutorial>> findByPublished(boolean published);
}
