package swc3.server.PrimaryDatasource.services.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.repository.TutorialRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialServiceImpl2 implements TutorialService2{
    TutorialRepository tutorialRepository;

    @Autowired
    public TutorialServiceImpl2(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    private String errorMessage(long id){
        return "Not found Tutorial with id = " + id;
    }

    @Override
    public Tutorial getById(long id) {
        return tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Tutorial> getAll(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null)
            tutorials.addAll(tutorialRepository.findAll());
        else
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));

        return tutorials;
    }

    @Override
    public void create(Tutorial tutorial) {
        tutorialRepository.save(
                new Tutorial(
                        tutorial.getTitle(),
                        tutorial.getDescription(),
                        false));
    }

    @Override
    public void update(long id, Tutorial tutorial) {
        var updatedTutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));

        updatedTutorial.setTitle(tutorial.getTitle());
        updatedTutorial.setDescription(tutorial.getDescription());
        updatedTutorial.setPublished(tutorial.getPublished());
    }

    @Override
    public void delete(long id) {
        if (!tutorialRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));

        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return tutorialRepository.findByPublished(published);
    }
}