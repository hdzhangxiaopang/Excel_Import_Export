package excel_import.exception;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class FileImportException extends Exception {

    public FileImportException(String message) {
        super(message);
    }

    public FileImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileImportException(Throwable cause) {
        super(cause);
    }
}
