
## Running the project
The repository is a Maven project running Java. Apache Maven must thus first be installed.
The entry point is in Reader.java
In order to run the project, use the following steps:

### From the command line
Run the following commands from the root of the project:
> mvn install
> java -jar target/Assignment1-0.0.1-SNAPSHOT.jar

### VS Code
- Install the "Extension Pack for Java" extension in the extensions tab of VS Code.
- Open the project root as a folder in VS Code (File/Open Folder...)
- Open the "JAVA PROJECTS" tab (bottom-left)
- Either click on the "Debug" icon next to "Assignment1" for the debug target, or right-click "Assignment1" and click "Run" for the release target.
--> Adding new class files to the project can easily be done by right-clicking "Assignment1", and clicking "New/Class".

### IntelliJ IDEA Community Edition
- Click on the "Open" button after launching the IDE, and select the root folder of the project.
- Open the project as a Maven project.
- Navigate to and open src/main/java/Main.java.
- Click the play button next to public static void main(String[] args).
- Now main is added as an entry point and can be rerun fast by clicking the play or debug button on the top-right of the screen.