package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;

public class JavaParserExample {
    public static void parseJavaFile(String filePath) {
        // Create an instance of JavaParser
        JavaParser javaParser = new JavaParser();

        // Parse the Java file
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(fileInputStream);

            // Check for parsing errors
            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();

                // Now you can work with the parsed AST (Abstract Syntax Tree)
                // For example, you can traverse the AST, analyze the code structure, etc.
                System.out.println("Parsed Java file AST:\n" + compilationUnit.toString());
            } else {
                // Handle parsing errors
                System.err.println("Parsing errors detected:");
                parseResult.getProblems().forEach(problem -> System.err.println(problem.toString()));
            }
        } catch (ParseProblemException e) {
            // Handle parse errors (e.g., syntax errors)
            System.err.println("Syntax error in the Java file:");
            e.getProblems().forEach(problem -> System.err.println(problem.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
