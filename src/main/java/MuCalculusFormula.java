
/**
 * Recursive datatype holding a mu-calculus formula, using the grammar as described in the assignment description.
 * @inv {@code (this.type == FALSE || this.type == TRUE || this.type == RECURSION_VARIABLE) <==> (f == null)}
 * @inv {@code (this.type == CONJUNCTION || this.type == DISJUNCTION) <==> (g != null)}
 * @inv {@code (this.type == FALSE || this.type == TRUE || this.type == CONJUNCTION || this.type == DISJUNCTION)
 * <==> (variableName == null)}
 */
public class MuCalculusFormula {
    /**
     * Construct a new MuCalculusFormula and its subformulas recursively from a string.
     * @param encodedFormula a mu-calculus formula using the grammar as described in the assignment description, encoded
     *                       into a string.
     * @throws IllegalArgumentException when {@code encodedFormula} does not encode a valid mu-calculus formula using
     * the grammar as described in the assignment description.
     */
    public MuCalculusFormula(String encodedFormula) {
        if (encodedFormula.equals("false")) {
            this.type = MuCalculusFormulaType.FALSE;
        } else if (encodedFormula.equals("true")) {
            this.type = MuCalculusFormulaType.TRUE;
        } else if (encodedFormula.length() == 1) { // Only 1 character means that this can only be a recursion variable.
            this.type = MuCalculusFormulaType.RECURSION_VARIABLE;
            this.variableName = encodedFormula;
        } else if (encodedFormula.charAt(0) == '(') {
            // This could be either a conjunction OR disjunction. Check which one of the two it is.

            // Go through the string and try to find a "&&" or "||" substring that is not between other brackets.
            int bracketCount = 0;
            int substringLocation = 0;
            for (int i = 1; i < encodedFormula.length(); i++) {
                // Check if we have found any of the substrings at i.
                if (bracketCount == 0 && (encodedFormula.charAt(i) == '&' || encodedFormula.charAt(i) == '|')) {
                    substringLocation = i;
                    break;
                }

                // Check if we have moved into or out of a pair of brackets in the subformula f.
                // Increment or decrement bracketCount accordingly.
                if (encodedFormula.charAt(i) == '(') {
                    bracketCount++;
                } else if (encodedFormula.charAt(i) == ')') {
                    bracketCount--;
                }
            }

            // Check if we have found a legal substring location, and throw an exception otherwise.
            if (substringLocation == 0) {
                throw new IllegalArgumentException("The given mu-calculus formula does not follow the given grammar!");
            }
            // A valid location for "&&" or "||" was found. Check which of the two was found.

            if (encodedFormula.charAt(substringLocation) == '&') {
                this.type = MuCalculusFormulaType.CONJUNCTION;
            } else {
                this.type = MuCalculusFormulaType.DISJUNCTION;
            }

            // Construct the subformulas.
            this.f = new MuCalculusFormula(encodedFormula.substring(1, substringLocation));
            this.g = new MuCalculusFormula(encodedFormula.substring(substringLocation + 2,
                    encodedFormula.length() - 1));
        } else if (encodedFormula.charAt(0) == '<') {
            this.type = MuCalculusFormulaType.DIAMOND;

            // Extract the action name.
            this.variableName = encodedFormula.substring(1, encodedFormula.indexOf('>'));

            // Construct subformula f.
            this.f = new MuCalculusFormula(encodedFormula.substring(encodedFormula.indexOf('>') + 1));
        } else if (encodedFormula.charAt(0) == '[') {
            this.type = MuCalculusFormulaType.BOX;

            // Extract the action name.
            this.variableName = encodedFormula.substring(1, encodedFormula.indexOf(']'));

            // Construct subformula f.
            this.f = new MuCalculusFormula(encodedFormula.substring(encodedFormula.indexOf(']') + 1));
        } else if (encodedFormula.charAt(0) == 'm') {
            this.type = MuCalculusFormulaType.MU;

            // Extract the recursion variable name.
            this.variableName = String.valueOf(encodedFormula.charAt(2));

            // Construct subformula f.
            this.f = new MuCalculusFormula(encodedFormula.substring(3));
        } else if (encodedFormula.charAt(0) == 'n') {
            this.type = MuCalculusFormulaType.NU;

            // Extract the recursion variable name.
            this.variableName = String.valueOf(encodedFormula.charAt(2));

            // Construct subformula f.
            this.f = new MuCalculusFormula(encodedFormula.substring(3));
        } else {
            // None of the given options were hit. This means that the input string does not follow the given grammar.
            // Throw an exception.
            throw new IllegalArgumentException("The given mu-calculus formula does not follow the given grammar!");
        }
    }

    /**
     * Convert the mu-calculus formula back to a string, recursively.
     */
    @Override
    public String toString() {
        switch (this.type) {
            case FALSE:
                return "false";
            case TRUE:
                return "true";
            case RECURSION_VARIABLE:
                return variableName;
            case CONJUNCTION:
                return "(" + f.toString() + "&&" + g.toString() + ")";
            case DISJUNCTION:
                return "(" + f.toString() + "||" + g.toString() + ")";
            case DIAMOND:
                return "<" + variableName + ">" + f.toString();
            case BOX:
                return "[" + variableName + "]" + f.toString();
            case MU:
                return "mu" + variableName + f.toString();
            case NU:
                return "nu" + variableName + f.toString();
            default:
                // The default case should never be hit!
                assert(false);
                return "";
        }
    }

    /**
     * Enum denoting the types of mu-calculus formulas that are supported.
     */
    public enum MuCalculusFormulaType {
        FALSE,
        TRUE,
        RECURSION_VARIABLE,
        CONJUNCTION,
        DISJUNCTION,
        DIAMOND,
        BOX,
        MU,
        NU
    };

    /**
     * The type of this mu-calculus formula.
     */
    public MuCalculusFormulaType type;

    /**
     * The subformula f of this formula.
     * Is null only if {@code this.type == FALSE || this.type == TRUE || this.type == RECURSION_VARIABLE}
     */
    public MuCalculusFormula f = null;

    /**
     * The subformula f of this formula.
     * Is not null only if {@code this.type == CONJUNCTION || this.type == DISJUNCTION}
     */
    public MuCalculusFormula g = null;

    /**
     * Holds the recursion variable name if {@code this.type == RECURSION_VARIABLE || this.type == MU ||
     * this.type == NU}, or the action name if {@code this.type == DIAMOND || this.type == BOX}
     */
    String variableName = null;
}
