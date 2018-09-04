# CS753

## Students
#### Thomas Sorrell (tgs1003) - 904880776
#### John Schlim
#### Luxing Zeng
#### Timothy Ward

###### Code for assignments is in appropriately labled directories (hw1, hw2, etc)

## Building and running code (Assignment 1)
Our codebase is setup to work with Maven. Run `mvn clean compile` followed by `mvn exec:java` to run our program. Index and data directories can be changed by editing the Maven build plugin in pom.xml. Here is the appropriate section of code <br />
`<mainClass>com.cs753.assignments.assignment1.lucene.Main</mainClass> ` <br />
            `<arguments>` <br />
              `<argument>default</argument>` <br />
              `<argument>default</argument>` <br />
            `</arguments>`<br />
Simply change the default argument lines to the directories desired. The first argument is the output directory for the index, and the second is the directory for the paragraphs.cbor file
