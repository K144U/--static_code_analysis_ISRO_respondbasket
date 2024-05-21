# Static Code Analyzer

The Static Code Analyzer is a tool designed to analyze Java source code statically and identify potential issues, violations of coding conventions, and design flaws. It parses Java source code files, applies predefined rules, and reports any detected violations or areas of improvement.

## Features

- **Rule-Based Analysis**: Define rules in XML format to specify coding conventions, design principles, and best practices.
- **Rule Evaluation**: Apply rules to parsed Java source code and detect violations based on predefined patterns.
- **Violation Reporting**: Generate reports highlighting detected violations along with relevant code snippets and violation messages.
- **Customizable**: Modify rules and adjust thresholds to tailor the analysis to specific project requirements.

## Getting Started

1. **Clone the Repository**: Clone this repository to your local machine.

2. **Define Rules**: Define or customize rules in the `rules.xml` file according to your project's coding standards and conventions.

3. **Run the Analyzer**: Execute the `RuleParser.java` file to run the static code analyzer on your Java source code files.

4. **Review Reports**: Review the generated reports in the console or output files to identify and address detected violations.

## Example

Consider the following snippet of Java code:

```java
package org.example;

public class Example {

    public static void main(String[] args) {
        int a = 10; // Valid
        int b, c; // Multiple variable declarations in a single line
        int d, e = 20; // Multiple variable initializations in a single line

        for (int i = 0; i < 10; i = i + 2) { // Reassigning loop variable
            // Do something
        }

        // Abstract class without any meaningful methods
        abstract class AbstractWithoutMethods {
        }

        try {
            // some code that may throw an Exception
        } catch (Exception ex) {
            throw ex; // Rethrowing the same exception
        }

        StringBuffer strBuf = new StringBuffer(); // StringBuffer in long-lived object
        strBuf.append("some string data");
    }

    public void CalculateSomeResult(int param1) {
        param1 = 10; // Reassigning parameter value
    }

    public void myMethod() { // Incorrect naming convention
        int x = 5; // Unused local variable
    }
}
```

Running the Static Code Analyzer on this code would detect the following violations:

![StaticCodeAnalyser](https://github.com/K144U/--static_code_analysis_ISRO_respondbasket/assets/133573718/80e229c5-9fc2-45b1-a8fc-2abcb9cbadae)


## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.

