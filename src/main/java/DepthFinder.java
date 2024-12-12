import java.util.ArrayList;

public class DepthFinder {
    static ArrayList<MuCalculusFormula> findNestedFixPoints(MuCalculusFormula formula, ArrayList<MuCalculusFormula> formulas) {
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

    static Integer findAlternationDepth(MuCalculusFormula formula) {
        return recurseAlternation(formula, null) + 1;
    }
    
    static Integer recurseAlternation(MuCalculusFormula formula, MuCalculusFormula.MuCalculusFormulaType binder) {
        switch (formula.type) {
            case BOX:
            case DIAMOND:
                return recurseAlternation(formula.f, binder);
            case CONJUNCTION:
            case DISJUNCTION:
                return Math.max(recurseAlternation(formula.f, binder), recurseAlternation(formula.g, binder));
            case MU:
                if (binder == MuCalculusFormula.MuCalculusFormulaType.NU) {
                    return Math.max(1, 1 + recurseAlternation(formula.f, MuCalculusFormula.MuCalculusFormulaType.MU));
                } else {
                    return Math.max(1, recurseAlternation(formula.f, MuCalculusFormula.MuCalculusFormulaType.MU));
                }
            case NU:
                if (binder == MuCalculusFormula.MuCalculusFormulaType.MU) {
                    return Math.max(1, 1 + recurseAlternation(formula.f, MuCalculusFormula.MuCalculusFormulaType.NU));
                } else {
                    return Math.max(1, recurseAlternation(formula.f, MuCalculusFormula.MuCalculusFormulaType.NU));
                }
        }
        return 0;
    }

    static Integer findDAD(MuCalculusFormula formula) {
        int depth = 0;
        switch (formula.type) {
            case BOX:
            case DIAMOND:
                depth = findDAD(formula.f);
            case CONJUNCTION:
            case DISJUNCTION:
                depth = Math.max(findDAD(formula.f), findDAD(formula.g));
            case MU:
                depth = Math.max(1, findDAD(formula.f));
            case NU:
                depth = Math.max(1, findDAD(formula.f));

        }

        return depth;
    }
}
