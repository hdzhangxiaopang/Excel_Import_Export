package base.util;

import base.constants.UtilConstants;
import exception.FileImportException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by zhangguilin on 9/19/2018.
 */
public class NodeTextUtil {

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
