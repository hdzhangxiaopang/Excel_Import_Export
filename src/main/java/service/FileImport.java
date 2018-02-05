package service;

import entity.ImportResult;
import exception.FileImportException;

import java.io.File;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public abstract class FileImport {
    public abstract ImportResult getImportResult(File file, String fileName) throws FileImportException;
}
