package swc3.server.Datasources.Db5.services;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.repo.TutorialMongoRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.*;

class TutorialMongoServiceTest {

    @Mock private TutorialMongoRepository tutorialMongoRepository;
    TutorialMongoService tutorialMongoService;
    ObjectId FAKE_TUTORIAL_ID = ObjectId.get();
    TutorialMongo fakeTutorial; // = new TutorialMongo("title1", "description1", true);
    List<TutorialMongo> fakeTutorials;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //we need to initialize mocks
        tutorialMongoService = new TutorialMongoService(tutorialMongoRepository); //injecting the mock

        fakeTutorial = new TutorialMongo("title1", "description1", true);
        fakeTutorial.setId(FAKE_TUTORIAL_ID);
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class GetByIdTest{
        @Test
        @DisplayName("should get tutorial with given id")
        void should_get_tutorial_by_Id() { //could be separated into multiple tests
            //given
            Mockito.when(tutorialMongoRepository.findById(fakeTutorial.getId())).thenReturn(Optional.of(fakeTutorial)); //mock the method
            //when
            var response = tutorialMongoService.getById(fakeTutorial.getId()); //testing the service
            //then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorial);
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorial, HttpStatus.OK)); // a bit extra test
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            Optional<TutorialMongo> empty = Optional.empty();
            Mockito.when(tutorialMongoRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(empty); //mock the method
            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialMongoService.getById(FAKE_TUTORIAL_ID) //when
            ).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class GetAllTest{
        @BeforeEach
        void setUp(){
            fakeTutorials = getFakeTutorials();
        }

        @Test
        @DisplayName("should send response body with all tutorials")
        void should_send_response_body_with_all_tutorials() {
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(fakeTutorials); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorials); //then
        }

        @Test
        @DisplayName("should send response status ok")
        void should_send_response_status_ok(){
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(fakeTutorials); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //then
        }

        @Test
        @DisplayName("should send status NOT_FOUND if no tutorials")
        void status_not_found_if_no_tutorials_found(){
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(new ArrayList<>()); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT)); //then
        }
    }

    @Nested
    class CreateTutorialTest{
        TutorialMongo newTutorial;

        @BeforeEach
        void setUp(){
            //given
            newTutorial = new TutorialMongo("title1", "description1", false);
            Mockito.when(tutorialMongoRepository.save(ArgumentMatchers.any(TutorialMongo.class))).thenReturn(fakeTutorial);
        }

        @Test
        @DisplayName("should call repository save method once")
        void should_call_TutorialMongoRepository_save_method(){
            tutorialMongoService.create(newTutorial); //when
            Mockito.verify(tutorialMongoRepository, Mockito.times(1)).save(ArgumentMatchers.any(TutorialMongo.class)); //then
        }

        @Test
        @DisplayName("should send response body with new tutorial")
        void should_return_response_with_new_tutorial() {
            var response = tutorialMongoService.create(newTutorial); //when
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorial); //then
        }

        @Test
        @DisplayName("should send response wuth status created")
        void should_return_response_with_status_created(){
            var response = tutorialMongoService.create(newTutorial); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        @DisplayName("should return response with created tutorial and status created")
        void should_return_response_with_tutorial_and_status_created(){
            var response = tutorialMongoService.create(newTutorial); //when
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(fakeTutorial, HttpStatus.CREATED)); //then
        }
    }

    @Nested
    class UpdateTutorialTest{
        TutorialMongo updatedTutorial;

        @BeforeEach
        void setUp(){
            //given
            updatedTutorial = new TutorialMongo("updatedTitle", "updatedDescription", !fakeTutorial.getPublished());
            updatedTutorial.setId(FAKE_TUTORIAL_ID);

            Mockito.when(tutorialMongoRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(Optional.of(fakeTutorial)); //mock the method
            Mockito.when(tutorialMongoRepository.save(ArgumentMatchers.any(TutorialMongo.class))).then(AdditionalAnswers.returnsFirstArg()); //mock the method
        }

        @Test
        void should_call_TutorialRepository_save_method() {
            tutorialMongoService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when
            Mockito.verify(tutorialMongoRepository, Mockito.times(1)).save(ArgumentMatchers.any(TutorialMongo.class)); //then
        }

        @Test
        void should_send_response_with_updated_tutorial() {
            var response = tutorialMongoService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when

            //if we have overridden equals method for that class
            Assertions.assertThat(response.getBody()).isEqualTo(updatedTutorial);

            // if we dont have overridden equals method for that class
            Assertions.assertThat(Objects.equals(response.getBody(), updatedTutorial));

            // if we want to iterate over the fields and check them one by one - better to use Objects.equals(o1,o2)
            for(int i = 0; i < 3; i++){
                Assertions.assertThat(
                        Objects.requireNonNull(response.getBody()).getClass().getDeclaredFields()[i])
                        .isEqualTo(updatedTutorial.getClass().getDeclaredFields()[i]);
            }
        }

        @Test
        void should_send_response_with_statusOK() {
            var response = tutorialMongoService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //then
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            Mockito.when(tutorialMongoRepository.findById(FAKE_TUTORIAL_ID)).thenReturn(Optional.empty());

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialMongoService.update(FAKE_TUTORIAL_ID, updatedTutorial) //when
            ).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class DeleteTutorialTest{
        @BeforeEach
        void setUp(){
            Mockito.when(tutorialMongoRepository.existsById(FAKE_TUTORIAL_ID)).thenReturn(true); //mock the method
        }

        @Test
        void should_return_response_with_status_no_content() {
            var response = tutorialMongoService.deleteById(FAKE_TUTORIAL_ID); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        void should_return_response_with_empty_body() {
            var response = tutorialMongoService.deleteById(FAKE_TUTORIAL_ID); //when
            Assertions.assertThat(response.getBody()).isNull();
        }

        @Test
        void should_call_repository_deleteById_method(){
            tutorialMongoService.deleteById(FAKE_TUTORIAL_ID); //when
            Mockito.verify(tutorialMongoRepository, Mockito.times(1)).deleteById(FAKE_TUTORIAL_ID); //then
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){
            //given
            ObjectId NON_EXISTING_TUTORIAL_ID = ObjectId.get();
            Mockito.when(tutorialMongoRepository.existsById(NON_EXISTING_TUTORIAL_ID)).thenReturn(false); //mock the method

            //then
            Assertions.assertThatThrownBy(
                    () -> tutorialMongoService.deleteById(NON_EXISTING_TUTORIAL_ID) //when
            ).isInstanceOf(ResourceNotFoundException.class);

            //check that exception is not thrown
            org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> tutorialMongoService.deleteById(FAKE_TUTORIAL_ID));
        }
    }

    @Nested
    class DeleteAllTutorials{

        @Test
        void should_return_response_with_status_no_content() {
            var response = tutorialMongoService.deleteAll(); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT); //then
        }

        @Test
        void should_return_response_with_empty_body() {
            var response = tutorialMongoService.deleteAll(); //when
            Assertions.assertThat(response.getBody()).isNull(); //then
        }

        @Test
        void should_call_repository_deleteById_method(){
            tutorialMongoService.deleteAll(); //when
            Mockito.verify(tutorialMongoRepository, Mockito.times(1)).deleteAll(); //then
        }

    }

    private List<TutorialMongo> getFakeTutorials() {
        var tut1 = new TutorialMongo("Tut#1", "Desc#1", true);
        tut1.setId(ObjectId.get());
        var tut2 = new TutorialMongo("Tut#2", "Desc#2", false);
        tut2.setId(ObjectId.get());
        var tut3 = new TutorialMongo("Tut#3", "Desc#3", true);
        tut3.setId(ObjectId.get());
        return new ArrayList<>(Arrays.asList(tut1,tut2,tut3));
    }
}