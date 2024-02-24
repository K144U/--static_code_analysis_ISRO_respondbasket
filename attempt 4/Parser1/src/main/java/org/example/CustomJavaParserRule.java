package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class CustomJavaParserRule {
    private final String filePath;
    private final JavaParser javaParser;

    public CustomJavaParserRule(String filePath) {
        this.filePath = filePath;
        this.javaParser = new JavaParser();
    }

    public void checkVariableDeclarations() {
        // Parse the Java file
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(fileInputStream);

            // Check for parsing errors
            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();

                // Create a custom visitor to check for variable declarations on the same line
                VariableDeclarationVisitor visitor = new VariableDeclarationVisitor();
                visitor.visit(compilationUnit, null);
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

    private class VariableDeclarationVisitor extends VoidVisitorAdapter<Void> {
        private int lastLine = -1;

        @Override
        public void visit(VariableDeclarator variableDeclarator, Void arg) {
            // Check if the variable declaration is in the same line as the previous one
            int currentLine = variableDeclarator.getBegin().get().line;
            if (lastLine != -1 && currentLine == lastLine) {
                System.out.println("Violation: Two variables declared on the same line");
            }

            lastLine = currentLine;
            super.visit(variableDeclarator, arg);
        }
    }
}