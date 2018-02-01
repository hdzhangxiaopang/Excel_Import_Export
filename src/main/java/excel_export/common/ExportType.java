package excel_export.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zhangguilin on 1/31/2018.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExportType {
    EXCEL_2007(0,"xlsx","application/vnd.ms-excel"),
    CSV(1,"csv","application/csv");

    private final int number;
    private final String fileType;
    private final String contentType;

    public static ExportType getExportType(int number){
        for (ExportType exportType : ExportType.values()){
            if(exportType.getNumber() == number){
                return exportType;
            }
        }
        return null;
    }

    ExportType(Integer number, String fileType, String contentType) {
        this.fileType = fileType;
        this.number = number;
        this.contentType = contentType;
    }

}
