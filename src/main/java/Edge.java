public class Edge {
    public String StartNode;
    public String Label;
    public String EndNode;

    public Edge(String startState, String label, String endNode) {
        StartNode = startState;
        Label = label;
        EndNode = endNode;
    }

    @Override
    public String toString() {
        return "(" + StartNode + ", \"" + Label + "\", " + EndNode + ")";
    }
}
