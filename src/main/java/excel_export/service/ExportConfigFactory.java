package excel_export.service;

import base.domain.ConfigParser;
import com.sun.corba.se.impl.orb.ORBConfiguratorImpl;
import excel_export.common.ExportCell;
import excel_export.common.ExportConfig;
import excel_export.common.ExportType;
import excel_export.exception.FileExportException;
import excel_import.exception.FileImportException;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguilin on 1/31/2018.
 */
public class ExportConfigFactory {
    public static ExportConfig getExportConfig(InputStream inputStream) throws FileExportException{
        return getExportCells(inputStream);
    }

    private static ExportConfig getExportCells(InputStream inputStream) throws FileExportException {
        ExportConfig exportConfig = new ExportConfig();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document document = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
           throw new FileExportException(e,"parse xml error");
        }
        Element element = document.getDocumentElement();
        NodeList elements = element.getElementsByTagName("cell");
        List<ExportCell> exportCells = initElement(elements);

        String fileName = "";
        String fileType = "";
        try {
            fileName = ConfigParser.getNodeText(element, "fileName");
            fileType = ConfigParser.getNodeText(element, "exportType");
        } catch (FileImportException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(fileName)){
            throw new FileExportException("用于导出的xml<fileName>为空");
        }
        if(StringUtils.isEmpty(fileType)||!StringUtils.isNumeric(fileType)){
            throw new FileExportException("用于导出的xml文档<exportType>为空");
        }
        exportConfig.setFileName(fileName);
        ExportType exportType = ExportType.getExportType(Integer.valueOf(fileType));
        if(exportType == null){
            throw new FileExportException("找不到对应的ExportType解析xml得到的fileType是"+fileType);
        }
        exportConfig.setExportType(exportType);
        exportConfig.setExportCells(exportCells);
        return exportConfig;
    }

    private static List<ExportCell> initElement(NodeList elements) throws FileExportException {
        ArrayList<ExportCell> exportCells = new ArrayList<>(elements.getLength());
        for(int i = 0; i < elements.getLength(); i++){
            ExportCell exportCell = new ExportCell();
            Element item = (Element) elements.item(i);
            String titleText = "";
            String aliasText = "";
            try {
                titleText = ConfigParser.getNodeText(item, "title");
                aliasText = ConfigParser.getNodeText(item, "alias");
            } catch (FileImportException e) {
                throw new FileExportException(e);
            }
            if(StringUtils.isEmpty(titleText)){
                throw new FileExportException("用于导出的xml文档<title>为空");
            }
            exportCell.setTitle(titleText);
            if(StringUtils.isEmpty(aliasText)){
                throw new FileExportException("用于导出的xml文档<alias>为空");
            }
            exportCell.setAlias(aliasText);
            exportCells.add(exportCell);
        }
        if(exportCells.isEmpty()){
            throw new FileExportException("用于导出的xml文档解析内容为空");
        }
        return exportCells;
    }
}
