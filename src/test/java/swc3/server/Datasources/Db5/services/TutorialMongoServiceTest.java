package swc3.server.Datasources.Db5.services;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import swc3.server.Datasources.Db5.models.TutorialMongo;
import swc3.server.Datasources.Db5.repo.TutorialMongoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TutorialMongoServiceTest {

    @Mock private TutorialMongoRepository tutorialMongoRepository;
    TutorialMongoService tutorialMongoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //we need to initialize mocks
        tutorialMongoService = new TutorialMongoService(tutorialMongoRepository); //injecting the mock
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class GetAllTest{
        List<TutorialMongo> fakeTutorials;

        @BeforeEach
        void setUp(){
            fakeTutorials = getFakeTutorials();
        }

        @Test
        void should_send_response_body_with_all_tutorials() {
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(fakeTutorials); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response.getBody()).isEqualTo(fakeTutorials); //then
        }

        @Test
        void should_send_response_status_ok(){
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(fakeTutorials); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); //then
        }

        @Test
        @DisplayName("should respond with status NOT_FOUND if there are no tutorials")
        void status_not_found_if_no_tutorials_found(){
            Mockito.when(tutorialMongoRepository.findAll()).thenReturn(new ArrayList<>()); //given
            var response = tutorialMongoService.getAll(); //when
            Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT)); //then
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