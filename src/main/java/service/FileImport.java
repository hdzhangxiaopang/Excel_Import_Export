package service;

import entity.ImportResult;
import exception.FileImportException;

import java.io.File;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public interface FileImport {

    /**
     * 数据导入
     */
    ImportResult getImportResult(File file, String fileName) throws FileImportException;
}
