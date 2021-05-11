package swc3.server;

import org.junit.jupiter.api.*;
import swc3.server.PrimaryDatasource.controller.HelloController;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(HelloController.class)
class UnitTests {

    HelloController controller;

//    @Autowired
//    private MockMvc mvc;

//    @Autowired
//    private Environment env;

//    String prefix = "http://localhost:" + env.getProperty("server.port");

//    @Autowired
//    public UnitTests(Environment env) {
//        this.env = env;
//        this.prefix = "http://localhost:" + env.getProperty("server.port");
//    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("before all.");
    }

    @BeforeEach
    void setUp() {
        System.out.println("before each.");
        controller = new HelloController();
    }

    @AfterEach
    void tearDown() {
        System.out.println("after each");

    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @Test
    void hello_with_given_name() {
        String response = controller.hello("Tomas"); // Act
        assertEquals("Hello, Tomas" + "!", response);// Assert
        System.out.println("testing hello_with_given_name");
    }

    //    @Test
//    void hello() throws Exception {
//        RequestBuilder request = get("/hello");
//        MvcResult result = mvc.perform(request).andReturn();
//        assertEquals("Hello, World", result.getResponse().getContentAsString());
//    }
//
//    @Test
//    void testHelloWithName() throws Exception {
//        mvc.perform(get("/hello?name=Dan"))
//                .andExpect(content().string("Hello, Dan"));
//    }


}
