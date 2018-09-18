import entity.*;
import exception.FileExportException;
import factory.ExportConfigFactory;

import java.io.*;
import java.util.*;

/**
 * Created by zhangguilin on 2/5/2018.
 */
public class ExportApi {
    public static void main(String[] args) throws FileExportException, IOException {
        exportApi();
    }

    private static void exportApi() throws FileExportException, IOException {
        ExportConfig exportConfig = ExportConfigFactory.getExportConfig(ExportApi.class.getResourceAsStream("export/export_api.xml"));
        ExportResult exportResult = FileExport.getExportResult(exportConfig, initData());
        FileOutputStream fileOutputStream = new FileOutputStream("C://export_api.xlsx");
        //FileOutputStream fileOutputStream = new FileOutputStream("C://export_api.csv");
        exportResult.export(fileOutputStream);
    }

    private static List<Map> initData() {
        List<Map> lists = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", UUID.randomUUID().toString().replace("-", "").toLowerCase());
            map.put("backendSystemId", UUID.randomUUID().toString().replace("-", "").toLowerCase());
            map.put("name", "API_00" + i);
            map.put("description", "DESC");
            map.put("path", "/system_api");
            map.put("method", "POST");
            map.put("scheme", 2);
            map.put("securityScheme", 2);
            map.put("state", 2);
            map.put("limitCount", 60);
            map.put("limitTime", 80);
            map.put("limitUnit", "SECONDS");
            map.put("isDelete", 1);
            lists.add(map);
        }
        return lists;
    }
    /*private static List<Api> initData() {
        List<Api> lists = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Api api = new Api();
            api.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            api.setBackendSystemId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            api.setName("API_00" + i);
            api.setDescription("DESC");
            api.setPath("/system_api");
            api.setMethod("POST");
            api.setScheme(1);
            api.setSecurityScheme(2);
            api.setState(1);
            api.setLimitCount(60);
            api.setLimitTime(80);
            api.setLimitUnit("SECOND");
            api.setIsDelete(1);
            lists.add(api);
        }
        return lists;
    }
*/
}
