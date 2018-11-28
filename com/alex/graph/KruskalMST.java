/**
 * A class for computing the minumun spanning tree using kruskal algorithm
 * 
 * @author Alexis Hernández
**/
package com.alex.graph;

import java.util.*;

//
public class KruskalMST	{
	// iterable minimun spanning tree
	Queue<Node> mst;
	// N = Number of nodes in the graph
	// receive a Queue with all the paths and generate de mst
	public KruskalMST(int N, PriorityQueue<Node> paths)	{
		// create a union find structure for detecting connected components
		UnionFind(N);
		// generate MST
		mst = new LinkedList<Node>();
		while	( mst.size() < N - 1 )	{
			Node node = paths.poll();
			//is (from) is connected to (to) then, adding this node will create a cycle, avoid it
			if	( connected( node.from, node.to ) )	continue;
			//add the connection (from) -> (to) to the mst
			union(node.from, node.to);
			mst.add(node);
		}
	}
	//Kruskal node, edge [from -> to] = cost
	class Node	implements Comparable<Node>	{
		int from, cost, to;
		Node(int from, int to, int cost)	{
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(Node that)	{
			return	cost - that.cost;
		}
	}
	// Union Find used for detecting cycles
	int[] id;    // id[i] = parent of i
	int[] sz;    // sz[i] = number of objects in subtree rooted at i
	// Create an empty union find data structure with N isolated sets.
	void UnionFind(int N)	{
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

    // Return component identifier for component containing p
    int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }
	// Are objects p and q in the same set?
    boolean connected(int p, int q) {
        return find(p) == find(q);
    }
	// Replace sets containing p and q with their union.
    void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        // make smaller root point to larger one
        if   (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else                 { id[j] = i; sz[i] += sz[j]; }
    }
	// End Union Find
}

