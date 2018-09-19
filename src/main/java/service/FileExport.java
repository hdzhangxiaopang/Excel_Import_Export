package service;

import entity.ExportConfig;
import exception.FileExportException;

import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public interface FileExport {

    /**
     * 数据导出
     */
    Object getExportResult(ExportConfig exportConfig, List<?> data) throws FileExportException;
}
