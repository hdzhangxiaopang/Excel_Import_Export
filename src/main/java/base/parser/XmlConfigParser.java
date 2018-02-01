package base.parser;
import base.constants.UtilConstants;
import excel_import.common.Configuration;
import excel_import.common.ImportCell;
import excel_import.exception.FileImportException;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
 * Created by zhangguilin on 2/1/2018.
 */
public class XmlConfigParser extends ConfigParser {

    private static List<ImportCell> initElemet(NodeList elements) throws FileImportException{

        ArrayList<ImportCell> importCells = new ArrayList<>(elements.getLength());
        for (int i = 0; i < elements.getLength(); i++) {
            ImportCell importCell = new ImportCell();
            Element node = (Element) elements.item(i);
            String numberText = getNodeText(node, "number");
            if(StringUtils.isEmpty(numberText)||!StringUtils.isNumeric(numberText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_NUMBER_ERROR);
            }
            importCell.setNumber(Integer.valueOf(numberText));

            String keyText = getNodeText(node, "key");
            if(StringUtils.isEmpty(keyText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_KEY_ERROR);
            }
            importCell.setKey(keyText);

            String cellTypeText = getNodeText(node, "cellType");
            importCell.setCellType(getCellType(cellTypeText));

            String nullAbleText = getNodeText(node, "nullAble");
            if(StringUtils.isEmpty(cellTypeText)||!StringUtils.isNumeric(nullAbleText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_NULL_ABLE_ERROR);
            }
            importCell.setNullAble(ImportCell.NullAble.getNullAble(Integer.valueOf(nullAbleText)));

            importCells.add(importCell);

        }
        if(importCells.isEmpty()){
            throw new FileImportException(UtilConstants.IMPORT_XML_NULL_ERROR);
        }
        return importCells;
    }
    private static ImportCell.CellType getCellType(String cellType) throws FileImportException{
        if(StringUtils.isEmpty(cellType)){
            throw new FileImportException(UtilConstants.IMPORT_XML_CELL_TYPE_ERROR);
        }
        if(StringUtils.isNumeric(cellType)){
            return ImportCell.CellType.getCellType(Integer.valueOf(cellType));
        }else{
            return ImportCell.CellType.getCellType(cellType);
        }
    }

    @Override
    public Configuration getConfig(InputStream configStream) throws FileImportException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document document = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(configStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new FileImportException(e,UtilConstants.IMPORT_XML_PARSER_ERROR);
        }
        document.getDocumentElement().normalize();
        Configuration configuration = new Configuration();

        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("cell");
        List<ImportCell> importCells = initElemet(nList);

        String startRowNoText = getNodeText(root, "startRowNumber");
        if(StringUtils.isEmpty(startRowNoText)||!StringUtils.isNumeric(startRowNoText)){
            throw new FileImportException(UtilConstants.IMPORT_XML_START_ROW_NUMBER_ERROR);
        }
        configuration.setStartRowNumber(Integer.valueOf(startRowNoText));
        configuration.setImportCells(importCells);
        configuration.setImportFileType(Configuration.ImportFileType.EXCEL);

        return configuration;
    }
}
