package me.t.kaurami.service.builder;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
//import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.setting.SettingHolder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractReportBuilder {
/*
//    private SettingHolder settingHolder;
    private BookReader reader;
    private ReportCreator creator;
    private BookEditor editor;

    public AbstractReportBuilder(SettingHolder settingHolder, BookReader reader, BookEditor editor) {
        this.settingHolder = settingHolder;
        this.reader = reader;
        this.editor = editor;
    }

    final public void buildReport() throws IOException, InvalidFormatException {
        StopWatch stopWatch = new StopWatch();
        log("Creating report... " + settingHolder.getType());
        stopWatch.start();
        creator = applicationContext.getBean(settingHolder.getType() + "_reportCreator", ReportCreator.class);
        reader.loadBook(settingHolder.getSourceFile());
        LinkedList<LinkedList<String>> sourceData = reader.readSheet();
        log("Source data successfully extracted!");
        creator.setData(sourceData);
        creator.setLimit(settingHolder.getLimit());
        List<Exportable> reportData = creator.createReport();
        log("The report has been successfully generated!");
        editor.setReportType(settingHolder.getType());
        editor.createReportFile(reportData);
        Workbook workbook = editor.getWorkbook();
        log("The report has been successfully written to a book");
        sendReport(workbook);
        stopWatch.stop();
        log("The process is completed");
        log("Spent time:" + stopWatch.getTotalTimeMillis() + " ms");
    }*/

    abstract protected void log(String log);

    abstract protected void sendReport(Workbook workbook);

    abstract protected void setReportCreator();
}
