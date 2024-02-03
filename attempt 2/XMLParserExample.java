import javax.xml.parsers.DocumentBuilder;
import java.util.*;
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
            NodeList bookList = rootElement.getElementsByTagName("book");

            if (bookList.getLength() > 0) {
                Element firstBook = (Element) bookList.item(0);
                String title = getElementValue(firstBook, "title");
                String author = getElementValue(firstBook, "author");
                String price = getElementValue(firstBook, "price");

                // Step 5: Print the Result
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Price: $" + price);
            } else {
                System.out.println("No books found in the bookstore.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
