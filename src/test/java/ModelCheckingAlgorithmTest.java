
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class ModelCheckingAlgorithmTest {

    LabelledTransitionSystem singleStateLTS;
    LabelledTransitionSystem noTransitionsLTS;
    LabelledTransitionSystem twoStateLoopLTS;
    LabelledTransitionSystem twoStateLoopDifferentLabelsLTS;
    LabelledTransitionSystem walkToLoopLTS;
    LabelledTransitionSystem splitToLoopLTS;
    LabelledTransitionSystem randomSplitToLoopLTS;
    LabelledTransitionSystem walkToLoopToEscapeLTS;

    /* The algorithm used in all tests. */
    protected ModelCheckingAlgorithm algorithm = new NaiveAlgorithm();

    /**
     * Initialize {@code this.algorithm}.
     */
    @BeforeAll
    abstract void initializeModelCheckingAlgorithm();

    @BeforeEach
    private void initializeLTS() {
        singleStateLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList("des (0,0,1)")));
        noTransitionsLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList("des (0,0,9)")));

        twoStateLoopLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,2,2)",
                "(0,\"go\",1)",
                "(1,\"go\",0)"
        )));

        twoStateLoopDifferentLabelsLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,2,2)",
                "(0,\"come\",1)",
                "(1,\"go\",0)"
        )));

        walkToLoopLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,3,3)",
                "(0,\"walk\",1)",
                "(1,\"walk\",2)",
                "(2,\"loop\",2)"
        )));

        splitToLoopLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,4,3)",
                "(0,\"splitleft\",1)",
                "(0,\"splitright\",2)",
                "(1,\"loop1\",1)",
                "(2,\"loop2\",2)"
        )));

        randomSplitToLoopLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,4,3)",
                "(0,\"split\",1)",
                "(0,\"split\",2)",
                "(1,\"loop1\",1)",
                "(2,\"loop2\",2)"
        )));

        walkToLoopToEscapeLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,5,4)",
                "(0,\"walk\",1)",
                "(1,\"walk\",2)",
                "(2,\"loop1\",2)",
                "(2,\"escape\",3)",
                "(3,\"loop2\",3)"
        )));
    }

    /**
     * Test for the 'true' mu-calculus formula. This formula should hold on all
     * states of any LTS.
     */
    private void testTrue(LabelledTransitionSystem M) {
        ArrayList<String> eval = algorithm.eval(new MuCalculusFormula("true"), M);
        ArrayList<String> allStates = M.getStates();

        // Check if eval and allStates are equivalent.
        assertEquals(allStates.size(), eval.size());

        for (String s : eval) {
            assertTrue(allStates.contains(s));
        }
    }

    /**
     * Test for the 'false' mu-calculus formula. This formula should hold on no
     * states of any LTS.
     */
    private void testFalse(LabelledTransitionSystem M) {
        ArrayList<String> eval = algorithm.eval(new MuCalculusFormula("false"), M);
        assertEquals(0, eval.size());
    }

    /**
     * Check if the evaluation of a mu-calculus formula on an LTS gives the
     * expected output.
     */
    private void testEvaluation(LabelledTransitionSystem M, MuCalculusFormula f, Collection<String> expected) {
        ArrayList<String> eval = algorithm.eval(f, M);

        assertEquals(expected.size(), eval.size());

        for (String s : expected) {
            assertTrue(eval.contains(s));
        }
    }

    @Test
    public void testTrue() {
        testTrue(singleStateLTS);
        testTrue(noTransitionsLTS);
        testTrue(twoStateLoopLTS);
    }

    @Test
    public void testFalse() {
        testFalse(singleStateLTS);
        testFalse(noTransitionsLTS);
        testFalse(twoStateLoopLTS);
    }

    @Test
    public void testConjunction() {
        testEvaluation(singleStateLTS, new MuCalculusFormula("(true&&true)"), singleStateLTS.getStates());
        testEvaluation(singleStateLTS, new MuCalculusFormula("(true&&false)"), new ArrayList<>());
        testEvaluation(twoStateLoopDifferentLabelsLTS, new MuCalculusFormula("(<come>true&&<go>true)"), new ArrayList<>());
        testEvaluation(twoStateLoopDifferentLabelsLTS, new MuCalculusFormula("(<come>true&&<come><go>true)"), new ArrayList<>(Arrays.asList("0")));
    }

    @Test
    public void testDisjunction() {
        testEvaluation(singleStateLTS, new MuCalculusFormula("(false||true)"), singleStateLTS.getStates());
        testEvaluation(singleStateLTS, new MuCalculusFormula("(false||false)"), new ArrayList<>());
        testEvaluation(twoStateLoopDifferentLabelsLTS, new MuCalculusFormula("(<come>true||<go>true)"), twoStateLoopDifferentLabelsLTS.getStates());
    }

    @Test
    public void testDiamond() {
        testEvaluation(singleStateLTS, new MuCalculusFormula("<noneexsistantlabel>true"), new ArrayList<>());
        testEvaluation(walkToLoopLTS, new MuCalculusFormula("<walk>true"), new ArrayList<>(Arrays.asList("0", "1")));
        testEvaluation(splitToLoopLTS, new MuCalculusFormula("<splitleft>true"), new ArrayList<>(Arrays.asList("0")));
        testEvaluation(splitToLoopLTS, new MuCalculusFormula("<loop1>true"), new ArrayList<>(Arrays.asList("1")));
        testEvaluation(splitToLoopLTS, new MuCalculusFormula("<loop2>true"), new ArrayList<>(Arrays.asList("2")));
        testEvaluation(splitToLoopLTS, new MuCalculusFormula("<loop>false"), new ArrayList<>());
    }

    @Test
    public void testBox() {
        testEvaluation(singleStateLTS, new MuCalculusFormula("[noneexsistantlabel]true"), singleStateLTS.getStates());
        testEvaluation(walkToLoopLTS, new MuCalculusFormula("[walk]true"), walkToLoopLTS.getStates());
        testEvaluation(walkToLoopLTS, new MuCalculusFormula("[walk]<walk>true"), new ArrayList<>(Arrays.asList("0", "2")));
        testEvaluation(randomSplitToLoopLTS, new MuCalculusFormula("[split]<loop1>true"), new ArrayList<>(Arrays.asList("1", "2")));
    }

    @Test
    public void testMu() {
        testEvaluation(noTransitionsLTS, new MuCalculusFormula("muXtrue"), noTransitionsLTS.getStates());
        testEvaluation(noTransitionsLTS, new MuCalculusFormula("muXfalse"), new ArrayList<>());
        testEvaluation(walkToLoopLTS, new MuCalculusFormula("muX(<loop>true||<walk>X)"), walkToLoopLTS.getStates());
        testEvaluation(walkToLoopToEscapeLTS, new MuCalculusFormula("muX(<loop1>true||<walk>X)"), new ArrayList<>(Arrays.asList("0", "1", "2")));
    }

    @Test
    public void testNu() {
        testEvaluation(noTransitionsLTS, new MuCalculusFormula("nuXtrue"), noTransitionsLTS.getStates());
        testEvaluation(noTransitionsLTS, new MuCalculusFormula("nuXfalse"), new ArrayList<>());
        testEvaluation(walkToLoopLTS, new MuCalculusFormula("nuX(<loop>true||<walk>X)"), walkToLoopLTS.getStates());
        testEvaluation(walkToLoopToEscapeLTS, new MuCalculusFormula("nuX(<loop1>true||<walk>X)"), new ArrayList<>(Arrays.asList("0", "1", "2")));
    }
}
