package ui;

import engine.external.Entity;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class DefaultTypeXMLReaderFactory {
    private static final ResourceBundle PATH_RESOURCES = ResourceBundle.getBundle("authoring_general");
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("default_types_factory");
    private static final String FOLDER_PATH = PATH_RESOURCES.getString("xml_folder_filepath");
    private static final String EXTENSIONS = ".xml";
    private Map<String, String> myNameToCategory;
    private Map<String, Map<String, String>> myNameToComponents;    //Name of default -> (map from components names -> values)
    private List<Node> myRootsList;

    public DefaultTypeXMLReaderFactory(){
        myNameToCategory = new HashMap<>();
        myNameToComponents= new HashMap<>();
        myRootsList = new ArrayList<>();
        fillRootsList();
        fillMaps();
    }

    /**
     * @return List of possible default names
     */
    public List<String> getDefaultNames(){
        List<String> defaultNames = new ArrayList<>(myNameToCategory.keySet());
        return Collections.unmodifiableList(defaultNames);
    }

    /**
     * @param name of the default type
     * @return String of its category
     */
    public String getCategory(String name){
        return myNameToCategory.get(name);
    }

    private void createEntity(String name){
        Map<String, String> componentMap = myNameToComponents.get(name);

    }
    private void fillMaps() {
        for(int k = 0; k < myRootsList.size(); k++){
            Element currentElement = (Element) myRootsList.get(k);
            Map<String, String> componentsMap = fillComponentsMap(currentElement);
            if(hasRequiredInformation(componentsMap, currentElement)){
                String name = componentsMap.get("Name");
                myNameToComponents.put(name, componentsMap);
                NodeList categoryList = currentElement.getElementsByTagName("Category");
                String category = categoryList.item(categoryList.getLength()-1).getTextContent();
                myNameToCategory.put(name, category);
            }
        }
    }


    private void fillRootsList(){
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
                myRootsList.add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasRequiredInformation(Map<String, String> componentsMap, Element root) {
        if(!componentsMap.containsKey("NameComponent")){
            String[] info = RESOURCES.getString("NoName").split(",");
            ErrorBox errorBox = new ErrorBox(info[0], info[1]);
            errorBox.display();
            return false;
        }
        else if(root.getElementsByTagName("Category").getLength() == 0){
            String[] info = RESOURCES.getString("NoCategory").split(",");
            ErrorBox errorBox = new ErrorBox(info[0], info[1]);
            errorBox.display();
            return false;
        }
        return true;
    }

    private Map<String, String> fillComponentsMap(Element root) {
        NodeList components = root.getElementsByTagName("Components");
        Map<String, String> componentsMap = new HashMap<>();
        for(int k = 0; k < components.getLength(); k++) {
            Node currentComponentList = components.item(k);
            NodeList subComponentsList = currentComponentList.getChildNodes();
            for (int i = 0; i < subComponentsList.getLength(); i++) {
                Node node = subComponentsList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    String componentName = node.getNodeName();
                    String componentValue = node.getTextContent();
                    componentsMap.put(componentName, componentValue);
                }
            }
        }
        return componentsMap;
    }


}
