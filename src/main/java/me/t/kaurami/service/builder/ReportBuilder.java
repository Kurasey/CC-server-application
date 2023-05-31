package me.t.kaurami.service.builder;
import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.bookReader.NotValidWorkbookException;
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
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Facade report building
 */
@Service
@RequestScope
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

    /**
     * Full cycle of report generation
     * @throws IOException
     * @throws NotValidWorkbookException
     */
    public void buildReport() throws IOException, NotValidWorkbookException {
        StopWatch stopWatch = new StopWatch();
        logger.info("Generating report... " + settingHolder.getType());
        stopWatch.start();
        creator = applicationContext.getBean(settingHolder.getType() + "_reportCreator", ReportCreator.class);
        reader.loadBook(settingHolder.getSourceFile());
        LinkedList<LinkedList<String>> sourceData = reader.readSheet();
        logger.info("Source data successfully extracted!");
        creator.setLimit(settingHolder.getLimit());
        List<Exportable> reportData = creator.createReport(sourceData);
        editor.setReportType(settingHolder.getType());
        Workbook workbook = editor.createReportFile(reportData);
        settingHolder.setTargetFile(workbook);
        stopWatch.stop();
        logger.info("The generating report is successfully completed \nSpent time:" + stopWatch.getTotalTimeMillis() + " ms");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
