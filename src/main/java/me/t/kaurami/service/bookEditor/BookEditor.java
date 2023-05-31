package me.t.kaurami.service.bookEditor;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.setting.ReportFormatHolder;
import me.t.kaurami.service.setting.SettingHolder;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface BookEditor {
    public void setWorkbook(Workbook workbook);

    public Workbook createReportFile(List<Exportable> report);

    public boolean setReportType(SettingHolder.ReportType type);

    public void setSetting(ReportFormatHolder setting);
}
