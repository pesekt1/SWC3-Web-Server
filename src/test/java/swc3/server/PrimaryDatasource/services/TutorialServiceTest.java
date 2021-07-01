package swc3.server.PrimaryDatasource.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import swc3.server.PrimaryDatasource.models.Tutorial;
import swc3.server.PrimaryDatasource.repository.TutorialRepository;
import swc3.server.PrimaryDatasource.services.tutorial.TutorialServiceImpl;
import swc3.server.exception.ResourceNotFoundException;

import java.util.*;

// unit tests - isolate the methods from their dependencies
// ...using mocks instead of the dependencies

//NOTE: We can use Assertions.assertThat(response.getBody()).isEqualTo(updatedTutorial) because we have overriden equals method for Tutorial class.
//otherwise it would compare the object addresses and it would fail.

class TutorialServiceTest {

//    private final ApplicationContextRunner runner = new ApplicationContextRunner()
//            .withConfiguration(UserConfigurations.of(TutorialService.class));

    //private final TutorialRepository tutorialRepository = Mockito.mock(TutorialRepository.class); //mock
    @Mock private TutorialRepository tutorialRepository; //mock annotation instead of = Mockito.mock(TutorialRepository.class);
    private TutorialServiceImpl tutorialService;
    long FAKE_TUTORIAL_ID = 1;

    @BeforeAll
    static void beforeAll() { }

    @AfterAll
    static void afterAll() { }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //we need to initialize mocks
        tutorialService = new TutorialServiceImpl(tutorialRepository); //using tutorialRepository mock
    }

    @AfterEach
    void tearDown() { }

    @Nested //grouping tests together
    class GetAllTutorialsTest{

        @Test
        @DisplayName("should get all tutorials with given title")
        void should_get_tutorials_for_given_title() {
            //given
            String title1 = "Tut#1";
            List<Tutorial> listFilteredTutorials = new ArrayList<>(Collections.singletonList(new Tutorial(title1, "Desc#1", true)));
            Mockito.when(tutorialRepository.findByTitleContaining(title1)).thenReturn(listFilteredTutorials); //return tut1

            //when
            var response = tutorialService.getAll(title1);

            //then
            Assertions.assertThat(response.getBody()).isEqualTo(listFilteredTutorials);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(listFilteredTutorials, HttpStatus.OK)); // a bit extra test
        }

        @Test
        @DisplayName("should respond with status NOT_FOUND if no tutorials found with given title")
        void status_not_found_if_no_tutorials_found_for_given_title(){
            //given
            String title1 = "Tut#1";
            Mockito.when(tutorialRepository.findByTitleContaining(title1)).thenReturn(new ArrayList<>()); //empty list

            //when
            var response = tutorialService.getAll(title1);

            //then
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

        @Test
        @DisplayName("should get all tutorials")
        void should_get_all_tutorials_if_title_not_provided() {
            //given
            List<Tutorial> fakeTutorials = getFakeTutorials();
            Mockito.when(tutorialRepository.findAll()).thenReturn(fakeTutorials); //return fake tutorials list

            //when
            var response = tutorialService.getAll(null);

            //then
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorials);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorials, HttpStatus.OK)); // a bit extra test
        }

        @Test
        @DisplayName("should respond with status NOT_FOUND if there are no tutorials")
        void status_not_found_if_no_tutorials_found(){
            //given
            Mockito.when(tutorialRepository.findAll()).thenReturn(new ArrayList<>()); //empty list

            //when
            var response = tutorialService.getAll(null);

            //then
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }

    }

    @Nested // grouping related tests together
    class GetTutorialByIdTest{

        @Test
        @DisplayName("should get tutorial with given id")
        void should_get_tutorial_by_Id() {
            //given
            var fakeTutorial = new Tutorial("title1", "description1", true);
            //long fakeId = fakeTutorial.getId();
            fakeTutorial.setId(FAKE_TUTORIAL_ID);
            Mockito.when(tutorialRepository.findById(fakeTutorial.getId())).thenReturn(Optional.of(fakeTutorial)); //mock the method

            //when
            var response = tutorialService.getById(fakeTutorial.getId()); //testing the service

            //then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorial);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorial, HttpStatus.OK)); // a bit extra test
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            Optional<Tutorial> empty = Optional.empty();
            Mockito.when(tutorialRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(empty); //mock the method

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialService.getById(FAKE_TUTORIAL_ID) //when
            ).isInstanceOf(ResourceNotFoundException.class);
        }

    }

    @Nested
    class CreateTutorialTest{

        Tutorial newTutorial;
        @BeforeEach
        void setUp(){
            //given
            newTutorial = new Tutorial("title1", "description1", false);
            Mockito.when(tutorialRepository.save(newTutorial)).thenReturn(newTutorial);
        }

        @Test
        void should_call_TutorialRepository_save_method(){
            tutorialService.create(newTutorial); //when
            Mockito.verify(tutorialRepository, Mockito.times(1)).save(ArgumentMatchers.any(Tutorial.class)); //then
        }

        @Test
        void should_return_response_with_new_tutorial() {
            var response = tutorialService.create(newTutorial); //when
            Assertions.assertThat(response.getBody()).isEqualTo(newTutorial); //then
        }

        @Test
        void should_return_response_with_status_created(){
            var response = tutorialService.create(newTutorial); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void should_return_response_with_tutorial_and_status_created(){
            var response = tutorialService.create(newTutorial); //when
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(newTutorial, HttpStatus.CREATED)); //then
        }
    }

    @Nested
    class UpdateTutorialTest{

        Tutorial fakeTutorial;

        Tutorial updatedTutorial;
        @BeforeEach
        void setUp(){
            //given
            fakeTutorial = new Tutorial("title1", "description1", true);
            fakeTutorial.setId(FAKE_TUTORIAL_ID);

            updatedTutorial = new Tutorial("updatedTitle", "updatedDescription", !fakeTutorial.getPublished());
            updatedTutorial.setId(FAKE_TUTORIAL_ID);

            Mockito.when(tutorialRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(Optional.of(fakeTutorial)); //mock the method
            Mockito.when(tutorialRepository.save(ArgumentMatchers.any(Tutorial.class))).then(AdditionalAnswers.returnsFirstArg()); //mock the method
        }

        @Test
        void should_call_TutorialRepository_save_method() {
            tutorialService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when
            Mockito.verify(tutorialRepository, Mockito.times(1)).save(ArgumentMatchers.any(Tutorial.class)); //then
        }

        @Test
        void should_send_response_with_updated_tutorial() {
            var response = tutorialService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when
            Assertions.assertThat(response.getBody()).isEqualTo(updatedTutorial); //then
        }

        @Test
        void should_send_response_with_statusOK() {
            var response = tutorialService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //then
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            Optional<Tutorial> empty = Optional.empty();
            Mockito.when(tutorialRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(empty); //mock the method

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialService.update(FAKE_TUTORIAL_ID, updatedTutorial) //when
            ).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class DeleteTutorialTest{

        @BeforeEach
        void setUp(){
            Mockito.when(tutorialRepository.existsById(FAKE_TUTORIAL_ID)).thenReturn(true); //mock the method
        }

        @Test
        void should_return_response_with_status_no_content() {
            var response = tutorialService.delete(FAKE_TUTORIAL_ID); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        void should_return_response_with_empty_body() {
            var response = tutorialService.delete(FAKE_TUTORIAL_ID); //when
            Assertions.assertThat(response.getBody()).isNull();
        }

        @Test
        void should_call_repository_deleteById_method(){
            tutorialService.delete(FAKE_TUTORIAL_ID); //when
            Mockito.verify(tutorialRepository, Mockito.times(1)).deleteById(FAKE_TUTORIAL_ID); //then
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            long NON_EXISTING_TUTORIAL_ID = 2;
            Mockito.when(tutorialRepository.existsById(NON_EXISTING_TUTORIAL_ID)).thenReturn(false); //mock the method

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialService.delete(NON_EXISTING_TUTORIAL_ID) //when
            ).isInstanceOf(ResourceNotFoundException.class);

            org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> tutorialService.delete(FAKE_TUTORIAL_ID));
        }
    }

    @Nested
    class DeleteAllTutorials{

        @Test
        void should_return_response_with_status_no_content() {
            var response = tutorialService.deleteAll(); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT); //then
        }

        @Test
        void should_return_response_with_empty_body() {
            var response = tutorialService.deleteAll(); //when
            Assertions.assertThat(response.getBody()).isNull(); //then
        }

        @Test
        void should_call_repository_deleteById_method(){
            tutorialService.deleteAll(); //when
            Mockito.verify(tutorialRepository, Mockito.times(1)).deleteAll(); //then
        }

    }

    @Nested
    class FindByPublishedTest{

        @Test
        void should_find_published_tutorials() {
            //given
            List<Tutorial> fakeTutorials = getFakeTutorials();
            Mockito.when(tutorialRepository.findByPublished(true)).thenReturn(fakeTutorials);

            var response = tutorialService.findByPublished(true); //when

            //then
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorials);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void should_find_not_published_tutorials(){
            Mockito.when(tutorialRepository.findByPublished(false)).thenReturn(new ArrayList<Tutorial>());

            var response = tutorialService.findByPublished(false);

            Assertions.assertThat(response.getBody()).isNull();
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
    }

    private List<Tutorial> getFakeTutorials() {
        var tut1 = new Tutorial("Tut#1", "Desc#1", true);
        tut1.setId(1);
        var tut2 = new Tutorial("Tut#2", "Desc#2", false);
        tut2.setId(2);
        var tut3 = new Tutorial("Tut#3", "Desc#3", true);
        tut3.setId(3);
        return new ArrayList<>(Arrays.asList(tut1,tut2,tut3));
    }
}