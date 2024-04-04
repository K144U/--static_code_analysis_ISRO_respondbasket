package org.example;

public class Example {
    public static void main(String[] args) {
        int a = 10,j=6; // This is a valid initialization
        int b, c, k,u ; // Violation: Multiple variable declarations in a single line are not allowed.
        int d, e = 20; // Violation: Multiple variable initializations in a single line are not allowed.
        float f = 0;
        char g;

    }

    public void MyClass() { // Violation: Method does not adhere to naming convention.
        int x = 5; // Violation: Has unused local variables.
        System.out.println("Hello World!");
    }
}
