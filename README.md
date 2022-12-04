# Mower's problematic solution

SEAT:CODE has been asked for a really important project. We need to develop an application that helps in controlling brand new mowers from the SEAT Martorell Factory. SEAT has rolled out brand new robotic mowers that are able to cut the grass and to inspect the terrain with their cameras to identify problems in the green areas.

## Specifications
- The application is a maven project.
- It uses lombok to help with some autogenerated code.
- It uses junit5 and mockito to carry on with the unit and integration tests.
- maven compiler sources 1.8

## Input

At the time of running the .jar pass as first argument a path to a directory containing the use cases. The use cases will be files with the information according to the input format explained in the instructions pdf.

```
java -jar simple-0.0.1-SNAPSHOT.jar "/absolute/path/to/the/cases/folder"
```

case1.txt

```
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```
It can be more than one file, the application will read them all as different cases and run them sequentially.

## Run

Build the project
```
mvn -U clean install
```
Locate generated jar under 'target' directory and run the project
(have the cases ready in a directory and provide the path to it as first argument)
```
java -jar simple-0.0.1-SNAPSHOT.jar "/absolute/path/to/the/cases/folder"
```
## Output

Output will print in the console the result information according to the output format explained in the instructions pdf.

It will print a blank line between different cases.

```
1 3 N
5 1 E

2 3 W
4 1 S
```

Thanks for the opportunity and best regards.