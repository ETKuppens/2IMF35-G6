import java.util.ArrayList;

public class LabelledTransitionSystem {
    private final ArrayList<Edge> Edges = new ArrayList<>();
    private final ArrayList<String> States = new ArrayList<>();
    private final ArrayList<String> Actions = new ArrayList<>();
    private Integer StartNode;
    private Integer EdgeCount;
    private Integer StateCount;

    public ArrayList<Edge> getEdges() {
        return Edges;
    }

    public ArrayList<String> getStates() {
        return States;
    }

    public Integer getStartNode() {
        return StartNode;
    }

    public void setStartNode(Integer startNode) {
        StartNode = startNode;
    }

    public Integer getEdgeCount() {
        return EdgeCount;
    }

    public void setEdgeCount(Integer edgeCount) {
        EdgeCount = edgeCount;
    }

    public Integer getStateCount() {
        return StateCount;
    }

    public void setStateCount(Integer stateCount) {
        StateCount = stateCount;
    }

    public LabelledTransitionSystem(ArrayList<String> LTS) {
        for (String lts:LTS)
        {
            if (lts.startsWith("des")) {
                lts = lts.substring(lts.indexOf('(') + 1, lts.lastIndexOf(')'));
                String[] values = lts.split(",");
                StartNode = Integer.parseInt(values[0]);
                EdgeCount = Integer.parseInt(values[1]);
                StateCount = Integer.parseInt(values[2]);
            } else {
                lts = lts.substring(1);
                lts = lts.substring(0, lts.length() - 1);
                String startState = lts.substring(0, lts.indexOf(','));
                String endState = lts.substring(lts.lastIndexOf(',') + 1);
                String label = lts.substring(lts.indexOf('"') + 1, lts.lastIndexOf('"'));

                addStates(startState, endState, label);

                Edges.add(new Edge(startState, label, endState));
            }
        }
    }

    private void addStates(String startState, String endState, String label) {
        if (!States.contains(startState)) {
            States.add(startState);
        }
        if (!States.contains(endState)) {
            States.add(endState);
        }
        if (!Actions.contains(label)) {
            Actions.add(label);
        }
    }

    public void print() {
        System.out.println("Starting node = " + StartNode);
        System.out.println("Edge count = " + EdgeCount);
        System.out.println("Vertex count = " + StateCount);
        for (Edge edge:Edges) {
            System.out.println(edge.toString());
        }
        for (String state:States) {
            System.out.println(state);
        }
        for (String action:Actions) {
            System.out.println(action);
        }
    }
}
