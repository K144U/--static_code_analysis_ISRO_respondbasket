package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, JavaParser!");

        // Call the CustomJavaParserRule to check for variable declarations on the same line
        try {
            CustomJavaParserRule customJavaParserRule = new CustomJavaParserRule("C:\\Users\\patha\\IdeaProjects\\Parser1\\src\\main\\java\\org\\example\\SyntaxErrorExample.java");
            customJavaParserRule.checkVariableDeclarations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}