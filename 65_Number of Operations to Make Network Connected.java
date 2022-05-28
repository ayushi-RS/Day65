class Solution {
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }

        UnionFindSet uf = new UnionFindSet(n);
        for (int[] conn : connections) {
            uf.union(conn[0], conn[1]);
        }

        return uf.setCount - 1;
    }
}

class UnionFindSet {
      private int[] parents;
      int setCount;
        
         public UnionFindSet(int n) {
          parents = new int[n + 1];
          setCount = n;
          for (int i = 0; i < parents.length; ++i) {
              parents[i] = i;
          }
      }

      public void union(int u, int v) {
          int pu = find(u);
          int pv = find(v);
          parents[pv] = pu;
          if(pv == pu) return; // if the their parents are the same, 2 nodes have already connected together. So the number of connected component will not decrement.
          setCount--;
      }

      public int find(int u) {
          while (parents[u] != u) {
              parents[u] = parents[parents[u]];  //path compress
              u = parents[u];
          }
          return u;
      }
}