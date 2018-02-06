import entity.Api;
import entity.ExportConfig;
import entity.ExportResult;
import entity.FileExport;
import exception.FileExportException;
import factory.ExportConfigFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    /*
    private static List<Map> initData() {
        List<Map> lists = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ID", UUID.randomUUID().toString().replace("-", "").toLowerCase());
            map.put("BACKEND_SYSTEM_ID", UUID.randomUUID().toString().replace("-", "").toLowerCase());
            map.put("NAME", "API_00" + i);
            map.put("DESCRIPTION", "DESC");
            map.put("PATH", "/system_api");
            map.put("METHOD", "POST");
            map.put("SCHEME", 2);
            map.put("SECURITY_SCHEME", 2);
            map.put("STATE", 2);
            map.put("LIMIT_COUNT", 60);
            map.put("LIMIT_TIME", 80);
            map.put("LIMIT_UNIT", "SECONDS");
            map.put("IS_DELETE", 1);
            lists.add(map);
        }
        return lists;
    } */


    private static List<Api> initData() {
        List<Api> lists = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Api api = new Api();
            api.setID(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            api.setBACKEND_SYSTEM_ID(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            api.setNAME("API_00" + i);
            api.setDESCRIPTION("DESC");
            api.setPATH("/system_api");
            api.setMETHOD("POST");
            api.setSCHEME(1);
            api.setSECURITY_SCHEME(2);
            api.setSTATE(1);
            api.setLIMIT_COUNT(60);
            api.setLIMIT_TIME(80);
            api.setLIMIT_UNIT("SECOND");
            api.setIS_DELETE(1);
            lists.add(api);
        }
        return lists;
    }

    /**
     * web下载
     */
    private void testExportInDownload() {

    }


}
