/**
 * A class for computing maximum matching in a bipartite graph without exceding a cost
 * 
 * Hopcroft–Karp algorithm
 * 
 * O(E * sqrt(V) ) for sparse graph
 * O(N
 * 
 * E -> number of edges
 * V -> number of vertices
 * 
 * @author Alexis Hernández
 * 
 * Un grafo es bipartito si y solo si no contiene ciclos de longitud impar.
 * Todos los arboles son bipartitos.
 * 
**/
package com.alex.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BipartiteMatching	{
	int N;	// number of elements in first graph (left)
	int M;	// number of elements in second graph (right)
	long [][] dist;	// dist[i][k] means how long is the path from left[i] to right[k]
	boolean visited [];	// visited[i] means if i-th element of left has been visited
	int right [];	// right[i] means that i-th element in right is matched witt right[i]-th element on left, -1 if is not matched
	
	BipartiteMatching(int N, int M, long [][] dist)	{
		this.N = N; 
		this.M = M;
		this.dist = dist;
		visited = new boolean [N];
		right = new int [M];
	}
	/**
	 * returns the maximum matching possible without exceding cost
	**/
	int getMaximumMatching(long cost)	{
		Arrays.fill(right, -1); // no one is matched
		int ans = 0;
		for (int v = 0; v < N; v++)	{
			Arrays.fill(visited, false); // no one is visited
			if	( match(v, cost) )
				ans++;
		}
		return ans;
	}
	/**
	 * returns true if left[v] could be matched with a element on right without exceding cost
	**/
	private boolean match(int v, long cost)	{
		if	( visited[v] )
			return	false;
		
		visited[v] = true;
		for (int w = 0; w < M; w++)	{
			if	( dist[v][w] < 0 || dist[v][w] > cost )	{
				// there is not a valid path from v to w
				continue;
			}
			if	( right[w] < 0	||	match( right[w], cost ) )	{
				// w is not matched or its coulple could get a new match
				right[w] = v;
				return	true;
			}
		}
		return	false;
	}

	/**
	 * returns is the given graph is bipartite
	**/
	static boolean isBipartite(boolean g [][])	{
		int N = g.length;
		Queue<Integer> q = new LinkedList<Integer>();
		boolean dist [] = new boolean [N];
		
		q.add(0);
		
		while	( !q.isEmpty() )	{
			int u = q.poll();
			for (int to = 0; to < N; to++)	if	( g[u][to] )	{
				if	( !dist[to] )	{
					dist[to] = !dist[u];
					q.add(to);
				}	else if ( dist[u] == dist[to] )	{
					// same color, problem
					return	false;
				}
			}
		}
		
		return	true;

	}
}

