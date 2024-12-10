import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EmersonLeiAlgorithm implements ModelCheckingAlgorithm {
    Map<String, ArrayList<String>> variableArrayMap = new HashMap<>();


    private void findVariables(MuCalculusFormula formula, LabelledTransitionSystem M) {
        if (formula.type == MuCalculusFormula.MuCalculusFormulaType.MU) {
            variableArrayMap.putIfAbsent(formula.variableName, new ArrayList<>()); // A[i] := ∅;
        }
        else if (formula.type == MuCalculusFormula.MuCalculusFormulaType.NU) {
            variableArrayMap.putIfAbsent(formula.variableName, new ArrayList<>(M.getStates())); // A[i] := S;
        }
        // Recursively go through the entire formula
        if (formula.f != null) {
            findVariables(formula.f, M);
        }
        if (formula.g != null) {
            findVariables(formula.g, M);
        }
    }

    private void init(MuCalculusFormula f, LabelledTransitionSystem M) {
        findVariables(f, M);
    }

    private ArrayList<MuCalculusFormula> findNestedFixPoints(MuCalculusFormula formula, ArrayList<MuCalculusFormula> formulas) {
        if (formula.f != null) {
            formulas = findNestedFixPoints(formula.f, formulas);
        }
        if (formula.g != null) {
            formulas = findNestedFixPoints(formula.g, formulas);
        }

        if (formula.type == MuCalculusFormula.MuCalculusFormulaType.NU
                || formula.type == MuCalculusFormula.MuCalculusFormulaType.MU) {
            formulas.add(formula);
        }

        return formulas;
    }

    private ArrayList<String> findFreeVariables() {
        return null;
    }

    private ArrayList<String> resetOpenSubFormulas(MuCalculusFormula initialFormula, MuCalculusFormula subsequentFormula, ArrayList<String> openFormulas) {
        ArrayList<MuCalculusFormula> formulas = findNestedFixPoints(initialFormula, new ArrayList<>());

        for (MuCalculusFormula formula : formulas) {
            if (formula.type == MuCalculusFormula.MuCalculusFormulaType.MU) {
                Boolean isOpen = false;

                if (isOpen) {
                    System.out.println('b');
                }
            }
        }

        return openFormulas;
    }

    @Override
    public ArrayList<String> eval(MuCalculusFormula f, LabelledTransitionSystem M) {
        init(f, M);
        return eval(f, variableArrayMap, M, null);
    }

    public ArrayList<String> eval(MuCalculusFormula f, Map<String, ArrayList<String>> A,
                                  LabelledTransitionSystem M, MuCalculusFormula.MuCalculusFormulaType binder) {
        switch (f.type) {
            case TRUE:
                // return S
                return M.getStates();
            case FALSE:
                // return \emptyset
                return new ArrayList<>();
            case RECURSION_VARIABLE:
                // return A[i]
                return A.get(f.variableName);
            case CONJUNCTION:
                // return eval(f) \setconjunction eval(g)
                ArrayList<String> eval1 = eval(f.f, A, M, binder);
                ArrayList<String> eval2 = eval(f.g, A, M, binder);

                ArrayList<String> conjunctionList = new ArrayList<>();

                for (String s : eval1) {
                    if (eval2.contains(s)) {
                        conjunctionList.add(s);
                    }
                }

                return conjunctionList;
            case DISJUNCTION:
                // return eval(f) \setdisjunction eval(g)
                ArrayList<String> eval3 = eval(f.f, A, M, binder);
                ArrayList<String> eval4 = eval(f.g, A, M, binder);

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
                        evalg = eval(f.f, A, M, binder);
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
                        evalg = eval(f.f, A, M, binder);
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
                ArrayList<String> Ai = variableArrayMap.get(f.variableName);
                // A[i] := \emptyset
                if (binder == MuCalculusFormula.MuCalculusFormulaType.NU) {
                    resetOpenSubFormulas(f, f, new ArrayList<>());
                }
                ArrayList<String> oldAi;

                do {
                    // X' := A[i]
                    oldAi = new ArrayList<>(Ai);

                    // A[i] := eval(g)
                    ArrayList<String> evalf = eval(f.f, A, M, MuCalculusFormula.MuCalculusFormulaType.MU);
                    Ai.clear();
                    Ai.addAll(evalf);
                } while (!oldAi.containsAll(Ai));

                return Ai;
            case NU:
                Ai = variableArrayMap.get(f.variableName);

                // A[i] := S
                Ai.clear();
                Ai.addAll(M.getStates());

                do {
                    // X' := A[i]
                    oldAi = new ArrayList<>(Ai);

                    // A[i] := eval(g)
                    ArrayList<String> evalf = eval(f.f, A, M, MuCalculusFormula.MuCalculusFormulaType.NU);
                    Ai.clear();
                    Ai.addAll(evalf);
                } while (!Ai.containsAll(oldAi));

                return Ai;
        }
        return null;
    }
}


