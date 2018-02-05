package exception;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class FileImportException extends Exception {

    public FileImportException(String message) {
        super(message);
    }

    public FileImportException(Throwable throwable, String message) {
        super(message, throwable);
    }

}
