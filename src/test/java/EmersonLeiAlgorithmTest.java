import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Emerson Lei model checking algorithm.
 */


public class EmersonLeiAlgorithmTest extends ModelCheckingAlgorithmTest {

    @Override
    void initializeModelCheckingAlgorithm() {
        this.algorithm = new EmersonLeiAlgorithm();
    }


    @Test
    public void ModelTest() {
        LabelledTransitionSystem modal_ops = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,14,8)",
                "(0,\"tau\",1)",
                "(0,\"tau\",2)",
                "(1,\"tau\",3)",
                "(1,\"tau\",4)",
                "(2,\"tau\",5)",
                "(2,\"tau\",4)",
                "(3,\"b\",0)",
                "(3,\"a\",6)",
                "(4,\"tau\",7)",
                "(4,\"tau\",6)",
                "(5,\"a\",0)",
                "(5,\"a\",7)",
                "(6,\"tau\",2)",
                "(7,\"b\",1)"
        )));

        LabelledTransitionSystem fixed_points = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                        "des (0,14,8)",
                        "(0,\"tau\",1)",
                        "(0,\"tau\",2)",
                        "(1,\"tau\",3)",
                        "(1,\"tau\",4)",
                        "(2,\"tau\",5)",
                        "(2,\"tau\",4)",
                        "(3,\"b\",0)",
                        "(3,\"a\",6)",
                        "(4,\"tau\",7)",
                        "(4,\"tau\",6)",
                        "(5,\"a\",0)",
                        "(5,\"a\",7)",
                        "(6,\"tau\",2)",
                        "(7,\"b\",1)"
        )));

        MuCalculusFormula noe = new MuCalculusFormula("nuXX");
        MuCalculusFormula noe2 = new MuCalculusFormula("muYY");
        MuCalculusFormula noe3 = new MuCalculusFormula("nuXmuY(X||Y)");
        MuCalculusFormula noe4 = new MuCalculusFormula("nuXmuY(X&&Y)");
        MuCalculusFormula noe5 = new MuCalculusFormula("nuX(X&&muYY)");

        MuCalculusFormula moe = new MuCalculusFormula("[tau]true");
        MuCalculusFormula moe2 = new MuCalculusFormula("<tau>[tau]true");
        MuCalculusFormula moe3 = new MuCalculusFormula("[tau]false");
        MuCalculusFormula moe4 = new MuCalculusFormula("<tau>[tau]false");
        MuCalculusFormula moe5 = new MuCalculusFormula("<tau>false");

        EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();
        NaiveAlgorithm naiveAlgorithm = new NaiveAlgorithm();

        assertEquals(emersonLeiAlgorithm.eval(moe, modal_ops).size(), naiveAlgorithm.eval(moe, modal_ops).size());
        assertEquals(emersonLeiAlgorithm.eval(moe2, modal_ops).size(), naiveAlgorithm.eval(moe2, modal_ops).size());
        assertEquals(emersonLeiAlgorithm.eval(moe3, modal_ops).size(), naiveAlgorithm.eval(moe3, modal_ops).size());
        assertEquals(emersonLeiAlgorithm.eval(moe4, modal_ops).size(), naiveAlgorithm.eval(moe4, modal_ops).size());
        assertEquals(emersonLeiAlgorithm.eval(moe5, modal_ops).size(), naiveAlgorithm.eval(moe5, modal_ops).size());

        assertEquals(emersonLeiAlgorithm.eval(noe, fixed_points).size(), naiveAlgorithm.eval(noe, fixed_points).size());
        assertEquals(emersonLeiAlgorithm.eval(noe2, fixed_points).size(), naiveAlgorithm.eval(noe2, fixed_points).size());
        assertEquals(emersonLeiAlgorithm.eval(noe3, fixed_points).size(), naiveAlgorithm.eval(noe3, fixed_points).size());
        assertEquals(emersonLeiAlgorithm.eval(noe4, fixed_points).size(), naiveAlgorithm.eval(noe4, fixed_points).size());
        assertEquals(emersonLeiAlgorithm.eval(noe5, fixed_points).size(), naiveAlgorithm.eval(noe5, fixed_points).size());

        System.out.println("womp");

        System.out.println(DepthFinder.findNestingDepth(new MuCalculusFormula("(muAnuB(A||B)&&muCmuD(C||muE(E||E)))"), 0));

        System.out.println(DepthFinder.findAlternationDepth(new MuCalculusFormula("(muAnuB(A||B)&&muCmuD(C||muE(E||E)))"), 0, null));
        System.out.println(DepthFinder.findAlternationDepth(new MuCalculusFormula("(muAnuB(A||B)&&muCnuD(C||muE(E||E)))"), 0, null));

//        EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();
//
//
//        emersonLeiAlgorithm.eval(moe, walkToLoopLTS);
    }
}
