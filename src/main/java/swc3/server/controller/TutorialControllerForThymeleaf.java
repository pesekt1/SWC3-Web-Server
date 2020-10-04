package swc3.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swc3.server.exception.ResourceNotFoundException;
import swc3.server.model.Tutorial;
import swc3.server.repository.TutorialRepository;

//controller for server-side rendering
@Controller
@RequestMapping("/thymeleaf")
public class TutorialControllerForThymeleaf {
    private final TutorialRepository tutorialRepository;

    public TutorialControllerForThymeleaf(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }


    //html without Bootstrap
    @RequestMapping("/tutorialsBasic")
    public String getTutorialsBasic(Model model){
        model.addAttribute("tutorials", tutorialRepository.findAll());
        return "tutorials/listBasic";
    }

    //html using Bootstrap
    @RequestMapping("/tutorials")
    public String getTutorials(Model model){
        model.addAttribute("tutorials", tutorialRepository.findAll());
        return "tutorials/list";
    }

    //html - advanced web page with update and delete
    @RequestMapping("/tutorialsAdvanced")
    public String getTutorialsAdvanced(Model model){
        model.addAttribute("tutorials", tutorialRepository.findAll());
        return "tutorials/listAdvanced";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTutorial(@PathVariable("id") long id, Model model) {
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid tutorial Id:" + id));
        tutorialRepository.delete(tutorial);
        model.addAttribute("tutorials", tutorialRepository.findAll());
        return "redirect:/thymeleaf/tutorialsAdvanced";
    }

    @RequestMapping("{id}/update")
    public String openTutorialForm(@PathVariable("id") long id, Model model){
        model.addAttribute("tutorial", tutorialRepository.findById(id).get());
        return "tutorials/tutorialForm";
    }

    @PostMapping("tutorialsAdvanced/{id}")
    public String updateTutorial(@PathVariable("id") long id, @ModelAttribute Tutorial tutorial){
        Tutorial _tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());
        tutorialRepository.save(_tutorial);

        return "redirect:/thymeleaf/tutorialsAdvanced";
    }

    @RequestMapping("create")
    public String createTutorialForm(Model model){
        model.addAttribute("tutorial", new Tutorial());
        return "tutorials/createTutorialForm";
    }

    @PostMapping("tutorialsAdvanced")
    public String createTutorial(@ModelAttribute Tutorial tutorial){
        tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
        return "redirect:/thymeleaf/tutorialsAdvanced";
    }

//    @PostMapping("/tutorials")
//    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
//        Tutorial _tutorial = tutorialRepository
//                .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
//        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
//    }

}