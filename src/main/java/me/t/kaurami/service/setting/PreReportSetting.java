package me.t.kaurami.service.setting;

import java.io.File;

public class PreReportSetting {
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

    private File file;
    private long limit;
    private ReportType type;
    private File pathForSave;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public File getPathForSave() {
        return pathForSave;
    }

    public void setPathForSave(File pathForSave) {
        this.pathForSave = pathForSave;
    }
}
