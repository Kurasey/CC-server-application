package me.t.kaurami.service.setting;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public class AppSetting {
    private Profile activeProfile;
    private File sourceFile;
    private long limit;
    private PreReportSetting.ReportType type;
    private Workbook targetFile;

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

    public Profile getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(Profile activeProfile) {
        this.activeProfile = activeProfile;
    }

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

    public PreReportSetting.ReportType getType() {
        return type;
    }

    public void setType(PreReportSetting.ReportType type) {
        this.type = type;
    }

    public Workbook getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(Workbook targetFile) {
        this.targetFile = targetFile;
    }
}
