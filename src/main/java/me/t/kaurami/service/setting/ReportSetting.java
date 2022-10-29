package me.t.kaurami.service.setting;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ReportSetting {

    public static enum ReportType{
        VOLUME_FORMING("Список объемообразующих клиентов"), CHECKING_RELATIONSHIP("Проверка связи по хозяину");

        private String name;

        ReportType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private File sourceFile;
    private long limit;
    private ReportType type;
    private Workbook pathForSave;       //Rename


    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Workbook getPathForSave() {
        return pathForSave;
    }

    public void setPathForSave(Workbook pathForSave) {
        this.pathForSave = pathForSave;
    }
}
