package me.t.kaurami.reportCreator;

import me.t.kaurami.TestDataLoader;
import me.t.kaurami.service.reportprocessor.ReportProcessorCheckingRelationship;
import me.t.kaurami.service.setting.SettingHolder;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.HashMap;

@SpringBootTest
public class ReportProcessorCheckingRelationshipTest {

    private MockitoSession session;

    @MockBean
    SettingHolder settingHolder;
    @SpyBean
    ReportProcessorCheckingRelationship reportCreator;




    private static TestDataLoader testDataLoader;
    private static HashMap<String, Integer> controlValues;
    @BeforeAll
    private static void setTestData(){
        testDataLoader = new TestDataLoader("C://TestData.txt");
        controlValues = new HashMap<String, Integer>(){{
            put("agents", 7);
            put("clients", 12);
        }};
    }

    @BeforeEach
    private void initMock(){
        session = Mockito.mockitoSession().initMocks(this).startMocking();
        System.out.println(Mockito.mockitoSession());
        System.out.println(settingHolder);
        Mockito.when(settingHolder.getNamePattern()).thenReturn("^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ");
        Mockito.when(settingHolder.getType()).thenReturn(SettingHolder.ReportType.CHECKING_RELATIONSHIP);
    }

@Test
    @DisplayName("relationship report creator test")
    public void test(){
/*        List<Exportable> results = reportCreator.createReport(testDataLoader.getSourceData());
        System.out.println(results);*/

    }


    @AfterEach
    public void afterEach(){
        session.finishMocking();
    }

}
