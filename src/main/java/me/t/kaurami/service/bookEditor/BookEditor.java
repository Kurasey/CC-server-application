package me.t.kaurami.service.bookEditor;

import me.t.kaurami.entities.Exportable;
import me.t.kaurami.service.setting.ReportFileParameters;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface BookEditor {
    public void setWorkbook(Workbook workbook);

    public void writeReport(List<Exportable> report);

    public Workbook getWorkbook();

    public void setSetting(ReportFileParameters setting);
}
