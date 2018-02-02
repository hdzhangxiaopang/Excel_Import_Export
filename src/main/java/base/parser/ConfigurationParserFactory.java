package base.parser;

import entity.ImportConfig;
import exception.FileImportException;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ConfigurationParserFactory {
    public static ConfigParser getConfigParser(ImportConfig.ParserType parserType) throws FileImportException{
        if(parserType == null){
            throw new FileImportException("parserType is null");
        }
        if(parserType == ImportConfig.ParserType.XML){
            return new XmlConfigParser();
        }
        return new XmlConfigParser();
    }
}
