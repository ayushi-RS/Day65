class Solution {
    public double frogPosition(int n, int[][] edges, int t, int target) {
        HashMap<Integer, ArrayList<Integer>> tree = new HashMap<>();        
        boolean[] visited = new boolean[n];
        
        if (n == 1) {
            if (edges.length == 0) {
                return 1.0;
            }
        }
        
        // Build adjacency matrix of the tree.
        for (int[] e: edges) {
            if (!tree.containsKey(e[0])) 
                tree.put(e[0], new ArrayList<Integer>());
            if (!tree.containsKey(e[1])) 
                tree.put(e[1], new ArrayList<Integer>());
            
            if (e[1] == 1) {
                tree.get(1).add(e[0]);
            }
            else if (e[0] == 1) {
                tree.get(1).add(e[1]);
            }
            else if (e[0] != 1 && e[1] != 1) {
                tree.get(e[0]).add(e[1]);
                tree.get(e[1]).add(e[0]);
            }
        }
        
        double p = 0.0;

        // Depth-First Search
        p = DFSVisit(1, 0, tree, target, 0, t, 1.0, visited);
        if (p == 2.0) {
            return 0;
        }
        return p;
    }
    
    private double DFSVisit(int u, int prev, HashMap<Integer, ArrayList<Integer>> tree, int target, 
                            int level, int t, double p, boolean[] visited)
    {
        // base case: Don't look at nodes beyond level > t
        if (level > t || visited[u-1]) {
            return 2.0;
        }
        
        visited[u-1] = true;
        
        // Found target within time "t"
        if (u == target) {
            if (tree.get(u).isEmpty() || allVisited(u, tree, visited) || level == t)
                return p;
            else {
                return 2.0;
            }
        }
        
        // Leaf node
        if (tree.get(u) == null) {
            return 2.0;
        }
        
        double pChild = 0;
        
        for (int v: tree.get(u)) {
            if (!visited[v-1]) {
                int sz = tree.get(u).contains(prev) ? tree.get(u).size()-1 : tree.get(u).size();
                
                pChild = DFSVisit(v, u, tree, target, level+1, t, p * 1.0/(sz), visited);

                if (pChild > 0 && pChild != 2.0) {
                    return pChild;
                }
            }
        }
        
        return pChild;
    }
    
    private boolean allVisited (int u, HashMap<Integer, ArrayList<Integer>> tree, boolean[] visited) {
        for (int i: tree.get(u)){
            if (!visited[i-1]){
                return false;
            }
        }
        return true;
    }
}