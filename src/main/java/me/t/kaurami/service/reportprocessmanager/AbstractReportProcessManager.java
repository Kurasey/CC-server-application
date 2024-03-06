package me.t.kaurami.service.reportprocessmanager;

//import me.t.kaurami.service.reportCreator.ReportCreator;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractReportProcessManager {

    abstract protected void log(String log);

    abstract protected void sendReport(Workbook workbook);

    abstract protected void setReportCreator();
}
