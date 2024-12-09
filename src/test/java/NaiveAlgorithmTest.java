
/**
 * Test class for the Naive model checking algorithm.
 */
public class NaiveAlgorithmTest extends ModelCheckingAlgorithmTest {

    @Override
    void initializeModelCheckingAlgorithm() {
        this.algorithm = new NaiveAlgorithm();
    }
}
