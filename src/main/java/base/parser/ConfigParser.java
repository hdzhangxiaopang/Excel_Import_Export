package base.parser;

import entity.ImportConfig;
import exception.FileImportException;

import java.io.InputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public interface ConfigParser {

    ImportConfig getConfig(InputStream inputStream) throws FileImportException;

}
