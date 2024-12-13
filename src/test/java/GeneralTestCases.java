import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Emerson Lei model checking algorithm.
 */


public class GeneralTestCases extends ModelCheckingAlgorithmTest {

    @Override
    void initializeModelCheckingAlgorithm() {
        this.algorithm = new EmersonLeiAlgorithm();
    }


    @Test
    public void ModelTest() {
        LabelledTransitionSystem lts = new LabelledTransitionSystem(new ArrayList<>(Arrays.asList(
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

        MuCalculusFormula fixPoint1 = new MuCalculusFormula("nuXX");
        MuCalculusFormula fixPoint2 = new MuCalculusFormula("muYY");
        MuCalculusFormula fixPoint3 = new MuCalculusFormula("nuXmuY(X||Y)");
        MuCalculusFormula fixPoint4 = new MuCalculusFormula("nuXmuY(X&&Y)");
        MuCalculusFormula fixPoint5 = new MuCalculusFormula("nuX(X&&muYY)");

        MuCalculusFormula modalOp1 = new MuCalculusFormula("[tau]true");
        MuCalculusFormula modalOp2 = new MuCalculusFormula("<tau>[tau]true");
        MuCalculusFormula modalOp3 = new MuCalculusFormula("[tau]false");
        MuCalculusFormula modalOp4 = new MuCalculusFormula("<tau>[tau]false");
        MuCalculusFormula modalOp5 = new MuCalculusFormula("<tau>false");

        MuCalculusFormula combined1 = new MuCalculusFormula("nuX(<tau>X&&muY(<tau>Y||[a]false))");
        MuCalculusFormula combined2 = new MuCalculusFormula("nuX<tau>X");
        MuCalculusFormula combined3 = new MuCalculusFormula("nuXmuY(<tau>Y||<a>X)");
        MuCalculusFormula combined4 = new MuCalculusFormula("nuXmuY((<tau>Y||<a>Y)||<b>X)");
        MuCalculusFormula combined5 = new MuCalculusFormula("nuX(X&&muYY)");

        MuCalculusFormula form1 = new MuCalculusFormula("false");
        MuCalculusFormula form2 = new MuCalculusFormula("true");
        MuCalculusFormula form3 = new MuCalculusFormula("(false&&true)");
        MuCalculusFormula form4 = new MuCalculusFormula("(true&&false)");
        MuCalculusFormula form5 = new MuCalculusFormula("(true&&true)");
        MuCalculusFormula form6 = new MuCalculusFormula("(false||true)");
        MuCalculusFormula form7 = new MuCalculusFormula("(false||false)");
        MuCalculusFormula form8 = new MuCalculusFormula("(true||false)");
        MuCalculusFormula form9 = new MuCalculusFormula("(true||true)");

        EmersonLeiAlgorithm emersonLeiAlgorithm = new EmersonLeiAlgorithm();
        NaiveAlgorithm naiveAlgorithm = new NaiveAlgorithm();

        assertEquals(emersonLeiAlgorithm.eval(fixPoint1, lts), naiveAlgorithm.eval(fixPoint1, lts));
        assertEquals(emersonLeiAlgorithm.eval(fixPoint2, lts), naiveAlgorithm.eval(fixPoint2, lts));
        assertEquals(emersonLeiAlgorithm.eval(fixPoint3, lts), naiveAlgorithm.eval(fixPoint3, lts));
        assertEquals(emersonLeiAlgorithm.eval(fixPoint4, lts), naiveAlgorithm.eval(fixPoint4, lts));
        assertEquals(emersonLeiAlgorithm.eval(fixPoint5, lts), naiveAlgorithm.eval(fixPoint5, lts));

        System.out.println(emersonLeiAlgorithm.eval(combined1, lts));
        System.out.println(emersonLeiAlgorithm.eval(combined2, lts));
        System.out.println(emersonLeiAlgorithm.eval(combined3, lts));
        System.out.println(emersonLeiAlgorithm.eval(combined4, lts));
        System.out.println(emersonLeiAlgorithm.eval(combined5, lts));
        System.out.println(emersonLeiAlgorithm.eval(form6, lts));
        System.out.println(emersonLeiAlgorithm.eval(form7, lts));
        System.out.println(emersonLeiAlgorithm.eval(form8, lts));
        System.out.println(emersonLeiAlgorithm.eval(form9, lts));

        assertEquals(emersonLeiAlgorithm.eval(modalOp1, lts), naiveAlgorithm.eval(modalOp1, lts));
        assertEquals(emersonLeiAlgorithm.eval(modalOp2, lts), naiveAlgorithm.eval(modalOp2, lts));
        assertEquals(emersonLeiAlgorithm.eval(modalOp3, lts), naiveAlgorithm.eval(modalOp3, lts));
        assertEquals(emersonLeiAlgorithm.eval(modalOp4, lts), naiveAlgorithm.eval(modalOp4, lts));
        assertEquals(emersonLeiAlgorithm.eval(modalOp5, lts), naiveAlgorithm.eval(modalOp5, lts));

        assertEquals(emersonLeiAlgorithm.eval(combined1, lts), naiveAlgorithm.eval(combined1, lts));
        assertEquals(emersonLeiAlgorithm.eval(combined2, lts), naiveAlgorithm.eval(combined2, lts));
        assertEquals(emersonLeiAlgorithm.eval(combined3, lts), naiveAlgorithm.eval(combined3, lts));
        assertEquals(emersonLeiAlgorithm.eval(combined4, lts), naiveAlgorithm.eval(combined4, lts));
        assertEquals(emersonLeiAlgorithm.eval(combined5, lts), naiveAlgorithm.eval(combined5, lts));

        assertEquals(emersonLeiAlgorithm.eval(form1, lts), naiveAlgorithm.eval(form1, lts));
        assertEquals(emersonLeiAlgorithm.eval(form2, lts), naiveAlgorithm.eval(form2, lts));
        assertEquals(emersonLeiAlgorithm.eval(form3, lts), naiveAlgorithm.eval(form3, lts));
        assertEquals(emersonLeiAlgorithm.eval(form4, lts), naiveAlgorithm.eval(form4, lts));
        assertEquals(emersonLeiAlgorithm.eval(form5, lts), naiveAlgorithm.eval(form5, lts));
        assertEquals(emersonLeiAlgorithm.eval(form6, lts), naiveAlgorithm.eval(form6, lts));
        assertEquals(emersonLeiAlgorithm.eval(form7, lts), naiveAlgorithm.eval(form7, lts));
        assertEquals(emersonLeiAlgorithm.eval(form8, lts), naiveAlgorithm.eval(form8, lts));
        assertEquals(emersonLeiAlgorithm.eval(form9, lts), naiveAlgorithm.eval(form9, lts));
    }
}
