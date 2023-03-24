package me.t.kaurami.service.builder;
import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.bookWriter.BookWriter;
import me.t.kaurami.service.bookWriter.StandardBookWriter;
import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.setting.SettingHolder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReportBuilder implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ReportBuilder.class);
    private ApplicationContext applicationContext;
    private SettingHolder settingHolder;
    private BookReader reader;
    private ReportCreator creator;
    private BookEditor editor;

    @Autowired
    public ReportBuilder(SettingHolder settingHolder, BookReader reader, BookEditor editor) {
        this.settingHolder = settingHolder;
        this.reader = reader;
        this.editor = editor;
    }

    public void buildReport() throws IOException, InvalidFormatException {
        StopWatch stopWatch = new StopWatch();
        logger.info("Creating report... " + settingHolder.getType());
        stopWatch.start();
        creator = applicationContext.getBean(settingHolder.getType() + "_reportCreator", ReportCreator.class);
        reader.loadBook(settingHolder.getSourceFile());
        LinkedList<LinkedList<String>> sourceData = reader.readSheet();
        logger.info("Source data successfully extracted!");
        creator.setData(sourceData);
        creator.setLimit(settingHolder.getLimit());
        List<Exportable> reportData = creator.createReport();
        logger.info("The report has been successfully generated!");
        editor.setReportType(settingHolder.getType());
        editor.createReportFile(reportData);
        Workbook workbook = editor.getWorkbook();
        logger.info("The report has been successfully written to a book");
        BookWriter writer = new StandardBookWriter();
        writer.writeBook("C:/testBook", workbook);
        settingHolder.setTargetFile(workbook);                                              //не работает загрузка
        stopWatch.stop();
        logger.info("The process is completed");
        logger.info("Spent time:" + stopWatch.getTotalTimeMillis() + " ms");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
