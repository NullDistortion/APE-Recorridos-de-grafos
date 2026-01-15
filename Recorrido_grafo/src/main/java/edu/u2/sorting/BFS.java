package edu.u2.sorting;

import java.util.*;

public class BFS {

    private int[][] graph;
    private int n;
    private boolean directed;

    public BFS(int[][] graph) {
        this.graph = graph;
        this.n = graph.length;
        this.directed = isDirected(graph);
    }

    // Verifica si el grafo es dirigido
    private boolean isDirected(int[][] g) {
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (g[i][j] != g[j][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> bfs(int s) {
        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> order = new ArrayList<>();

        visited[s] = true;
        dist[s] = 0;
        queue.add(s);

        System.out.println(directed
                ? "Grafo DIRIGIDO"
                : "Grafo NO DIRIGIDO");

        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);

            for (int v = 0; v < n; v++) {
                if (graph[u][v] == 1 && !visited[v]) {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        System.out.println("Orden BFS desde " + s + ": " + order);
        System.out.println("Distancias: " + Arrays.toString(dist));
        System.out.println("Padres: " + Arrays.toString(parent));

        return order;
    }
}