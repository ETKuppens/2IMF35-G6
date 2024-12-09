
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Naive algorithm for checking mu-calculus formulas against labelled transition systems,
 * as described by the pseudo-code of page 11/32 from the slides of lecture 4.
 */
public class NaiveAlgorithm {

    /**
     * Get all states in M where f holds.
     * @param f The mu-calculus formula against which the LTS is checked.
     * @param M The LTS to be checked.
     * @return An array holding all states in M.States for which mu-calculus formula f holds.
     */
    public static ArrayList<String> eval(MuCalculusFormula f, LabelledTransitionSystem M) {
        return eval(f, new AList(f), M);
    }

    private static ArrayList<String> eval(MuCalculusFormula f, AList A, LabelledTransitionSystem M) {
        switch (f.type) {
            case TRUE:
                // return S
                return M.getStates();
            case FALSE:
                // return \emptyset
                return new ArrayList<>();
            /*case ATOMIC_PROPOSITION:
                // return {s \in S | p \in L(s)}
                Set<String> states = new HashSet<>();

                for (Edge e : M.getEdges()) {
                    if (e.Label.equals(f.variableName)) {
                        states.add(f.variableName);
                    }
                }

                ArrayList<String> statesArray = new ArrayList<>();
                statesArray.addAll(states);

                return statesArray;*/
            case RECURSION_VARIABLE:
                // return A[i]
                return A.getAtVar(f.variableName);
            case CONJUNCTION:
                // return eval(f) \setconjunction eval(g)
                ArrayList<String> eval1 = eval(f.f, A, M);
                ArrayList<String> eval2 = eval(f.g, A, M);

                ArrayList<String> conjunctionList = new ArrayList<>();

                for (String s : eval1) {
                    if (eval2.contains(s)) {
                        conjunctionList.add(s);
                    }
                }

                return conjunctionList;
            case DISJUNCTION:
                // return eval(f) \setdisjunction eval(g)
                ArrayList<String> eval3 = eval(f.f, A, M);
                ArrayList<String> eval4 = eval(f.g, A, M);

                ArrayList<String> disjunctionList = eval3;

                for (String s : eval4) {
                    if (!eval3.contains(s)) {
                        disjunctionList.add(s);
                    }
                }

                return disjunctionList;
            case DIAMOND:
                // return {s \in S | \exists t \in S : s->^a t \implies t \in eval(g)}
                ArrayList<String> diamondList = new ArrayList<>();
                ArrayList<String> S = M.getStates();
                ArrayList<String> evalg;

                for (String s : S) {
                    boolean implicationHoldsForOne = false;

                    // Check if the implication holds for at least one t \in S.
                    ArrayList<Edge> edges = M.getEdges();

                    // Go through all edges from s and check if it holds the correct label.
                    // in that case, check if the end node t is in eval(g).
                    for (Edge e : edges) {
                        // Skip this edge if it doesn't start in node s.
                        if (!e.StartNode.equals(s)) {
                            continue;
                        }
                        // e.StartNode.equals(s)

                        // Skip this edge if its label is not f.variableName
                        if (!e.Label.equals(f.variableName)) {
                            continue;
                        }
                        // e.Label.equals(f.variableName)

                        // Check if t \in eval(g)
                        evalg = eval(f.f, A, M);
                        boolean tInEvalg = evalg.contains(e.EndNode);

                        if (tInEvalg) {
                            // Update implicationHoldsForOne and break out of this inner loop.
                            implicationHoldsForOne = true;
                            break;
                        }
                    }

                    // If the implication holds for at least one t, add s to the output.
                    if (implicationHoldsForOne) {
                        diamondList.add(s);
                    }
                }

                return diamondList;
            case BOX:
                // return {s \in S | \forall t \in S : s->^a t \implies t \in eval(g)}
                ArrayList<String> boxList = new ArrayList<>();
                S = M.getStates();

                for (String s : S) {
                    boolean implicationHoldsForall = true;

                    // Check if the implication holds for all t \in S.
                    ArrayList<Edge> edges = M.getEdges();

                    // Go through all edges from s and check if it holds the correct label.
                    // in that case, check if the end node t is in eval(g).
                    for (Edge e : edges) {
                        // Skip this edge if it doesn't start in node s.
                        if (!e.StartNode.equals(s)) {
                            continue;
                        }
                        // e.StartNode.equals(s)

                        // Skip this edge if its label is not f.variableName
                        if (!e.Label.equals(f.variableName)) {
                            continue;
                        }
                        // e.Label.equals(f.variableName)

                        // Check if t \in eval(g)
                        evalg = eval(f.f, A, M);
                        boolean tInEvalg = evalg.contains(e.EndNode);

                        // Update implicationHoldsForAll with the new found results.
                        implicationHoldsForall &= tInEvalg;
                    }

                    // If the implication holds for all t, add s to the output.
                    if (implicationHoldsForall) {
                        boxList.add(s);
                    }
                }

                return boxList;
            case MU:
                // A[i] := \emptyset;
                // repeat 
                //   X' := A[i];
                //   A[i] := eval(g)
                // until A[i] = X'
                // return A[i]
                ArrayList<String> Ai = A.getAtVar(f.variableName);

                // A[i] := \emptyset
                Ai.clear();

                ArrayList<String> oldAi;

                do {
                    // X' := A[i]
                    oldAi = new ArrayList<>(Ai);

                    // A[i] := eval(g)
                    ArrayList<String> evalf = eval(f.f, A, M);
                    Ai.clear();
                    Ai.addAll(evalf);
                } while (!oldAi.containsAll(Ai));
                // ^ Keep in mind that Ai will grow every iteration except the last.
                // Therefore, we only need to check if the old (smaller) Ai contains
                // every value in the new (bigger) Ai.

                return Ai;
            case NU:
                // A[i] := S;
                // repeat 
                //   X' := A[i];
                //   A[i] := eval(g)
                // until A[i] = X'
                // return A[i]
                Ai = A.getAtVar(f.variableName);

                // A[i] := S
                Ai.clear();
                Ai.addAll(M.getStates());

                do {
                    // X' := A[i]
                    oldAi = new ArrayList<>(Ai);

                    // A[i] := eval(g)
                    ArrayList<String> evalf = eval(f.f, A, M);
                    Ai.clear();
                    Ai.addAll(evalf);
                } while (!Ai.containsAll(oldAi));
                // ^ Keep in mind that Ai will shrink every iteration except the last.
                // Therefore, we only need to check if the new (smaller) Ai contains
                // every value in the old (bigger) Ai.

                return Ai;
        }
        return null;
    }

    /**
     * Class holding the intermediate values for calculating the fixed points,
     * with extended functionality allowing indexing by recursion variable name.
     */
    private static class AList extends ArrayList<ArrayList<String>> {

        /**
         * Construct an A list from the recursion variables of a mu-calculus
         * formula.
         *
         * @param f the mu-calculus formula to take the recursion variables
         * from.
         */
        public AList(MuCalculusFormula f) {
            addElements(f);
        }

        /**
         * Get the saved states A[i] of recursion variable Xi.
         */
        public ArrayList<String> getAtVar(String Xi) {
            int index = indexMapping.get(Xi);
            return this.get(index);
        }

        final private Map<String, Integer> indexMapping = new HashMap<>();

        private void addElements(MuCalculusFormula f) {
            switch (f.type) {
                case RECURSION_VARIABLE:
                    // Assume no unbound recursion variables!
                    return;
                case CONJUNCTION:
                case DISJUNCTION:
                    addElements(f.f);
                    addElements(f.g);
                    return;
                case DIAMOND:
                case BOX:
                    addElements(f.f);
                    return;
                case MU:
                case NU:
                    // Add the newly bound recursion variable to the mapping.
                    this.add(new ArrayList<>());
                    this.indexMapping.put(f.variableName, this.size() - 1);
                    // Recurse to the subformula.
                    addElements(f.f);
                    return;
                default:
            }
        }
    }
}
