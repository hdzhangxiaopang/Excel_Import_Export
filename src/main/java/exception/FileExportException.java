package exception;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class FileExportException extends Exception {

    public FileExportException(String message) {
        super(message);
    }

    public FileExportException( Throwable cause,String message) {
        super(message, cause);
    }

    public FileExportException(Throwable cause) {
        super(cause);
    }

}
