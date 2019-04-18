package ui;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DefaultTypeXMLReaderFactory {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("authoring_general");
    private static final String FOLDER_PATH = RESOURCES.getString("xml_folder_filepath");
    private static final String EXTENSIONS = ".xml";
    private Map<String, Document> myNameToFileMap;

    public DefaultTypeXMLReaderFactory(){
        initializeDocuments();
    }


    private void initializeDocuments(){
        File assetFolder = new File(FOLDER_PATH);
        File[] files = assetFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(EXTENSIONS));
        for(File temp : files) {
            System.out.println("********************");
            System.out.println(temp.getName());
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder;
                documentBuilder= documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(temp);
                document.getDocumentElement().normalize();
                Node root = document.getDocumentElement();
                System.out.println("Root element :" + root.getNodeName());
                NodeList nList = document.getElementsByTagName("Components");
                Node nNode = nList.item(0);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                NodeList components = ((Element) root).getElementsByTagName("Components");
                for(int k = 0; k < components.getLength(); k++) {
                    Node currentComponentList = components.item(k);
                    NodeList subComponentsList = currentComponentList.getChildNodes();
                    for (int i = 0; i < subComponentsList.getLength(); i++) {
                        Node node = subComponentsList.item(i);
                        if(node.getNodeType() == Node.ELEMENT_NODE){
                            System.out.println(node.getNodeName() + "\t" + node.getTextContent());
                        }
                    }
                }
//


            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }






    }
            }
}
