package base.parser;
import base.constants.UtilConstants;
import entity.ImportConfig;
import entity.ImportCell;
import entity.ImportLabel;
import exception.FileImportException;
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
            String numberText = getNodeText(node, ImportLabel.NUMBER);
            if(StringUtils.isEmpty(numberText)||!StringUtils.isNumeric(numberText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_NUMBER_ISEMPTY);
            }
            importCell.setNumber(Integer.valueOf(numberText));

            String keyText = getNodeText(node, ImportLabel.KEY);
            if(StringUtils.isEmpty(keyText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_KEY_ISEMPTY);
            }
            importCell.setKey(keyText);

            String cellTypeText = getNodeText(node, ImportLabel.CELL_TYPE);
            importCell.setCellType(getCellType(cellTypeText));

            String nullAbleText = getNodeText(node, ImportLabel.NULL_ABLE);
            if(StringUtils.isEmpty(cellTypeText)||!StringUtils.isNumeric(nullAbleText)){
                throw new FileImportException(UtilConstants.IMPORT_XML_NULL_ABLE_ISEMPTY);
            }
            importCell.setNullAble(ImportCell.NullAble.getNullAble(Integer.valueOf(nullAbleText)));

            importCells.add(importCell);

        }
        if(importCells.isEmpty()){
            throw new FileImportException(UtilConstants.IMPORT_XML_FILE_CONTENT_ISEMPTY);
        }
        return importCells;
    }
    private static ImportCell.CellType getCellType(String cellType) throws FileImportException{
        if(StringUtils.isEmpty(cellType)){
            throw new FileImportException(UtilConstants.IMPORT_XML_CELL_TYPE_ISEMPTY);
        }
        if(StringUtils.isNumeric(cellType)){
            return ImportCell.CellType.getCellType(Integer.valueOf(cellType));
        }else{
            return ImportCell.CellType.getCellType(cellType);
        }
    }

    @Override
    public ImportConfig getConfig(InputStream configStream) throws FileImportException {
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
        ImportConfig configuration = new ImportConfig();

        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName(ImportLabel.CELL);
        List<ImportCell> importCells = initElemet(nList);

        String startRowNoText = getNodeText(root, ImportLabel.START_ROW_NUMBER);
        if(StringUtils.isEmpty(startRowNoText)||!StringUtils.isNumeric(startRowNoText)){
            throw new FileImportException(UtilConstants.IMPORT_XML_START_ROW_NUMBER_ERROR);
        }
        configuration.setStartRowNumber(Integer.valueOf(startRowNoText));
        configuration.setImportCells(importCells);
        configuration.setImportFileType(ImportConfig.ImportFileType.EXCEL);

        return configuration;
    }
}
