import entity.API;
import entity.ExportConfig;
import entity.ExportResult;
import entity.FileExport;
import exception.FileExportException;
import factory.ExportConfigFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangguilin on 2/5/2018.
 */
public class Export_Api {
    public static void main(String[] args) throws FileExportException, FileNotFoundException {
        exportApi();
    }

    private static void exportApi() throws FileExportException, FileNotFoundException {
        ExportConfig exportConfig = ExportConfigFactory.getExportConfig(ExportTest.class.getResourceAsStream("export/export_api.xml"));
        ExportResult exportResult = FileExport.getExportResult(exportConfig, initData());
        FileOutputStream fileOutputStream = new FileOutputStream("C://export_api.xlsx");
        //FileOutputStream fileOutputStream = new FileOutputStream("C://export_api.csv");
        exportResult.export(fileOutputStream);
    }

    private static List<API> initData() {
        List<API> lists = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            API api = new API();
            api.setID(UUID.randomUUID().toString().replace("-","").toLowerCase());
            api.setBACKEND_SYSTEM_ID(UUID.randomUUID().toString().replace("-","").toLowerCase());
            api.setNAME("API_00"+i);
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

}
