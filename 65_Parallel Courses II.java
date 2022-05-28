class Solution {
    public int minNumberOfSemesters(int n, int[][] dependencies, int k) {
        int size=(1<<n);
        int[] dp=new int[size];
        
        for(int graph=1;graph<size;graph++)
        {
            Set<Integer> nodes=new HashSet();
            
            for(int j=0;(1<<j)<=graph;j++)
            {
                if((graph&(1<<j))!=0)
                {
                    nodes.add(j);
                }
            }
            
            Set<Integer> opened=new HashSet(nodes);
            
            for(int[] edge:dependencies)
            {
                if(nodes.contains(edge[0]-1) && nodes.contains(edge[1]-1))
                {
                    opened.remove(edge[1]-1);
                }
            }
            
            if(opened.size()<=k)
            {
                int sum=0;
                for(int node:opened)
                {
                    sum=sum|(1<<node);
                }
                dp[graph]=dp[graph^sum]+1;
            } else {
                
                dp[graph]=Integer.MAX_VALUE;
                Set<Integer> sums=new HashSet();
                int mask=0;
                for(int j:opened)
                {
                    mask=mask|(1<<j);
                }
                //generate all submasks
                //https://cp-algorithms.com/algebra/all-submasks.html
                int submask=mask;
                while(submask>0)
                {
                    submask=(submask-1)&mask;
                    if(submask>0)
                    {
                        int count=Integer.bitCount(submask);
                        if(count==k)
                           sums.add(submask);
                    }
                }
				//get the minimum
                for(int sum:sums)
                {
                    dp[graph]=Math.min(dp[graph], dp[graph^sum]+1);
                }
            }
        }
        return dp[size-1];
    }
}