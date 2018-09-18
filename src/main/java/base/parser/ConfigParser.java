package base.parser;

import base.constants.UtilConstants;
import entity.ImportConfig;
import exception.FileImportException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public abstract class ConfigParser {

    public abstract ImportConfig getConfig(InputStream inputStream) throws FileImportException;

    /**
     * 根据标签名称获取标签值
     */
    public static String getNodeText(Element element, String key) throws FileImportException {
        NodeList nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            throw new FileImportException(UtilConstants.LABEL_ISEMPTY + key);
        }
        return nodeList.item(0).getTextContent();

    }

}
