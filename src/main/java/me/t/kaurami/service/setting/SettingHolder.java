package me.t.kaurami.service.setting;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "market-owner")
public class SettingHolder{

    private static Logger logger = LoggerFactory.getLogger(SettingHolder.class);

    private final Map<String, ReportFormatHolder> settings;
    private final Map<String, String> availableDistricts;
    private String namePattern;
    private File sourceFile;
    private long limit;
    private ReportType type;
    private Workbook targetFile;       //Rename

    public static enum ReportType{
        VOLUME_FORMING("Список объемообразующих клиентов"), CHECKING_RELATIONSHIP("Проверка связи по хозяину");

        private String name;

        ReportType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

//        public void setName(String name) {
//            this.name = name;
//        }
    }

    public SettingHolder(@Qualifier("settings") Map<String, ReportFormatHolder> settings, @Qualifier("availableDistricts") Map<String, String> availableDistricts) {
        this.settings = settings;
        this.availableDistricts = availableDistricts;
    }

    public String getNamePattern() {
        return namePattern;
    }


    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public File getSourceFile() {
        return sourceFile;
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

    public Workbook getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(Workbook targetFile) {
        this.targetFile = targetFile;
    }
}
