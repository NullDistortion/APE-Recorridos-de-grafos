package edu.u2.travel;

import edu.u2.model.Graph;
import java.util.List;

public class GraphUses {

    public static void findConnectedComponents(Graph g) {
        int n = g.getnVertices();
        boolean[] visited = new boolean[n];
        int count = 0;

        System.out.println("\n>>> COMPONENTES CONEXAS");
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                System.out.print("Grupo " + count + ": { ");
                dfsCollect(g, i, visited);
                System.out.println("}");
            }
        }
    }

    private static void dfsCollect(Graph g, int u, boolean[] visited) {
        visited[u] = true;
        System.out.print((u + 1) + " ");

        // Reemplazo de for-each por for indexado
        List<Integer> neighbors = g.getAdj().get(u);
        for (int i = 0; i < neighbors.size(); i++) {
            int v = neighbors.get(i);
            if (!visited[v]) {
                dfsCollect(g, v, visited);
            }
        }
    }

    public static boolean hasCycle(Graph g) {
        int n = g.getnVertices();
        boolean[] visited = new boolean[n];
        boolean[] recStack = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (g.isDirected()) {
                    if (detectCycleDirected(g, i, visited, recStack)) return true;
                } else {
                    if (detectCycleUndirected(g, i, visited, -1)) return true;
                }
            }
        }
        return false;
    }

    private static boolean detectCycleUndirected(Graph g, int u, boolean[] visited, int parent) {
        visited[u] = true;

        List<Integer> neighbors = g.getAdj().get(u);
        for (int i = 0; i < neighbors.size(); i++) {
            int v = neighbors.get(i);

            if (!visited[v]) {
                if (detectCycleUndirected(g, v, visited, u)) return true;
            } else {
                if (v != parent) return true;
            }
        }
        return false;
    }

    private static boolean detectCycleDirected(Graph g, int u, boolean[] visited, boolean[] recStack) {
        visited[u] = true;
        recStack[u] = true;

        List<Integer> neighbors = g.getAdj().get(u);
        for (int i = 0; i < neighbors.size(); i++) {
            int v = neighbors.get(i);

            if (!visited[v]) {
                if (detectCycleDirected(g, v, visited, recStack)) return true;
            } else {
                if (recStack[v]) return true;
            }
        }

        recStack[u] = false;
        return false;
    }
}