import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class


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
    List<String> lines = new ArrayList<>();

    MuCalculusFormula muCalculusFormula = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(argumentsCommand[1]))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    }

    for (String line : lines) {
      if (!line.contains("%") && !line.equals("")) {
        muCalculusFormula = new MuCalculusFormula(MuCalculusInputCleaner.cleanInput(line));
      }
    }

    LabelledTransitionSystem labelledTransitionSystem = new LabelledTransitionSystem(LTSlist);
    
    ArrayList<String> eval = new ArrayList<>();

    Integer nestingDepth = DepthFinder.findNestingDepth(muCalculusFormula);
    Integer alternationDepth = DepthFinder.findAlternationDepth(muCalculusFormula);

    System.out.println("Nesting depth = " + nestingDepth.toString());
    System.out.println("Alternation depth = " + alternationDepth.toString());

    if (argumentsCommand[2].equals("naive")) {
      System.out.println("Using naive algorithm");
      NaiveAlgorithm naiveAlgorithm = new NaiveAlgorithm();
      
      eval = naiveAlgorithm.eval(muCalculusFormula, labelledTransitionSystem);

    } else if (argumentsCommand[2].equals("EL")) {
      System.out.println("Using Emerson Lei algorithm");
      EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();

      eval = emersonLeiAlgorithm.eval(muCalculusFormula, labelledTransitionSystem);


    } else {
      System.out.println("Please select either 'naive' or 'EL' as setting");
    }
  }
}

