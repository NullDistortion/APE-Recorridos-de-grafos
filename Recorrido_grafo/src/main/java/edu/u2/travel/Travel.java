package edu.u2.travel;

import edu.u2.model.Graph;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Travel {

    public static void bfs(Graph g, int startNode) {
        int n = g.getnVertices();
        if (startNode < 0 || startNode >= n) return;

        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        int[] parent = new int[n];

        // Inicialización manual si prefieres evitar Arrays.fill (opcional, pero Arrays.fill es estándar)
        for(int i = 0; i < n; i++) {
            dist[i] = -1;
            parent[i] = -1;
        }

        Queue<Integer> q = new ArrayDeque<>();
        visited[startNode] = true;
        dist[startNode] = 0;
        q.add(startNode);

        System.out.println("\n>>> REPORTE BFS");
        // String.format es tradicional (Java 5), pero si prefieres concatenación avísame.
        System.out.println(String.format("%-10s %-10s %-10s", "Vertice", "Nivel", "Padre"));

        while (!q.isEmpty()) {
            int u = q.poll();

            // Reemplazo de operador ternario por if-else
            String padreStr;
            if (parent[u] == -1) {
                padreStr = "-";
            } else {
                padreStr = String.valueOf(parent[u] + 1);
            }

            System.out.println(String.format("%-10d %-10d %-10s", (u + 1), dist[u], padreStr));

            List<Integer> neighbors = g.getAdj().get(u);
            for (int i = 0; i < neighbors.size(); i++) {
                int v = neighbors.get(i);
                if (!visited[v]) {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    parent[v] = u;
                    q.add(v);
                }
            }
        }
    }

    public static void dfs(Graph g, int startNode) {
        int n = g.getnVertices();
        if (startNode < 0 || startNode >= n) return;

        boolean[] visited = new boolean[n];
        System.out.println("\n>>> REPORTE DFS");
        System.out.print("Orden: ");
        dfsRecursive(g, startNode, visited);
        System.out.println("FIN");
    }

    private static void dfsRecursive(Graph g, int u, boolean[] visited) {
        visited[u] = true;
        System.out.print((u + 1) + " -> ");

        List<Integer> neighbors = g.getAdj().get(u);
        for (int i = 0; i < neighbors.size(); i++) {
            int v = neighbors.get(i);
            if (!visited[v]) {
                dfsRecursive(g, v, visited);
            }
        }
    }
}