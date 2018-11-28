/*************************************************************************
 *  Identifies articulation points and bridge edges.
 *
 *  Articulation points can be used to decompose a graph into biconnected components.
 *
 *  A brigde decomposes a directed graph into two-edge connected components.
 *
 *  Key quantity:  low[v] = minimum DFS preorder number of v
 *  and the set of vertices w for which there is a back edge (x, w)
 *  with x a descendant of v and w an ancestor of v.
 *
 *  Note: code assumes no parallel edges, e.g., two parallel edges
 *  would be (incorrectly) identified as bridges.
 *
 *  Runs in O(E + V) time.
 *  
 *  
 *  HINT:
 *   - deg[x] < 2 || (deg[x] & 1) != 0 means is not biconnected
 *
 *************************************************************************/
package com.alex.graph;

import java.util.*;

public class Biconnected {
    private int[] low;
    private int[] pre;
    private int cnt;
    private int bridges;
    private HashSet<Integer> articulation;

    public Biconnected(ArrayList<Integer> [] graph) {
    	int N = graph.length;
        low = new int[N];
        pre = new int[N];
        articulation = new HashSet<Integer>();
        Arrays.fill(low, -1);
        Arrays.fill(pre, -1);
        cnt = bridges = 0;
        
        for (int v = 0; v < N; v++)
            if (pre[v] == -1)
                dfs(graph, v, v);
    }

    private void dfs(ArrayList<Integer> [] graph, int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : graph[v] ) {
            if (pre[w] == -1) {
                children++;
                dfs(graph, v, w);

                // update low number
                low[v] = Math.min(low[v], low[w]);

                // non-root of DFS is an articulation point if low[w] >= pre[v]
                if (low[w] >= pre[v] && u != v) 
                    articulation.add(v);

		// check v -> w is a brigde
                if (low[w] == pre[w]) {
                  //  StdOut.println(v + "-" + w + " is a bridge");
                    bridges++;
                }
            }

            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }

        // root of DFS is an articulation point if it has more than 1 child
        if (u == v && children > 1)
            articulation.add(v);
    }

}


