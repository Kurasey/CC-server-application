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

    abstract protected void log(String log);

    abstract protected void sendReport(Workbook workbook);

    abstract protected void setReportCreator();
}
