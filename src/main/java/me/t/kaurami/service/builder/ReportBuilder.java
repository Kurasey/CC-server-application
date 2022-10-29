package me.t.kaurami.service.builder;
import me.t.kaurami.service.bookEditor.BookEditor;
import me.t.kaurami.service.bookReader.BookReader;
import me.t.kaurami.service.bookWriter.BookWriter;
import me.t.kaurami.service.bookWriter.StandardBookWriter;
import me.t.kaurami.service.reportCreator.ReportCreator;
import me.t.kaurami.service.setting.ReportFileParameters;
import me.t.kaurami.service.setting.SettingHolder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReportBuilder implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ReportBuilder.class);

    private ApplicationContext applicationContext;
    private SettingHolder settingHolder;
    private ReportFileParameters reportFileParameters;
    private BookReader reader;
    private ReportCreator creator;
    private BookEditor editor;

    public ReportBuilder(SettingHolder settingHolder, BookReader reader, BookEditor editor) {
        this.settingHolder = settingHolder;
        this.reader = reader;
        this.editor = editor;
    }

    public void createReport() throws IOException, InvalidFormatException {
        loadBeans();
        reader.loadBook(settingHolder.getSourceFile());
        logger.info("Book is Loaded");
        creator.setData(reader.readSheet());
        logger.info("Sheet is readed");
        editor.setSetting(reportFileParameters);
        logger.info("Setting is selected");
        creator.setLimit(settingHolder.getLimit());
        logger.info("limit is selected");
        editor.writeReport(creator.createReport());
        logger.info("Report is created");
        BookWriter writer = new StandardBookWriter();
        writer.writeBook("C:/testBook", editor.getWorkbook());
//        settingHolder.setTargetFile(editor.getWorkbook());
    }


    private void loadBeans(){
        switch (settingHolder.getType()){
            case VOLUME_FORMING:
                logger.info("Selected report creator: 'VF'");
                creator = applicationContext.getBean("reportCreatorVF", ReportCreator.class);
                reportFileParameters = settingHolder.getVolumeFormingSettings();
                break;
            case CHECKING_RELATIONSHIP:
                logger.info("Selected report creator: 'CR'");
                creator = applicationContext.getBean("reportCreatorCR", ReportCreator.class);
                reportFileParameters = settingHolder.getRelationCheckingSettings();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
