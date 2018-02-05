package factory;

import base.constants.UtilConstants;
import base.parser.ConfigParser;
import base.parser.XmlConfigParser;
import base.util.EmptyUtil;
import entity.ImportConfig;
import exception.FileImportException;

/**
 * Created by zhangguilin on 2/1/2018.
 */
public class ConfigurationParserFactory {
    public static ConfigParser getConfigParser(ImportConfig.ParserType parserType) throws FileImportException {
        if (EmptyUtil.isEmpty(parserType)) {
            throw new FileImportException(UtilConstants.PARSER_TYPE_ISEMPTY);
        }
        if (parserType == ImportConfig.ParserType.XML) {
            return new XmlConfigParser();
        }
        return new XmlConfigParser();
    }
}
