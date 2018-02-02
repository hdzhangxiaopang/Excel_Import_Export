package excel_import.executor;

import base.constants.UtilConstants;
import excel_import.common.Configuration;
import excel_import.common.ImportResult;
import excel_import.exception.FileImportException;
import excel_import.service.FileImport;
import excel_import.service.impl.ExcelImport;

import java.io.File;

/**
 * Created by zhangguilin on 2/2/2018.
 */
public class FileImportExecutor {

    public static ImportResult importFile(Configuration configuration,File file,String fileName) throws FileImportException{
        FileImport fileImport = getFileImport(configuration);
        return fileImport.getImportResult(file,fileName);
    }

    private static FileImport getFileImport(Configuration configuration) throws FileImportException {
        if (configuration.getImportFileType() == Configuration.ImportFileType.EXCEL){
            return new ExcelImport(configuration);
        }
        throw new FileImportException(UtilConstants.IMPORT_FILE_TYPE_ERROR);
    }
}
