package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleParser {

    public static void main(String[] args) {
        int[] categoryCounts = new int[3]; // Array to store category counts: [Coding Conventions, Design Flaws, Comment Density]
        boolean commentDensityViolation = false; // Flag to track comment density violation

        try {
            // Load XML rules
            Document document = loadXmlDocument("C:\\Users\\patha\\IdeaProjects\\parser\\src\\main\\java\\org\\example\\trail.xml");

            // Extract rules from XML
            NodeList ruleNodes = document.getElementsByTagName("rule");

            // Parse Java source code and generate AST
            CompilationUnit cu = StaticJavaParser.parse(new File("C:\\Users\\patha\\IdeaProjects\\parser\\src\\main\\java\\org\\example\\Example.java"));

            // Process each rule
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element ruleElement = (Element) ruleNodes.item(i);

                // Check if the rule is enabled
                boolean ruleEnabled = Boolean.parseBoolean(ruleElement.getAttribute("enabled"));

                if (ruleEnabled) {
                    // Extract rule information from XML
                    String category = ruleElement.getElementsByTagName("category").item(0).getTextContent();
                    String patternStr = ruleElement.getElementsByTagName("pattern").item(0).getTextContent();
                    Pattern pattern = Pattern.compile(patternStr);
                    String message = ruleElement.getElementsByTagName("message").item(0).getTextContent();
                    String description = ruleElement.getElementsByTagName("description").item(0).getTextContent();

                    // Apply rule-based checks
                    boolean violationDetected = applyRule(cu, pattern, message);
                    if (violationDetected) {
                        if (category.equals("Coding Conventions")) {
                            categoryCounts[0]++;
                        } else if (category.equals("Design Flaws")) {
                            categoryCounts[1]++;
                        } else if (category.equals("Comment Density")) {
                            // Increment count only if it's the first violation detected
                            if (!commentDensityViolation) {
                                categoryCounts[2]++;
                                commentDensityViolation = true; // Set the flag to true
                                // Print comment density value along with the violation message
                                double commentDensity = calculateCommentDensity(cu);
                                System.out.println("Comment density: " + commentDensity);
                            }
                        }
                    } else {
                        System.out.println("Rule: " + description + " Passed");
                        System.out.println("-----------------------------");
                    }
                } else {
                    System.out.println("Rule: " + ruleElement.getAttribute("id") + " is disabled");
                    System.out.println("-----------------------------");
                }
            }

            // Check comment density (after all other rules)
            if (checkCommentDensity(cu)) {
                // If violation detected, set the flag and increment count if it's the first violation
                if (!commentDensityViolation) {
                    categoryCounts[2]++;
                    commentDensityViolation = true; // Set the flag to true
                    // Print comment density value along with the violation message
                    double commentDensity = calculateCommentDensity(cu);
                    System.out.println("Comment density: " + commentDensity);
                }
                System.out.println("Violation detected: Comment density is too high. Aim to keep it below 20%.");
                System.out.println("-----------------------------");
            }

            // Print category counts
            System.out.println("Category Counts: " + Arrays.toString(categoryCounts));

            // Calculate mean of the current weights
            double[] meanWeights = calculateMeanWeights("C:\\Users\\patha\\IdeaProjects\\parser\\src\\main\\java\\org\\example\\weights.txt");

            // Display mean weight
            System.out.println("Mean Weights: " + Arrays.toString(meanWeights));

            // Calculate Severity Score
            double severityScore = 0;
            for (int i = 0; i < categoryCounts.length; i++) {
                severityScore += categoryCounts[i] * meanWeights[i];
            }
            System.out.println("Severity Score: " + severityScore);

            // Ask user for desired error
            Scanner sc = new Scanner(System.in);
            System.out.print("Desired Error: ");
            double desiredError = sc.nextDouble();

            // Load weights from a text file
            double[] weights = loadWeightsFromFile("C:\\Users\\patha\\IdeaProjects\\parser\\src\\main\\java\\org\\example\\weights.txt");

            // Call adjustWeights function with categoryCounts converted to 2D double array
            double learningRate = 0.01;
            int maxIterations = 1000;
            weights = PerceptronAdjustWeights.adjustWeights(categoryCounts, weights, 0, desiredError, learningRate, maxIterations);
            System.out.println("Updated Weights: " + Arrays.toString(weights));

            // Update weights file with updated values
            appendWeightsToFile("C:\\Users\\patha\\IdeaProjects\\parser\\src\\main\\java\\org\\example\\weights.txt", weights);
            System.out.println("Weights file updated with updated values.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document loadXmlDocument(String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(filename));
    }

    private static double[] loadWeightsFromFile(String filename) throws IOException {
        double[] weights = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            String[] weightStrings = line.trim().split("\\s+");
            weights = new double[weightStrings.length];
            for (int i = 0; i < weightStrings.length; i++) {
                weights[i] = Double.parseDouble(weightStrings[i]);
            }
        }
        return weights;
    }

    private static void appendWeightsToFile(String filename, double[] weights) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(System.lineSeparator());
            for (double weight : weights) {
                writer.write(Double.toString(weight));
                writer.write(" ");
            }
        }
    }

    private static double[] calculateMeanWeights(String filename) throws IOException {
        double[] meanWeights = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            double sum = 0;
            while ((line = reader.readLine()) != null) {
                String[] weightStrings = line.trim().split("\\s+");
                if (meanWeights == null) {
                    meanWeights = new double[weightStrings.length];
                }
                for (int i = 0; i < weightStrings.length; i++) {
                    double weight = Double.parseDouble(weightStrings[i]);
                    meanWeights[i] += weight;
                }
                count++;
            }
            for (int i = 0; i < meanWeights.length; i++) {
                meanWeights[i] /= count;
            }
        }
        return meanWeights;
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

    private static boolean checkCommentDensity(CompilationUnit cu) {
        int totalLines = countLines(cu.toString());
        int commentLines = countLinesWithComments(cu);
        double commentDensity = (double) commentLines / totalLines;

        return commentDensity > 0.2;
    }

    private static int countLines(String code) {
        return code.split("\r\n|\r|\n").length;
    }

    private static int countLinesWithComments(CompilationUnit cu) {
        int linesWithComments = 0;
        for (Comment comment : cu.getAllContainedComments()) {
            linesWithComments += countLines(comment.getContent());
        }
        return linesWithComments;
    }

    private static double calculateCommentDensity(CompilationUnit cu) {
        int totalLines = countLines(cu.toString());
        int commentLines = countLinesWithComments(cu);
        return (double) commentLines / totalLines;
    }
}

class PerceptronAdjustWeights {
    public static double[] adjustWeights(int[] X, double[] weights, double threshold, double desiredTotalErrors, double learningRate, int maxIterations) {
        int numFeatures = X.length;
        int numSamples = 1; // Since X is a 1D array
        for (int iter = 0; iter < maxIterations; iter++) {
            double[] predictedTotalErrors = new double[numSamples];
            for (int i = 0; i < numSamples; i++) {
                double totalError = 0;
                for (int j = 0; j < numFeatures; j++) {
                    totalError += X[j] * weights[j];
                }
                predictedTotalErrors[i] = totalError;
            }
            for (int j = 0; j < numFeatures; j++) {
                double weightUpdate = 0;
                for (int i = 0; i < numSamples; i++) {
                    double error = desiredTotalErrors - predictedTotalErrors[i];
                    weightUpdate += learningRate * error * X[j];
                }
                weights[j] += weightUpdate;
            }
        }
        return weights;
    }
}
