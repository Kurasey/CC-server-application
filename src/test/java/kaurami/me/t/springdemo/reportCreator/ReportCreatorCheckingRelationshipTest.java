package kaurami.me.t.springdemo.reportCreator;

import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.reportCreator.ReportCreatorCheckingRelationship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoSession;
import org.mockito.Spy;

public class ReportCreatorCheckingRelationshipTest {

    private MockitoSession session;

    @Spy
    ReportCreatorCheckingRelationship reportCreator;

    @BeforeEach
    public void beforeEach(){
        session = Mockito.mockitoSession().initMocks(this).startMocking();
    }

    @Test
    @DisplayName("relationship report creator test")
    public void test(){
//        reportCreator.setData();

    }

    @AfterEach
    public void afterEach(){
        session.finishMocking();
    }

}
