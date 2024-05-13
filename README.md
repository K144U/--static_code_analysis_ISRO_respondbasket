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
public class Example {
    public static void main(String[] args) {
        int x = 10; // Valid declaration
        int y, z; // Violation: Multiple variable declarations in a single line
    }
}
```

Running the Static Code Analyzer on this code would detect the violation of the rule prohibiting multiple variable declarations in a single line.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.

