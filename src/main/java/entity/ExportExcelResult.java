package entity;

import entity.ExportResult;
import entity.ExportType;
import exception.FileExportException;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@Data
public class ExportExcelResult extends ExportResult{

    private Workbook workbook;

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public void export(OutputStream outputStream) throws FileExportException {
        try {
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new FileExportException("Error occurred while export excel msg is " + e);
        }
    }

    @Override
    public ExportType getExportType() {
        return ExportType.EXCEL_2007;
    }
}
