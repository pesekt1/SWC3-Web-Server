package swc3.server.PrimaryDatasource.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.repository.TutorialRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.*;

class TutorialServiceTest {

//    private final ApplicationContextRunner runner = new ApplicationContextRunner()
//            .withConfiguration(UserConfigurations.of(TutorialService.class));

    //private final TutorialRepository tutorialRepository = Mockito.mock(TutorialRepository.class); //mock
    @Mock private TutorialRepository tutorialRepository; //mock annotation instead of = Mockito.mock(TutorialRepository.class);
    private TutorialService tutorialService;

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //we need to initialize mocks
        tutorialService = new TutorialService(tutorialRepository); //using tutorialRepository mock
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class GetAllTutorialsTest{
        @Test
        @DisplayName("should get all tutorials with given title")
        void should_get_tutorials_for_given_title() {
            //given
            String title1 = "Tut#1";
            List<Tutorial> listFilteredTutorials = new ArrayList<>(Collections.singletonList(new Tutorial(title1, "Desc#1", true)));
            Mockito.when(tutorialRepository.findByTitleContaining(title1)).thenReturn(listFilteredTutorials); //return tut1

            //when
            var response = tutorialService.getAllTutorials(title1);

            //then
            Assertions.assertThat(response.getBody()).isEqualTo(listFilteredTutorials);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(listFilteredTutorials, HttpStatus.OK));
        }

        @Test
        @DisplayName("should respond with status NOT_FOUND if no tutorials found with given title")
        void status_not_found_if_no_tutorials_found_for_given_title(){
            //given - when - then
            String title1 = "Tut#1";
            Mockito.when(tutorialRepository.findByTitleContaining(title1)).thenReturn(new ArrayList<>()); //empty list
            var response = tutorialService.getAllTutorials(title1);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

        @Test
        @DisplayName("should get all tutorials")
        void should_get_all_tutorials_if_title_not_provided() {
            //given
            var tut1 = new Tutorial("Tut#1", "Desc#1", true);
            var tut2 = new Tutorial("Tut#2", "Desc#2", false);
            var tut3 = new Tutorial("Tut#3", "Desc#3", true);
            List<Tutorial> fakeTutorials = new ArrayList<>(Arrays.asList(tut1,tut2,tut3));

            Mockito.when(tutorialRepository.findAll()).thenReturn(fakeTutorials); //return fake tutorials list

            //when
            var response = tutorialService.getAllTutorials(null);

            //then
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorials);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorials, HttpStatus.OK));
        }

        @Test
        @DisplayName("should respond with status NOT_FOUND if there are no tutorials")
        void status_not_found_if_no_tutorials_found(){
            //given - when - then
            Mockito.when(tutorialRepository.findAll()).thenReturn(new ArrayList<>()); //empty list
            var response = tutorialService.getAllTutorials(null);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
    }

    @Nested
    class GetTutorialByIdTest{
        @Test
        @DisplayName("should get tutorial with given id")
        void should_get_tutorial_by_Id() {
            //given
            //comes from before each

            var fakeTutorial = new Tutorial("title1", "description1", true);
            long fakeId = fakeTutorial.getId();

            Mockito.when(tutorialRepository.findById(fakeId)).thenReturn(Optional.of(fakeTutorial)); //mock the method

            //when
            var response = tutorialService.getTutorialById(fakeId); //testing the service

            //then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorial);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorial, HttpStatus.OK));
            //the last one is a bit extra
        }
        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            long id = 1;
            Optional<Tutorial> empty = Optional.empty();
            Mockito.when(tutorialRepository.findById(id)).thenReturn(empty); //mock the method

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialService.getTutorialById(id)
            ).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Test
    void createTutorial() {
    }

    @Test
    void updateTutorial() {
    }

    @Test
    void deleteTutorial() {
    }

    @Test
    void deleteAllTutorials() {
    }

    @Test
    void findByPublished() {
    }
}