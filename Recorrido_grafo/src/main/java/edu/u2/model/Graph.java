package edu.u2.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int nVertices;
    private boolean directed;
    private List<List<Integer>> adj;

    public Graph(int nVertices, boolean directed) {
        this.nVertices = nVertices;
        this.directed = directed;
        this.adj = new ArrayList<>();
        // Bucle for tradicional
        for (int i = 0; i < nVertices; i++) {
            adj.add(new ArrayList<Integer>());
        }
    }

    public void addEdge(int u, int v) {
        if (u >= 0 && u < nVertices && v >= 0 && v < nVertices) {
            if (!adj.get(u).contains(v)) {
                adj.get(u).add(v);
            }

            if (!directed) {
                if (!adj.get(v).contains(u)) {
                    adj.get(v).add(u);
                }
            }
        }
    }

    public int getnVertices() {
        return nVertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<List<Integer>> getAdj() {
        return adj;
    }
}