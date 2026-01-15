import java.util.*;

public class Grafo_Orozco {
    private int nVertices;
    private boolean directed;
    private List<List<Integer>> adj;

    public Grafo_Orozco(int nVertices, boolean directed) {
        this.nVertices = nVertices;
        this.directed = directed;
        this.adj = new ArrayList<>();

        // CAMBIO: Iteramos hasta <= nVertices para tener índices del 0 al 10
        // El índice 0 quedará vacío y no se usará.
        for (int i = 0; i <= nVertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        if (!directed) {
            adj.get(v).add(u);
        }
    }

    public List<Integer> dfs(int s) {
        List<Integer> traversalOrder = new ArrayList<>();
        // Array de visitados de tamaño N + 1
        boolean[] visited = new boolean[nVertices + 1];

        dfsRecursive(s, visited, traversalOrder);
        return traversalOrder;
    }

    private void dfsRecursive(int u, boolean[] visited, List<Integer> traversalOrder) {
        visited[u] = true;
        traversalOrder.add(u);

        // Ordenamos para que la salida sea predecible (ej: 1, 2, 3...)
        Collections.sort(adj.get(u));

        for (int v : adj.get(u)) {
            if (!visited[v]) {
                dfsRecursive(v, visited, traversalOrder);
            }
        }
    }
}