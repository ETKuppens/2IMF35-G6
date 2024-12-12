import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
import java.io.File;
import java.io.FileNotFoundException;

class Reader {
  public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter labeled transition system, modal mu calculus formula and setting");

    String command = myObj.nextLine();  // Read user input
    String[] argumentsCommand = command.split("\\s+");

    System.out.println("LTS: " + argumentsCommand[0] + "\nMu-calculus: " + argumentsCommand[1] + "\nSetting: " + argumentsCommand[2]);  // Output user input
    
    ArrayList<String> LTSlist = new ArrayList<>();
    try {
      File myFile = new File(argumentsCommand[0]);
      Scanner myReader = new Scanner(myFile);  
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
        LTSlist.add(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    LabelledTransitionSystem labelledTransitionSystem = new LabelledTransitionSystem(LTSlist);
  }
}

