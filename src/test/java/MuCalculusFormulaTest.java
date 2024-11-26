import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the MuCalculusFormula class.
 */
class MuCalculusFormulaTest {
    private void TestConstruction(String encodedFormula) {
        assertEquals(encodedFormula, (new MuCalculusFormula(encodedFormula)).toString());
    }

    @Test
    public void falseConstructionTest() {
        TestConstruction("false");
    }

    @Test
    public void trueConstructionTest() {
        TestConstruction("true");
    }

    @Test
    public void recursionVariableConstructionTest() {
        TestConstruction("X");
    }

    @Test
    public void conjunctionConstructionTest() {
        TestConstruction("(false&&true)");
        TestConstruction("((false&&true)&&X)");
        TestConstruction("((false&&true)&&(false&&true))");
        TestConstruction("((false||true)&&(false&&true))");
    }

    @Test
    public void disjunctionConstructionTest() {
        TestConstruction("(false||true)");
        TestConstruction("((false||true)||X)");
        TestConstruction("((false||true)||(false||true))");
        TestConstruction("((false&&true)||(false||true))");
    }

    @Test
    public void diamondConstructionTest() {
        TestConstruction("<test_action>X");
    }

    @Test
    public void boxConstructionTest() {
        TestConstruction("[test_action]X");
    }

    @Test
    public void muConstructionTest() {
        TestConstruction("muXfalse");
    }

    @Test
    public void nuConstructionTest() {
        TestConstruction("nuXfalse");
    }

    @Test
    public void recursiveConstructionTest() {
        TestConstruction("(muZ(true&&<action1>Z)||nuY[action2](<action3>nuAfalse&&muBtrue))");
    }
}
