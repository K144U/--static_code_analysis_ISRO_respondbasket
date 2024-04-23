import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaParser {

    public static void main(String[] args) {
        try {
            // Load XML rules
            Document document = loadXmlDocument("D:\\Java Parser\\java_dom_parser\\src\\java_dom_parser\\My_Maven\\src\\main\\java\\trial.xml");

            // Extract rules from XML
            NodeList ruleNodes = document.getElementsByTagName("rule");

            // Parse Java source code and generate AST
            CompilationUnit cu = StaticJavaParser.parse(new File("D:\\Java Parser\\java_dom_parser\\src\\java_dom_parser\\My_Maven\\src\\main\\java\\Example.java"));

            // Process each rule
            List<String> violations = new ArrayList<>();
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
                    List<String> ruleViolations = applyRule(cu, pattern, message);
                    if (!ruleViolations.isEmpty()) {
                        violations.addAll(ruleViolations);
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
            if (!violations.isEmpty()) {
                System.out.println("Violations detected:");
                for (String violation : violations) {
                    System.out.println(violation);
                }
            } else {
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

    private static List<String> applyRule(CompilationUnit cu, Pattern pattern, String message) {
        List<String> violations = new ArrayList<>();
        String javaCode = cu.toString();
        Matcher matcher = pattern.matcher(javaCode);
        while (matcher.find()) {
            String violation = "Violation detected: " + message + "\nMatching code: " + matcher.group();
            if (!violations.contains(violation)) {
                violations.add(violation);
                violations.add("-----------------------------");
            }
        }
        return violations;
    }
}
