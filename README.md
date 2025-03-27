# CAL Compiler

## Project Overview
The CAL compiler is a standard compiler for the CAL programming language into Three-Address Code. The code is parsed
using ANTLR and is translated using a visitor in java. The project was created as an assignment for a university final
year module.

## Assignment Details
The assignment required us to demonstrate our knowledge of compilers by building a compiler for the CAL language. The
provided file must be compiled into Three-Address Code (TAC). This compiler was to be written with a ANTLR parser from a
grammar file and a visitor written in Java.
- The spec for the CAL language must be followed, and can be found [here](cal.pdf).
- The spec for the TAC language must be followed, and can be found [here](taci.pdf).

For a full list of requirements, the [full specification](spec.md) for the assignment is available.

## Usage
### Running the program
*Maven must be installed in order to compile this program.*

In order to run the program, it must first be compiled. This can be done by running the following command in the `/src`
directory.
```sh
mvn clean compile
```

Running the program begins the simulation. This can be done by running the following command in the `/src` folder.
```sh
java -jar Main <file.cal>
```
Replacing `<file.cal>` with the path to your file.

### Running TAC
In order to run the compiled TAC, you may run the following command in the root directory.
```sh
java -jar TACi.jar <file.ir>
```
Replacing `<file.ir>` with the path to your file.

## Known Issues
- [ ] Broken negative arithmetic
- This is due to a misunderstanding in the lack of multiplication support by TAC.
- [ ] Broken number to boolean operations
- This is due to a misunderstanding in boolean operation support by TAC. Only `||` and `&&` operators are supported.
- [ ] Tests are incomplete
- Unit tests were planned to be extended, however time did not allow. This did not count for the grade.