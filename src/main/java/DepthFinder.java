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
                    return new Child(formula, 1);
                }
            case NU:
                lowerChild = recurseAlternation(formula.f);
                if (lowerChild.getLatestFixpoint() != null
                        && lowerChild.getLatestFixpoint().type == MuCalculusFormula.MuCalculusFormulaType.MU) {
                    return new Child(formula, Math.max(1, 1 + Math.max(recurseAlternation(formula.f).getDepth(), lowerChild.getDepth())));
                } else {
                    return new Child(formula, 1);
                }
        }
        return new Child(null, 0);
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
