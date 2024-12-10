import java.util.Scanner;  // Import the Scanner class

class Reader {
  public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter labeled transition system, modal mu calculus formula and setting");

    String command = myObj.nextLine();  // Read user input
    String[] argumentsCommand = command.split("\\s+");

    System.out.println("LTS: " + argumentsCommand[0] + "\nMu-calculus: " + argumentsCommand[1] + "\nSetting: " + argumentsCommand[2]);  // Output user input

  }
}

