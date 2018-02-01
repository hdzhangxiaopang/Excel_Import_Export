import excel_export.FileExport;
import excel_export.common.ExportConfig;
import excel_export.common.ExportResult;
import excel_export.exception.FileExportException;
import excel_export.service.ExportConfigFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ExportTest {

    public static void main(String[] args) throws FileNotFoundException,FileExportException{
        testExport();
    }

    public static void testExport() throws FileExportException, FileNotFoundException {
        ExportConfig exportConfig = ExportConfigFactory.getExportConfig(ExportTest.class.getResourceAsStream("export/export_config.xml"));
        ExportResult exportResult = FileExport.getExportResult(exportConfig, initData());
       // OutputStream outputStream = new FileOutputStream("C://output.xlsx");
        OutputStream outputStream = new FileOutputStream("C://output.csv");
        exportResult.export(outputStream);
    }
    public static List<Map> initData(){
        //map也可以换成一个实体类
        List<Map> lists = new LinkedList<>();
        for(int i = 0;i< 10;i++){
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("index", i);
            maps.put("date", new Date());
            maps.put("greet", "hi" + i);
            maps.put("float", Float.valueOf(i));
            maps.put("bigdecimal", BigDecimal.valueOf(i));
            lists.add(maps);
        }
        return lists;
    }
}
