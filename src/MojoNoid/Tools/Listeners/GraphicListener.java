package MojoNoid.Tools.Listeners;

import MojoNoid.Agents.GraphicalAgent;

public abstract class GraphicListener {
    private static GraphicalAgent graphicalAgent;

    public static GraphicalAgent getGraphicalAgent() {
        return graphicalAgent;
    }
    public static void setGraphicalAgent(GraphicalAgent graphicalAgent) {
        GraphicListener.graphicalAgent = graphicalAgent;
    }
}
