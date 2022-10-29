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

    private final Map<String, ReportFileParameters> settings;
    private final Map<String, String> availableDistricts;
    private String namePattern;
    private ReportFileParameters volumeFormingSettings;
    private ReportFileParameters relationCheckingSettings;
    private String districtName;


    public SettingHolder(@Qualifier("settings") Map<String, ReportFileParameters> settings, @Qualifier("availableDistricts") Map<String, String> availableDistricts) {
        this.settings = settings;
        this.availableDistricts = availableDistricts;
    }

    public void setActiveSettings(String districtName) throws UnknownSettingException{
        if (availableDistricts.containsKey(districtName)){
            this.districtName = districtName;
            logger.info("Selected district: " + districtName);
            setVolumeFormingSettings();
            setRelationCheckingSettings();
        }else {
            throw new UnknownSettingException(districtName + " is not available \n\n" + "available: " + availableDistricts + "\n\n");
        }
    }

    public void setActiveSettings() throws UnknownSettingException{
        if (districtName == null){
            throw new IllegalArgumentException("Value 'districtName' is not initialized");
        }
        if (availableDistricts.containsKey(districtName)){
            this.districtName = districtName;
            logger.info("Selected district: " + districtName);
            setVolumeFormingSettings();
            setRelationCheckingSettings();
        }else {
            throw new UnknownSettingException(districtName + " is not available \n\n" + "available: " + availableDistricts + "\n\n");
        }
    }

    public String getNamePattern() {
        return namePattern;
    }


    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public ReportFileParameters getVolumeFormingSettings() {
        return volumeFormingSettings;
    }

    public ReportFileParameters getRelationCheckingSettings() {
        return relationCheckingSettings;
    }

    private void setVolumeFormingSettings() {
        this.volumeFormingSettings = settings.get("volumeFormingSettings" + districtName);
        logger.info("Volume forming setting: " + districtName);
    }

    private void setRelationCheckingSettings() {
        this.relationCheckingSettings = settings.get("relationCheckingSettings" + districtName);
        logger.info("Check relation setting: " + relationCheckingSettings);
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////
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
    private ReportSetting.ReportType type;
    private Workbook targetFile;       //Rename


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

    public ReportSetting.ReportType getType() {
        return type;
    }

    public void setType(ReportSetting.ReportType type) {
        this.type = type;
    }

    public Workbook getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(Workbook targetFile) {
        this.targetFile = targetFile;
    }
}
