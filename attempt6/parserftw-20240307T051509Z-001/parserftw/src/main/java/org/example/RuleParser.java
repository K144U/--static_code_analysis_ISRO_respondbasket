import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleParser {

    public static void main(String[] args) {
        try {
            // Load XML rules
            Document document = loadXmlDocument("C:\\Users\\patha\\Downloads\\parserftw-20240307T051509Z-001\\parserftw\\src\\main\\java\\org\\example\\rules.xml");

            // Extract rules from XML
            NodeList ruleNodes = document.getElementsByTagName("rule");

            // Parse Java source code and generate AST
            CompilationUnit cu = StaticJavaParser.parse(new File("C:\\Users\\patha\\Downloads\\parserftw-20240307T051509Z-001\\parserftw\\src\\main\\java\\org\\example\\Example.java"));

            // Process each rule
            // Process each rule
            boolean anyViolation = false;
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element ruleElement = (Element) ruleNodes.item(i);

                // Check if the rule is enabled
                boolean ruleEnabled = Boolean.parseBoolean(ruleElement.getAttribute("enabled"));

                if (ruleEnabled) {
                    // Extract rule information from XML
                    String patternStr = ruleElement.getElementsByTagName("pattern").item(0).getTextContent();
                    Pattern pattern = Pattern.compile(patternStr);
                    String message = ruleElement.getElementsByTagName("message").item(0).getTextContent();
                    String description = ruleElement.getElementsByTagName("description").item(0).getTextContent();

                    // Apply rule-based checks
                    boolean violationDetected = applyRule(cu, pattern, message);
                    if (violationDetected) {
                        anyViolation = true;
                    } else {
                        System.out.println("Rule: " + description + " Passed");
                        System.out.println("-----------------------------");
                    }
                } else {
                    System.out.println("Rule: " + ruleElement.getAttribute("id") + " is disabled");
                    System.out.println("-----------------------------");
                }
            }
            // Print overall result
            if (!anyViolation) {
                System.out.println("No violations detected. All rules passed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document loadXmlDocument(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(filename));
    }

    private static boolean applyRule(CompilationUnit cu, Pattern pattern, String message) {
        String javaCode = cu.toString();
        Matcher matcher = pattern.matcher(javaCode);
        if (matcher.find()) {
            System.out.println("Violation detected: " + message);
            System.out.println("Matching code: " + matcher.group());
            System.out.println("-----------------------------");
            return true;
        }
        return false;
    }
}
