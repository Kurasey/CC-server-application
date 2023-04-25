package me.t.kaurami.data;


import me.t.kaurami.TestDataLoader;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.service.bookReader.BookReader;
import org.junit.jupiter.api.*;
import org.mockito.*;


import java.util.*;

import static org.mockito.Mockito.*;

public class DataUploaderTest {

    @Mock
    BookReader reader;
    @Mock
    AgentRepository agentRepository;
    @Mock
    ClientRepository clientRepository;
    @Spy
    DataUploader dbController;

    static TestDataLoader testDataLoader;
    static Map<String, Integer> controlValues;

    @BeforeAll
    private static void setTestData(){
        testDataLoader = new TestDataLoader("C://TestData.txt");
        controlValues = new HashMap<String, Integer>(){{
            put("agents", 7);
            put("clients", 12);
        }};
    }



    MockitoSession session;
    LinkedList<LinkedList<String>> data;
    Map<String, Map<String, List<String>>> fields = new HashMap<>();
    Map<Agent, List<Client>> result;


    @BeforeEach
    void beforeEach(){
        session = Mockito.mockitoSession().initMocks(this).startMocking();
        setMocks();
        dbController.setAgentRepository(agentRepository);
        dbController.setClientRepository(clientRepository);
        dbController.setReader(reader);
        dbController.setFields(fields);
        try {
            dbController.uploadData(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMocks(){
        when(reader.readSheet()).thenReturn(data);
        when(agentRepository.saveAll(any())).then(invocationOnMock -> {
            Iterable<Agent> iterable = (Iterable<Agent>) invocationOnMock.getArguments()[0];
            iterable.forEach(agent -> {
                result.put(agent, new ArrayList<>());
            });
            return null;
        });

        when(clientRepository.saveAll(any())).then(invocationOnMock -> {
            Iterable<Client> iterable = (Iterable<Client>) invocationOnMock.getArguments()[0];
            iterable.forEach(client -> {
                result.get(client.getAgent()).add(client);
            });
            return null;
        });
    }

    @BeforeEach
    void initData(){
        result = new HashMap<>();
        data = new LinkedList<>(testDataLoader.getSourceData());
        fields = testDataLoader.getFields();
    }

    @Test
    @DisplayName("Test upload agents")
    void uploadAgents() {
        Assertions.assertTrue(result.keySet().size() == controlValues.get("agents"));
    }

    @Test
    @DisplayName("Test upload clients")
    void uploadClients(){
        List<Client> clients = new ArrayList<>();
        result.values().stream().forEach(clients1 -> clients.addAll(clients1));
        Assertions.assertTrue(clients.size() == controlValues.get("clients"));
    }

    @AfterEach
    void afterEach(){
        session.finishMocking();
    }

}
