

public class DepthFinder {
    static Integer findNestingDepth(MuCalculusFormula formula, Integer depth) {
        Integer depth1 = 0, depth2 = 0;
        if (formula.f != null) {
            depth1 = findNestingDepth(formula.f, depth);
        }
        if (formula.g != null) {
            depth2 = findNestingDepth(formula.g, depth);
        }

        depth = Math.max(depth1, depth2);

        if (formula.type == MuCalculusFormula.MuCalculusFormulaType.NU
                || formula.type == MuCalculusFormula.MuCalculusFormulaType.MU) {
            depth++;
        }

        return depth;
    }
    
    static Integer findAlternationDepth(MuCalculusFormula formula, Integer depth, MuCalculusFormula.MuCalculusFormulaType binder) {
        MuCalculusFormula.MuCalculusFormulaType newBinder = binder;
        if (formula.type == MuCalculusFormula.MuCalculusFormulaType.NU) {
            newBinder = MuCalculusFormula.MuCalculusFormulaType.NU;
        } else if (formula.type == MuCalculusFormula.MuCalculusFormulaType.MU) {
            newBinder = MuCalculusFormula.MuCalculusFormulaType.MU;
        }
        Integer depth1 = 0, depth2 = 0;
        if (formula.f != null) {
            depth1 = findAlternationDepth(formula.f, depth, newBinder);
        }
        if (formula.g != null) {
            depth2 = findAlternationDepth(formula.g, depth, newBinder);
        }

        if (binder != null) {
            depth = Math.max(1, Math.max(depth1, depth2));
        } else {
            depth = Math.max(depth1, depth2);
        }

        if ((formula.type == MuCalculusFormula.MuCalculusFormulaType.NU
                || formula.type == MuCalculusFormula.MuCalculusFormulaType.MU)
                && binder != formula.type && binder != null) {
            depth++;
        }

        return depth;
    }
}
