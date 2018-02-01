package excel_export.common;

import excel_export.exception.FileExportException;
import lombok.Data;

import java.io.OutputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public abstract class ExportResult {
    private String fileName;

    public abstract Object getResult();

    public abstract ExportType getExportType();

    public abstract void export(OutputStream outputStream) throws FileExportException;

}
