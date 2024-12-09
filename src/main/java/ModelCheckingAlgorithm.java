
import java.util.ArrayList;

/**
 *  * Interface for the Naive- and Emerson-Lei model checking algorithms.
 */
public interface ModelCheckingAlgorithm {

    /**
     * Get all states in M where f holds.
     *
     * @param f The mu-calculus formula against which the LTS is checked.
     * @param M The LTS to be checked.
     * @return An array holding all states in M.States for which mu-calculus
     * formula f holds.
     */
    public abstract ArrayList<String> eval(MuCalculusFormula f, LabelledTransitionSystem M);
}
