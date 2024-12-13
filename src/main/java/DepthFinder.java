import java.util.ArrayList;

public class DepthFinder {
    static Integer findNestingDepth(MuCalculusFormula formula) {
        switch (formula.type) {
            case BOX:
            case DIAMOND:
                return findNestingDepth(formula.f);
            case CONJUNCTION:
            case DISJUNCTION:
                return Math.max(findNestingDepth(formula.f), findNestingDepth(formula.g));
            case MU:
            case NU:
                return 1 + findNestingDepth(formula.f);
        }
        return 0;
    }

    static ArrayList<String> findVariables(MuCalculusFormula formula, ArrayList<String> vars) {
        if (formula.f != null) {
            vars = findVariables(formula.f, vars);
        }
        if (formula.g != null) {
            vars = findVariables(formula.g, vars);
        }

        if (formula.type == MuCalculusFormula.MuCalculusFormulaType.RECURSION_VARIABLE && !vars.contains(formula.variableName)) {
            vars.add(formula.variableName);
        }

        return vars;
    }

    static Integer findAlternationDepth(MuCalculusFormula formula) {
        return recurseAlternation(formula).getDepth();
    }
    
    static Child recurseAlternation(MuCalculusFormula formula) {
        switch (formula.type) {
            case BOX:
            case DIAMOND:
                return recurseAlternation(formula.f);
            case CONJUNCTION:
            case DISJUNCTION:
                Child eval1 = recurseAlternation(formula.f);
                Child eval2 = recurseAlternation(formula.g);
                Child dominantChild;

                if (eval1.getDepth() >= eval2.getDepth()) {
                    dominantChild = eval1;
                } else {
                    dominantChild = eval2;
                }

                return dominantChild;
            case MU:
                Child lowerChild = recurseAlternation(formula.f);
                if (lowerChild.getLatestFixpoint() != null
                        && lowerChild.getLatestFixpoint().type == MuCalculusFormula.MuCalculusFormulaType.NU) {
                    return new Child(formula, Math.max(1, 1 + Math.max(recurseAlternation(formula.f).getDepth(), lowerChild.getDepth())));
                } else {
                    return new Child(formula, Math.max(1, lowerChild.getDepth()));
                }
            case NU:
                lowerChild = recurseAlternation(formula.f);
                if (lowerChild.getLatestFixpoint() != null
                        && lowerChild.getLatestFixpoint().type == MuCalculusFormula.MuCalculusFormulaType.MU) {
                    return new Child(formula, Math.max(1, 1 + Math.max(recurseAlternation(formula.f).getDepth(), lowerChild.getDepth())));
                } else {
                    return new Child(formula, Math.max(1, lowerChild.getDepth()));
                }
        }
        return new Child(null, 0);
    }

    static Integer findDAD(MuCalculusFormula formula) {
        return recursiveDAD(formula).getDepth();
    }

    static AdvancedChild recursiveDAD(MuCalculusFormula formula) {
        switch (formula.type) {
            case BOX:
            case DIAMOND:
                return recursiveDAD(formula.f);
            case CONJUNCTION:
            case DISJUNCTION:
                AdvancedChild eval1 = recursiveDAD(formula.f);
                AdvancedChild eval2 = recursiveDAD(formula.g);
                AdvancedChild dominantChild;

                if (eval1.getDepth() >= eval2.getDepth()) {
                    dominantChild = eval1;
                } else {
                    dominantChild = eval2;
                }

                return dominantChild;
            case MU:
                AdvancedChild lowerChild = recursiveDAD(formula.f);
                ArrayList<String> containedVars = findVariables(formula, new ArrayList<>());
                if (lowerChild.getLatestFixpoint() != null
                        && lowerChild.getLatestFixpoint().type == MuCalculusFormula.MuCalculusFormulaType.NU
                        && lowerChild.getContainedVars().contains(formula.variableName)) {
                    return new AdvancedChild(formula, Math.max(1, 1 + Math.max(recursiveDAD(formula.f).getDepth(), lowerChild.getDepth())), containedVars);
                } else {
                    return new AdvancedChild(formula, Math.max(1, lowerChild.getDepth()), containedVars);
                }
            case NU:
                lowerChild = recursiveDAD(formula.f);
                containedVars = findVariables(formula, new ArrayList<>());
                if (lowerChild.getLatestFixpoint() != null
                        && lowerChild.getLatestFixpoint().type == MuCalculusFormula.MuCalculusFormulaType.MU
                        && lowerChild.getContainedVars().contains(formula.variableName)) {
                    return new AdvancedChild(formula, Math.max(1, 1 + Math.max(recursiveDAD(formula.f).getDepth(), lowerChild.getDepth())), containedVars);
                } else {
                    return new AdvancedChild(formula, Math.max(1, lowerChild.getDepth()), containedVars);
                }
        }
        return new AdvancedChild(null, 0, new ArrayList<>());
    }
}
