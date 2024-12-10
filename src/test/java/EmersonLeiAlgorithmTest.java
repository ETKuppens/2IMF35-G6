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
        walkToLoopLTS = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
                "des (0,3,3)",
                "(0,\"walk\",1)",
                "(1,\"walk\",2)",
                "(2,\"loop\",2)"
        )));

        MuCalculusFormula moe = new MuCalculusFormula("nuAnuBmuCmuD((A||B)||muE(E&&E))");

        EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();

        emersonLeiAlgorithm.eval(moe, walkToLoopLTS);


//        EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();
//
//
//        emersonLeiAlgorithm.eval(moe, walkToLoopLTS);
    }
}
