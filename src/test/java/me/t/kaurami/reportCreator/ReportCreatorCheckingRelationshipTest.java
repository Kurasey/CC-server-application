package me.t.kaurami.reportCreator;

import me.t.kaurami.TestDataLoader;
import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.reportCreator.ReportCreatorCheckingRelationship;
import me.t.kaurami.service.setting.SettingHolder;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.HashMap;
import java.util.List;

public class ReportCreatorCheckingRelationshipTest {

    private MockitoSession session;
    @Mock
    SettingHolder settingHolder;
    @Spy
    ReportCreatorCheckingRelationship reportCreator;


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
        Mockito.when(settingHolder.getNamePattern()).thenReturn("^.+ИП|^.+ООО|^.+ПАО|^.+ЗАО|^.+МУП|^.+ГБУ");
    }

    @Test
    @DisplayName("relationship report creator test")
    public void test(){
        reportCreator.setData(testDataLoader.getSourceData());
        reportCreator.setFields(testDataLoader.getFields());
        reportCreator.setSettingHolder(settingHolder);
        List<Exportable> results = reportCreator.createReport();
        System.out.println(results);

    }

    @AfterEach
    public void afterEach(){
        session.finishMocking();
    }

}
