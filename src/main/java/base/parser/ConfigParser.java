package base.parser;

import entity.ImportConfig;
import exception.FileImportException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public abstract class ConfigParser {

    abstract public ImportConfig getConfig(InputStream inputStream) throws FileImportException;

    public static String getNodeText(Element element,String key) throws FileImportException{
        NodeList nodeList = element.getElementsByTagName(key);
        if(nodeList.getLength()==0){
            throw new FileImportException("Tag is Empty. tag:"+key);
        }
        return nodeList.item(0).getTextContent();

    }

}
