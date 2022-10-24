import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ListResourceBundle;

class ReportSheetStyles extends ListResourceBundle {
    private static fianl Object[][] styles = {

    };

    static {
        private CellStyle styleTitle;
        private CellStyle styleText;
        private CellStyle styleNumber;
    }

    public static Object[][] getContents(){
        return styles;
    }

}