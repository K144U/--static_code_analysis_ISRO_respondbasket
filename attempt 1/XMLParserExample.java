import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XMLParserExample {
    public static void main(String[] args) {
        try {
            // Step 1: Create DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Step 2: Parse XML File
            Document document = builder.parse("example.xml");

            // Step 3: Traverse and Manipulate Document
            Element rootElement = document.getDocumentElement();

            // Step 4: Access Elements and Attributes
            NodeList nodeList = rootElement.getElementsByTagName("elementName");
            Node node = nodeList.item(0);
            String value = node.getTextContent();

            // Step 5: Print the Result
            System.out.println("Value of 'elementName': " + value);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
