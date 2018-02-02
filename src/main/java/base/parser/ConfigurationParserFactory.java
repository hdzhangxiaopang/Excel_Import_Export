package base.parser;

import base.util.EmptyUtil;
import excel_import.common.Configuration;
import excel_import.exception.FileImportException;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ConfigurationParserFactory {
    public static ConfigParser getConfigParser(Configuration.ParserType parserType) throws FileImportException{
        if(parserType == null){
            throw new FileImportException("parserType is null");
        }
        if(parserType == Configuration.ParserType.XML){
            return new XmlConfigParser();
        }
        return new XmlConfigParser();
    }
}
