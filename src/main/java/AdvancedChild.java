import java.util.ArrayList;

public class AdvancedChild extends Child{
    private ArrayList<String> ContainedVars;

    public ArrayList<String> getContainedVars() {
        return ContainedVars;
    }

    public void setContainedVars(ArrayList<String> containedVars) {
        ContainedVars = containedVars;
    }

    public AdvancedChild(MuCalculusFormula latestFixpoint, int depth, ArrayList<String> containedVars) {
        this.setLatestFixpoint(latestFixpoint);
        this.setDepth(depth);
        this.setContainedVars(containedVars);
    }
}
