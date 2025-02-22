class Solution:
  def lowestCommonAncestor(self, root: Optional[TreeNode], p: Optional[TreeNode], q: Optional[TreeNode]) -> Optional[TreeNode]:
    q_ = deque([root])
    parent = {root: None}
    ancestors = set()  # p's ancestors

    # Iterate until we find both p and q
    while p not in parent or q not in parent:
      root = q_.popleft()
      if root.left:
        parent[root.left] = root
        q_.append(root.left)
      if root.right:
        parent[root.right] = root
        q_.append(root.right)

    # Insert all of p ancestors
    while p:
      ancestors.add(p)
      p = parent[p]  # P becomes None in the end

    # Go up from q until meet any of p's ancestors
    while q not in ancestors:
      q = parent[q]

    return q
