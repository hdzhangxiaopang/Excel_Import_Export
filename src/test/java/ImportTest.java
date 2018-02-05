import base.parser.ConfigParser;
import factory.ConfigurationParserFactory;
import entity.ImportConfig;
import entity.MapResult;
import exception.FileImportException;
import executor.FileImportExecutor;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ImportTest {
    public static void main(String[] args) throws FileImportException, URISyntaxException {
        testImport();
    }

    private static void testImport() throws FileImportException, URISyntaxException {
        ConfigParser configParser = ConfigurationParserFactory.getConfigParser(ImportConfig.ParserType.XML);
        URI uri = ImportTest.class.getResource("import/testImport.xlsx").toURI();
        File importFile = new File(uri);
        ImportConfig configuration = null;
        try {
            configuration = configParser.getConfig(ImportTest.class.getResourceAsStream("import/import_config.xml"));
            MapResult mapResult = (MapResult) FileImportExecutor.importFile(configuration, importFile, importFile.getName());
            List<Map> maps = mapResult.getResult();
            for (Map<String, Object> map : maps) {
                int index = (int) map.get("index");
                float f1 = (float) map.get("float");
                String string = (String) map.get("string");
                Date date = (Date) map.get("date");
                BigDecimal bigDecimal = (BigDecimal) map.get("bigdecimal");
                System.out.println("index :" + index + " f1 : " + f1 + " string : " + string
                        + " date : " + date.toString() + " bigdecimal " + bigDecimal);
            }
        } catch (FileImportException e) {
            e.printStackTrace();
        }

    }
}
