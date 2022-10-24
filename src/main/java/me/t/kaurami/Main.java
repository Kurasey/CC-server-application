package me.t.kaurami;

import me.t.kaurami.service.setting.ReportSetting;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.bookWriter.BookWriter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.File;


public class Main {

    public static void main(String[] args) throws Exception {
        //createVolumeforming();
        createCheckerRel();
    }


    private static void createVolumeforming() throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BookReader reader = context.getBean("standardBookReader", BookReader.class);
        ReportCreator reportCreator = context.getBean("reportCreatorVF", ReportCreator.class);
        BookEditor bookEditor = context.getBean("bookEditor", BookEditor.class);
        BookWriter writer = context.getBean("standardBookWriter", BookWriter.class);

        reader.loadBook(new File("C:/выгрузка 2022-08-15.xlsx"));
        reportCreator.setData(reader.readSheet());
        bookEditor.setSetting(context.getBean("volumeFormingSetting", ReportSetting.class));
        reportCreator.setLimit(1000000l);
        bookEditor.writeReport(reportCreator.createReport());
        writer.writeBook("C:/tes6t", bookEditor.getWorkbook());
    }

    private static void createCheckerRel() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-context-Krasnodar.xml");
        context.getEnvironment().setActiveProfiles("Krasnodar");
        context.refresh();

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BookReader reader = context.getBean("standardBookReader", BookReader.class);
        ReportCreator reportCreator = context.getBean("reportCreatorCR", ReportCreator.class);
        BookEditor bookEditor = context.getBean("bookEditor", BookEditor.class);
        BookWriter writer = context.getBean("standardBookWriter", BookWriter.class);

        reader.loadBook(new File("C:/Клиенты 2022-08-16.xlsx"));
        reportCreator.setData(reader.readSheet());
        bookEditor.setSetting(context.getBean("ckeckingRelationship", ReportSetting.class));
        bookEditor.writeReport(reportCreator.createReport());
        writer.writeBook("C:/tes6t", bookEditor.getWorkbook());
    }

}
