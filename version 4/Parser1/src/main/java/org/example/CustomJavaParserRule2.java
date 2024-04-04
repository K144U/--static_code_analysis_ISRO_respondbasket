package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

public class CustomJavaParserRule2 {
    private final String filePath;
    private final JavaParser javaParser;

    public CustomJavaParserRule2(String filePath) {
        this.filePath = filePath;
        this.javaParser = new JavaParser();
    }

    public void checkUnusedAssignedVariables() {
        // Parse the Java file
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(fileInputStream);

            // Check for parsing errors
            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();

                // Create a custom visitor to check for unused assigned variables
                UnusedAssignedVariableVisitor visitor = new UnusedAssignedVariableVisitor();
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

    private static class UnusedAssignedVariableVisitor extends VoidVisitorAdapter<Void> {
        private Set<String> assignedVariables = new HashSet<>();
        private Set<String> usedVariables = new HashSet<>();

        @Override
        public void visit(AssignExpr assignExpr, Void arg) {
            Expression target = assignExpr.getTarget();
            if (target instanceof NameExpr) {
                String variableName = ((NameExpr) target).getNameAsString();
                assignedVariables.add(variableName);
            }

            super.visit(assignExpr, arg);
        }

        @Override
        public void visit(NameExpr nameExpr, Void arg) {
            usedVariables.add(nameExpr.getNameAsString());
            super.visit(nameExpr, arg);
        }

        @Override
        public void visit(VariableDeclarator variableDeclarator, Void arg) {
            String variableName = variableDeclarator.getNameAsString();
            if (assignedVariables.contains(variableName) && !usedVariables.contains(variableName)) {
                System.out.println("Warning: Unused assigned variable '" + variableName + "'");
            }

            super.visit(variableDeclarator, arg);
        }
    }
}
