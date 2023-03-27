package kaurami.me.t.springdemo.data;


import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.data.DataUploader;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.service.bookReader.BookReader;
import org.junit.jupiter.api.*;
import org.mockito.*;


import java.util.*;

import static org.mockito.Mockito.*;

public class DataUploaderTest {


    MockitoSession session;
    LinkedList<LinkedList<String>> data;
    Map<String, Map<String, List<String>>> fields;

    @Mock
    BookReader reader;
    @Mock
    AgentRepository agentRepository;
    @Mock
    ClientRepository clientRepository;


    @Spy
    DataUploader dbController;

    @BeforeEach
    void beforeEach() {
        session = Mockito.mockitoSession().initMocks(this).startMocking();
        data = new LinkedList<>();
        data.addFirst(new LinkedList<>(Arrays.asList(
                "Код",
                "Клиент",
                "ИНН",
                "Хозяин (сети):",
                "Агент",
                "Группа клиента",
                "Нач. договора",
                "адрес"
        )));
        data.add(new LinkedList<>(Arrays.asList(
                "11205704",
                "Кузнецов Ю.С,ИП_П027",
                "235300263621",
                "",
                "Агент Корма Кр.-027 Водяная Наталья Ивановна",
                "ПКСто 027 Водяная Наталья Ивановна_П027",
                "20.01.2022",
                "г.Тимашевск, ул.50 лет Октября, 241, с 9 до 17, Б/П!"
        )));
        data.add(new LinkedList<>(Arrays.asList(
                "11205711",
                "Плеских С.А,ИП_П027",
                "231100167560",
                "Плеских С.А,ИП НЕСТЛЕ",
                "Агент Корма Кр.-027 Водяная Наталья Ивановна",
                "ПКСто 027 Водяная Наталья Ивановна_П027",
                "21.01.2022",
                "г.Тимашевск, ул.Интернациональная, 16, 9.00-18.00, б/в, с печатью!!"
        )));

        fields = new HashMap<>();
        fields.put("CLIENTS_LIST", new HashMap<String, List<String>>(){{
            put("accessID", new LinkedList<>(Arrays.asList("Код")));
            put("name", new LinkedList<>(Arrays.asList("Клиент")));
            put("individualTaxpayerNumber", new LinkedList<>(Arrays.asList("ИНН")));
            put("marketOwnerName", new LinkedList<>(Arrays.asList("Хозяева сети", "Хозяин (сети):")));
            put("agentName", new LinkedList<>(Arrays.asList("Агент")));
            put("folderName", new LinkedList<>(Arrays.asList("Группа клиента")));
            put("contractDate", new LinkedList<>(Arrays.asList("Нач. договора")));
            put("address", new LinkedList<>(Arrays.asList("адрес")));
        }});
        when(reader.readSheet()).thenReturn(data);
        when(agentRepository.save(any())).then(invocationOnMock -> {
            Agent agent = ((Agent) (invocationOnMock.getArguments()[0]));
            System.out.println(agent);
            return agent;
        });
        when(clientRepository.save(any())).then(invocationOnMock -> {
            Client client = ((Client) (invocationOnMock.getArguments()[0]));
            System.out.println(client);
            return client;
        });

    }

    @Test
    @DisplayName("Data loader: upload test")
    void Test() {

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

    @AfterEach
    void afterEach(){
        session.finishMocking();
    }


}
