package entity;

import base.constants.UtilConstants;
import entity.ExportResult;
import entity.ExportType;
import exception.FileExportException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ExportCSVResult extends ExportResult{

    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public Object getResult() {
        return result;
    }


    @Override
    public ExportType getExportType() {
        return ExportType.CSV;
    }

    @Override
    public void export(OutputStream outputStream) throws FileExportException {
        try {
            outputStream.write(result.getBytes("UTF-8"));
            outputStream.close();
        } catch (IOException e) {
           throw new FileExportException(UtilConstants.EXPORT_CSV_FILE_ERROR+e);
        }
    }
}
