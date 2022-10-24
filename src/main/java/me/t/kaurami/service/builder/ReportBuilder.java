package me.t.kaurami.service.builder;

import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.setting.AppSetting;
import me.t.kaurami.service.setting.Profile;
import me.t.kaurami.service.setting.ReportSetting;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

public class ReportBuilder implements ApplicationContextAware {
    private ApplicationContext context;
    private AppSetting appSetting;
    private ReportSetting reportSetting;
    private BookReader reader;
    private ReportCreator creator;
    private BookEditor editor;

    public ReportBuilder(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void setAppSetting(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    @Autowired
    @Qualifier("standardBookReader")
    public void setReader(BookReader reader) {
        this.reader = reader;
    }

    @Autowired
    @Qualifier("bookEditor")
    public void setEditor(BookEditor editor) {
        this.editor = editor;
    }

    public void createReport() throws IOException, InvalidFormatException {
        loadBeans();
        reader.loadBook(appSetting.getSourceFile());
        creator.setData(reader.readSheet());
        editor.setSetting(reportSetting);
        creator.setLimit(appSetting.getLimit());
        editor.writeReport(creator.createReport());
        appSetting.setTargetFile(editor.getWorkbook());
    }


    private void loadBeans(){
        Profile profile = appSetting.getActiveProfile();
        GenericXmlApplicationContext reportContext = new GenericXmlApplicationContext();
        reportContext.getEnvironment().setActiveProfiles("Krasnodar");
        System.out.println(profile.getXmlConfig());
        System.out.println(profile.getInnerName());
        reportContext.load("app-context-Krasnodar.xml");
        reportContext.refresh();
        reportContext.registerShutdownHook();
        switch (appSetting.getType()){
            case VOLUME_FORMING:
                creator = context.getBean("reportCreatorVF", ReportCreator.class);
                reportSetting = context.getBean("volumeFormingSetting", ReportSetting.class);
                break;
            case CHECKING_RELATIONSHIP:
                creator = context.getBean("reportCreatorCR", ReportCreator.class);
                reportSetting = context.getBean("ckeckingRelationship", ReportSetting.class);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
