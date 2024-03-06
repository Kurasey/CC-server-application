package me.t.kaurami.service.reportprocessmanager;
import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.bookReader.NotValidWorkbookException;

import me.t.kaurami.service.reportprocessor.TemplateReportProcessor;
import me.t.kaurami.service.setting.SettingHolder;
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

import java.io.IOException;
import java.util.List;

/**
 * Facade report building
 */
@Service
@RequestScope
public class ReportProcessManager implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ReportProcessManager.class);
    private ApplicationContext applicationContext;
    private SettingHolder settingHolder;
    private BookReader reader;
    private TemplateReportProcessor creator;
    private BookEditor editor;

    @Autowired
    public ReportProcessManager(SettingHolder settingHolder, BookReader reader, BookEditor editor) {
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
        creator = applicationContext.getBean(settingHolder.getType() + "_reportCreator", TemplateReportProcessor.class);
        reader.loadBook(settingHolder.getSourceFile());
        List<List<String>> sourceData = reader.readSheet();
        logger.info("Source data successfully extracted!");
        creator.setLimit(settingHolder.getLimit());
        List<Exportable> reportData = creator.processReport(sourceData);
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
