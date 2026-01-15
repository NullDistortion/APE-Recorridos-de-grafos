package edu.u2.sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    int nVertices;
    boolean directed;
    List<List<Integer>> adj;

    // Constructor
    public Graph(int nVertices, boolean directed) {
        this.nVertices = nVertices;
        this.directed = directed;
        this.adj = new ArrayList<>();
        for (int i = 0; i < nVertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // Método addEdge
    public void addEdge(int u, int v) {
        // 1. Siempre agregamos de u -> v
        if (!adj.get(u).contains(v)) {
            adj.get(u).add(v);
        }

        if (!directed) {
            if (!adj.get(v).contains(u)) {
                adj.get(v).add(u);
            }
        }
    }

    public void printGraph() {
        String tipo = directed ? "Dirigido" : "No Dirigido";
        System.out.println("--- Grafo " + tipo + " (Vértices 1 a " + nVertices + ") ---");

        for (int i = 0; i < nVertices; i++) {
            System.out.print("Vértice " + (i + 1) + ": ");
            if (adj.get(i).isEmpty()) {
                System.out.print("Sin conexiones");
            } else {
                for (Integer neighbor : adj.get(i)) {
                    System.out.print("-> " + (neighbor + 1) + " ");
                }
            }
            System.out.println();
        }
    }

    public static Graph loadFromMatrixFile(String filePath, boolean isDirected) {
        Graph g = null;
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) lines.add(line);
            }
            scanner.close();

            int n = lines.size();
            System.out.println("\nCargando archivo: " + filePath);
            System.out.println("Detectados " + n + " vértices.");

            g = new Graph(n, isDirected);

            for (int i = 0; i < n; i++) {
                String[] parts = lines.get(i).split("\\s+");
                for (int j = 0; j < parts.length; j++) {
                    if (parts[j].equals("1")) {
                        g.addEdge(i, j);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: No se encontró el archivo: " + filePath);
        } catch (Exception e) {
            System.out.println("ERROR grave: " + e.getMessage());
        }
        return g;
    }
}