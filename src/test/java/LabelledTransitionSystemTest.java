import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the LabelledTransitionSystemTest class.
 */

class LabelledTransitionSystemTest {
    private final ArrayList<String> testList = new ArrayList<>(
            Arrays.asList(
                    "des (0,12,10)",
                    "(0,\"lock(p2, f2)\",1)",
                    "(0,\"lock(p1, f1)\",2)",
                    "(1,\"lock(p1, f1)\",3)",
                    "(1,\"lock(p2, f1)\",4)",
                    "(2,\"lock(p2, f2)\",3)",
                    "(2,\"lock(p1, f2)\",5)",
                    "(4,\"eat(p2)\",6)",
                    "(5,\"eat(p1)\",7)",
                    "(6,\"free(p2, f2)\",8)",
                    "(7,\"free(p1, f1)\",9)",
                    "(8,\"free(p2, f1)\",0)",
                    "(9,\"free(p1, f2)\",0)"
            )
    );

    @Test
    public void createLTSTest() {
        LabelledTransitionSystem labelledTransitionSystem = new LabelledTransitionSystem(testList);
        labelledTransitionSystem.print();
        assertEquals(labelledTransitionSystem.getStateCount(), labelledTransitionSystem.getStates().size());
    }
}
