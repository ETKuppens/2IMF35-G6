import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MuCalculusInputCleanerTest {
    @Test
    void testCleanInput() {
        MuCalculusFormula f = new MuCalculusFormula(MuCalculusInputCleaner.cleanInput("nu X. (X &&  mu Y. Y)"));
        assertEquals("nuX(X&&muYY)", f.toString());
    }
}
