package edu.u2.main;

import edu.u2.utils.GraphLoader;
import edu.u2.model.Graph;
import edu.u2.travel.GraphUses;
import edu.u2.travel.Travel;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Graph currentGraph = null;
    private static String currentFileName = "";

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            List<String> availableFiles = GraphLoader.listTxtFiles();
            System.out.println("\nSeleccione un dataset detectado:");

            if (availableFiles.isEmpty()) {
                System.out.println("No se encontraron archivos .txt");
            } else {
                for (int i = 0; i < availableFiles.size(); i++) {
                    System.out.println((i + 1) + ". " + availableFiles.get(i));
                }
            }
            System.out.println("0. Salir");
            System.out.println("----------------------------------------");

            int option = readInt("Opcion: ");

            if (option == 0) {
                running = false;
            } else {
                if (option > 0 && option <= availableFiles.size()) {
                    currentFileName = availableFiles.get(option - 1);
                    System.out.println("\nCargando: " + currentFileName + "...");

                    currentGraph = GraphLoader.loadGraphAuto(currentFileName);

                    if (currentGraph != null) {
                        runOperationsMenu();
                    }
                } else {
                    System.out.println("Opcion invalida.");
                }
            }
        }
        System.out.println("Fin del programa.");
    }

    private static void runOperationsMenu() {
        boolean back = false;
        while (!back) {
            String type = "";
            if (currentGraph.isDirected()) {
                type = "DIRIGIDO ->";
            } else {
                type = "NO DIRIGIDO -";
            }

            System.out.println("\n----------------------------------------");
            System.out.println(" ARCHIVO: " + currentFileName);
            System.out.println(" TIPO DETECTADO: [" + type + "]");
            System.out.println(" VERTICES: " + currentGraph.getnVertices());
            System.out.println("----------------------------------------");
            System.out.println("1. Recorrido BFS (Anchura + Tabla)");
            System.out.println("2. Recorrido DFS (Profundidad)");
            System.out.println("3. Ver Componentes Conexas");
            System.out.println("4. Analisis de Ciclos");
            System.out.println("5. Imprimir Lista de Adyacencia");
            System.out.println("0. << Regresar / Cargar otro grafo");

            int op = readInt(">> ");

            switch (op) {
                case 1:
                    int startBfs = requestNode("Nodo inicio BFS: ");
                    if (startBfs != -1) {
                        Travel.bfs(currentGraph, startBfs);
                    }
                    break;
                case 2:
                    int startDfs = requestNode("Nodo inicio DFS: ");
                    if (startDfs != -1) {
                        Travel.dfs(currentGraph, startDfs);
                    }
                    break;
                case 3:
                    GraphUses.findConnectedComponents(currentGraph);
                    break;
                case 4:
                    boolean cyclic = GraphUses.hasCycle(currentGraph);
                    if (cyclic) {
                        System.out.println("\nResultado: CONTIENE CICLOS");
                    } else {
                        System.out.println("\nResultado: NO CONTIENE CICLOS");
                    }
                    break;
                case 5:
                    printAdjacency();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    private static void printAdjacency() {
        System.out.println("\n--- Estructura Interna ---");
        for (int i = 0; i < currentGraph.getnVertices(); i++) {
            System.out.print("V" + (i + 1) + " -> [");
            List<Integer> adj = currentGraph.getAdj().get(i);
            for (int j = 0; j < adj.size(); j++) {
                System.out.print((adj.get(j) + 1));
                if (j < adj.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    private static int requestNode(String msg) {
        int val = readInt(msg);
        if (val < 1 || val > currentGraph.getnVertices()) {
            System.out.println("Nodo fuera de rango (1-" + currentGraph.getnVertices() + ")");
            return -1;
        }
        return val - 1;
    }

    private static int readInt(String msg) {
        System.out.print(msg);
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}