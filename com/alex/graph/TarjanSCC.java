/**
 * A class for computing strongly connected componets using Tarjan's algorithm
 * 
 * O(E + V)
 * 
 * E -> number of edges
 * V -> number of vertices
 * 
 * @author Alexis Hernández
 * 
**/
package com.alex.graph;

import java.util.ArrayList;
import java.util.Stack;

public class TarjanSCC {

    int[] id;    	   		        // id[v] = id of strong component containing v
    int count;         			    // number of strongly-connected components
    private boolean[] marked;       // marked[v] = has v been visited?
    private int[] low;              // low[v] = low number of v
    private int pre;                // preorder number counter
    private Stack<Integer> stack;
    
	public TarjanSCC(ArrayList<Integer> g [])	{
		int N = g.length;
		
		marked = new boolean[N];
		stack = new Stack<Integer>();
		id = new int [N];
		low = new int [N];
		
		for (int v = 0; v < N; v++)	if	( !marked[v] )
			dfs(g, v);
	}

	private void dfs(ArrayList<Integer>[] g, int v) {
		marked[v] = true;
		low[v] = pre++;
		int min = low[v];
		stack.push(v);
		for (int w : g[v])	{
			if	( !marked[w] )	dfs(g, w);
			min = Math.min(min, low[w]);
		}
		if	( min < low[v] )	{
			low[v] = min;
			return;
		}
		
		int w;
		do	{
			w = stack.pop();
			id[w] = count;
			low[w] = g.length;
		}	while	( w != v );
		count++;

	}
	// are v and w strongly connected?
	public boolean stronglyConnected(int v, int w)	{
		return	id[v] == id[w];
	}

}
