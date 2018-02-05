import base.parser.ConfigParser;
import entity.ImportConfig;
import entity.MapResult;
import exception.FileImportException;
import executor.FileImportExecutor;
import factory.ConfigurationParserFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguilin on 2/5/2018.
 */
public class Import_Api {
    public static void main(String[] args) throws FileImportException, URISyntaxException {
        importApi();
    }

    private static void importApi() throws FileImportException, URISyntaxException {
        ConfigParser configParser = ConfigurationParserFactory.getConfigParser(ImportConfig.ParserType.XML);
        URI uri = Import_Api.class.getResource("/import/import_api.xlsx").toURI();
        File importFile = new File(uri);
        ImportConfig importConfig = null;
        try {
            importConfig = configParser.getConfig(ImportTest.class.getResourceAsStream("/import/import_api.xml"));
            MapResult mapResult = (MapResult) FileImportExecutor.importFile(importConfig, importFile, importFile.getName());
            List<Map> maps = mapResult.getResult();
            for (Map<String, Object> map : maps) {
                String id = (String) map.get("ID");
                String backend_system_id = (String) map.get("BACKEND_SYSTEM_ID");
                String name = (String) map.get("NAME");
                String description = (String) map.get("DESCRIPTION");
                String method = (String) map.get("METHOD");
                String path = (String) map.get("PATH");
                int scheme = (int) map.get("SCHEME");
                int security_scheme = (int) map.get("SECURITY_SCHEME");
                int state = (int) map.get("STATE");
                int limit_count = (int) map.get("LIMIT_COUNT");
                int limit_time = (int) map.get("LIMIT_TIME");
                String limit_unit = (String) map.get("LIMIT_UNIT");
                int is_delete = (int) map.get("IS_DELETE");
                System.out.println("ID: " + id + ", BACKEND_SYSTEM_ID: " + backend_system_id + ", NAME: " + name + ", DESCRIPTION: " + description +
                        ", METHOD: " + method + ", PATH: " + path + ", SCHEME: " + scheme + ", SECURITY_SCHEME: " + security_scheme + ", STATE" + state +
                        ",LIMIT_COUNT: " + limit_count + ", LIMIT_TIME: " + limit_time + ", LIMIT_UNIT: " + limit_unit + ", IS_DELETE: " + is_delete);
            }
        } catch (FileImportException e) {
            e.printStackTrace();
        }


    }
}
