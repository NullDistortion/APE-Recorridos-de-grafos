package edu.u2.utils;

import edu.u2.model.Graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {

    public static List<String> listTxtFiles() {
        List<String> textFiles = new ArrayList<>();

        File[] possibleFolders = {
                new File("src/main/resources"),
                new File("src/resources"),
                new File("resources")
        };

        File targetFolder = null;
        for (int i = 0; i < possibleFolders.length; i++) {
            File f = possibleFolders[i];
            if (f.exists() && f.isDirectory()) {
                targetFolder = f;
                break;
            }
        }

        if (targetFolder != null) {
            // Reemplazo de Lambda por listado simple y filtrado manual
            File[] files = targetFolder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.getName().toLowerCase().endsWith(".txt")) {
                        textFiles.add(file.getName());
                    }
                }
            }
        } else {
            System.err.println("No se encontró la carpeta 'resources'");
        }

        return textFiles;
    }

    public static Graph loadGraphAuto(String fileName) {
        List<String[]> matrixRows = new ArrayList<>();

        try {
            InputStream is = GraphLoader.class.getClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new RuntimeException("Archivo no encontrado en la carpeta: " + fileName);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    matrixRows.add(line.trim().split("\\s+"));
                }
            }
           br.close();

        } catch (Exception e) {
            System.err.println("Error leyendo: " + e.getMessage());
            return null;
        }

        int n = matrixRows.size();
        boolean isSymmetric = true;
        int[][] tempMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] row = matrixRows.get(i);

            int limit = Math.min(row.length, n);

            for (int j = 0; j < limit; j++) {
                if (row[j].equals("1")) {
                    tempMatrix[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tempMatrix[i][j] != tempMatrix[j][i]) {
                    isSymmetric = false;
                    break;
                }
            }
            if (!isSymmetric) break;
        }

        boolean isDirected = !isSymmetric;

        String symStr = "Asimétrica";
        if(isSymmetric) symStr = "Simétrica";

        String dirStr = "NO DIRIGIDO";
        if(isDirected) dirStr = "DIRIGIDO";

        System.out.println(">> Análisis del Grafo: Matriz " + symStr);
        System.out.println(">> Modo detectado: " + dirStr);

        Graph graph = new Graph(n, isDirected);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tempMatrix[i][j] == 1) {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }
}