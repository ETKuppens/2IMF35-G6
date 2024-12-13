
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
 
/**
 * Run the mu-calculus formulas for Part II - ex. 3
 */
public class RobotsTest {

    private final List<String> gameList = new ArrayList<>(Arrays.asList(
        "robots_50.aut",
        "robots_100.aut",
        "robots_150.aut",
        "robots_200.aut",
        "robots_250.aut",
        "robots_300.aut",
        "robots_350.aut",
        "robots_400.aut",
        "robots_450.aut",
        "robots_500.aut"
    ));

    private final NaiveAlgorithm naive = new NaiveAlgorithm();
    private final EmersonLeiAlgorithm emersonLei = new EmersonLeiAlgorithm();

    /**
     * For each specified game, check if there is a play starting in (0,0)
     * such that player II wins.
     */
    @Test
    public void playerIIPlayWinPossible() throws FileNotFoundException {
        MuCalculusFormula f = new MuCalculusFormula("muX(<choose1><choose2>X||<won>true)");

        System.out.println(f.toString() + " nesting depth: "  + DepthFinder.findNestingDepth(f));
        System.out.println(f.toString() + " alternation depth: "  + DepthFinder.findAlternationDepth(f));
        System.out.println(f.toString() + " dependent alternation depth: "  + DepthFinder.findDAD(f));

        for (int i = 0; i < gameList.size(); i++) {
            String gameName = gameList.get(i);
            
            Scanner gameFile = new Scanner(new File(getClass().getClassLoader().getResource(gameName).getFile()));
            ArrayList<String> LTS = new ArrayList<>();

            while (gameFile.hasNext()) {
                LTS.add(gameFile.nextLine());
            }

            gameFile.close();

            LabelledTransitionSystem game = new LabelledTransitionSystem(LTS);
            ArrayList<String> evalNaive = naive.eval(f, game);
            ArrayList<String> evalEmersonLei = emersonLei.eval(f, game);

            // Check the output;
            // we are looking for that position (0,0) - so state 0 - holds!
            boolean naiveHolds = evalNaive.contains(game.getStartNode().toString());
            boolean emersonLeiHolds = evalEmersonLei.contains(game.getStartNode().toString());

            System.out.println("Game " + gameName + ": naive: " + naiveHolds + ", lei: " + emersonLeiHolds);
        }
    }

    /**
     * Check if player II has a strategy to win when strating in (0,0).
     */
    @Test
    public void playerIIPlayWinAlways() throws FileNotFoundException {
        MuCalculusFormula f = new MuCalculusFormula("muX([choose1]<choose2>X||<won>true)");

        System.out.println(f.toString() + " nesting depth: "  + DepthFinder.findNestingDepth(f));
        System.out.println(f.toString() + " alternation depth: "  + DepthFinder.findAlternationDepth(f));
        System.out.println(f.toString() + " dependent alternation depth: "  + DepthFinder.findDAD(f));

        for (int i = 0; i < gameList.size(); i++) {
            String gameName = gameList.get(i);
            
            Scanner gameFile = new Scanner(new File(getClass().getClassLoader().getResource(gameName).getFile()));
            ArrayList<String> LTS = new ArrayList<>();

            while (gameFile.hasNext()) {
                LTS.add(gameFile.nextLine());
            }

            gameFile.close();

            LabelledTransitionSystem game = new LabelledTransitionSystem(LTS);
            ArrayList<String> evalNaive = naive.eval(f, game);
            ArrayList<String> evalEmersonLei = emersonLei.eval(f, game);

            // Check the output;
            // we are looking for that position (0,0) - so state 0 - holds!
            boolean naiveHolds = evalNaive.contains(game.getStartNode().toString());
            boolean emersonLeiHolds = evalEmersonLei.contains(game.getStartNode().toString());

            System.out.println("Game " + gameName + ": naive: " + naiveHolds + ", lei: " + emersonLeiHolds);
        }
    }

}