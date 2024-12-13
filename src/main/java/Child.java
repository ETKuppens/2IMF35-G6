public class Child {
    private MuCalculusFormula LatestFixpoint;
    private int Depth;

    public MuCalculusFormula getLatestFixpoint() {
        return LatestFixpoint;
    }

    public void setLatestFixpoint(MuCalculusFormula latestFixpoint) {
        LatestFixpoint = latestFixpoint;
    }

    public int getDepth() {
        return Depth;
    }

    public void setDepth(int depth) {
        Depth = depth;
    }

    public Child() {
    }

    public Child(MuCalculusFormula latestFixpoint, int depth) {
        LatestFixpoint = latestFixpoint;
        Depth = depth;
    }
}
