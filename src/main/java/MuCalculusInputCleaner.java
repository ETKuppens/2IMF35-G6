
/**
 * Class providing a static function for cleaning
 * input such that it can be read by the constructor
 * of MuCalculusFormula.
 */
public class MuCalculusInputCleaner {

    /**
     * Clean an input mu-calculus formula by removing
     * any spaces and/or dots.
     * @param input A mu-calculus formula in .mcf format
     * @return The input string, with all whitespace and dots removed
     */
    static String cleanInput(String input) {
        String strCpy = input;
        strCpy = strCpy.replaceAll("[\\s.]", "");
        return strCpy;
    }
}
