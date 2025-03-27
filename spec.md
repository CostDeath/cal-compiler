# Assignment 1: A Lexical and Syntax Analyser

## Aim
The aim of this assignment is to implement a lexical analyser (scanner) and syntax analyser (parser) using Antlr4 for a
simple language called CAL.

The details of the CAL language are given [here](cal.pdf).

```
file.cal parsed successfully
```
```
file.cal has not parsed
```

Please click here to review the School's policy on plagiarism. All submissions will be checked for plagiarism and severe
penalties will apply.

# Assignment 2: Semantic Analysis and Intermediate Representation

## Aim

The aim of this assignment is to add semantic analysis checks and intermediate representation generation to the lexical
and syntax analyser you have implement in Assignment 1. The generated intermediate code should be a 3-address code and
stored in a file with the ".ir" extension.

You will need to extend your submission for Assignment 1 to:
- Generate a Parse Tree.
- Add a Symbol Table that can handle scope.
- Perform a set of semantic checks. This following is a list of typical semantic checks:
  - Is every identifier declared within scope before its is used?
  - Is no identifier declared more than once in the same scope?
  - Is the left-hand side of an assignment a variable of the correct type?
  - Are the arguments of an arithmetic operator the integer variables or integer constants?
  - Are the arguments of a boolean operator boolean variables or boolean constants?
  - Is there a function for every invoked identifier?
  - Does every function call have the correct number of arguments?
  - Is every variable both written to and read from?
  - Is every function called?
- Generate an Intermediate Representation using 3-address code.

Feel free to add any additional semantic checks you can think of!

The .jar file for a 3-Address Code Interpreter is available at: [here](TACi.jar). It is described [here](taci.pdf). 