class UnionFind {
  public UnionFind(int n) {
    id = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; ++i)
      id[i] = i;
  }

  public void union(int u, int v) {
    int i = find(u);
    int j = find(v);
    if (i == j)
      return;
    if (rank[i] > rank[j]) {
      final int temp = i;
      i = j;
      j = temp;
    } else if (rank[i] == rank[j]) {
      ++rank[j];
    }
    id[i] = j;
  }

  public int find(int u) {
    return id[u] == u ? u : (id[u] = find(id[u]));
  }

  private int[] id;
  private int[] rank;
}

class Solution {
  public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
    List<Integer> criticalEdges = new ArrayList<>();
    List<Integer> pseudoCriticalEdges = new ArrayList<>();

    // Record the index information, so edges[i] := (u, v, weight, index)
    for (int i = 0; i < edges.length; ++i)
      edges[i] = new int[] {edges[i][0], edges[i][1], edges[i][2], i};

    // Sort by weight
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);

    final int mstWeight = getMSTWeight(n, edges, new int[] {}, -1);

    for (int[] e : edges) {
      final int index = e[3];
      // Deleting `e` makes the MST weight increase or can't form a MST
      if (getMSTWeight(n, edges, new int[] {}, index) > mstWeight)
        criticalEdges.add(index);
      // If an edge can be in any MST, we can always add `e` to the edge set
      else if (getMSTWeight(n, edges, e, -1) == mstWeight)
        pseudoCriticalEdges.add(index);
    }

    return new ArrayList<>(Arrays.asList(criticalEdges, pseudoCriticalEdges));
  }

  private int getMSTWeight(int n, int[][] edges, int[] firstEdge, int deletedEdgeIndex) {
    int mstWeight = 0;
    UnionFind uf = new UnionFind(n);

    if (firstEdge.length == 4) {
      uf.union(firstEdge[0], firstEdge[1]);
      mstWeight += firstEdge[2];
    }

    for (int[] e : edges) {
      final int u = e[0];
      final int v = e[1];
      final int weight = e[2];
      final int index = e[3];
      if (index == deletedEdgeIndex)
        continue;
      if (uf.find(u) == uf.find(v))
        continue;
      uf.union(u, v);
      mstWeight += weight;
    }

    final int root = uf.find(0);
    for (int i = 0; i < n; ++i)
      if (uf.find(i) != root)
        return Integer.MAX_VALUE;

    return mstWeight;
  }
}
