class Solution:
  def buildMatrix(self, k: int, rowConditions: List[List[int]], colConditions: List[List[int]]) -> List[List[int]]:
    rowOrder = self._topologicalSort(rowConditions, k)
    if not rowOrder:
      return []

    colOrder = self._topologicalSort(colConditions, k)
    if not colOrder:
      return []

    ans = [[0] * k for _ in range(k)]
    nodeToRowIndex = [0] * (k + 1)

    for i, node in enumerate(rowOrder):
      nodeToRowIndex[node] = i

    for j, node in enumerate(colOrder):
      i = nodeToRowIndex[node]
      ans[i][j] = node

    return ans

  def _topologicalSort(self, conditions: List[List[int]], n: int) -> List[int]:
    order = []
    graph = [[] for _ in range(n + 1)]
    inDegree = [0] * (n + 1)

    # Build graph
    for u, v in conditions:
      graph[u].append(v)
      inDegree[v] += 1

    # Topology
    q = deque([i for i in range(1, n + 1) if inDegree[i] == 0])

    while q:
      u = q.popleft()
      order.append(u)
      for v in graph[u]:
        inDegree[v] -= 1
        if inDegree[v] == 0:
          q.append(v)

    return order if len(order) == n else []
