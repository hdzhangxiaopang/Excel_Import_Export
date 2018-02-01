package base.parser;

import base.util.EmptyUtil;
import excel_import.common.Configuration;
import excel_import.exception.FileImportException;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ConfigurationParserFactory {
    public static ConfigParser getConfigParser(Configuration.PasreType parseType) throws FileImportException{
        if(EmptyUtil.isNotEmpty(parseType)){
            throw new FileImportException("parseType is null");
        }
        if(parseType == Configuration.PasreType.XML){
            return new XmlConfigParser();
        }
        return new XmlConfigParser();
    }
}
