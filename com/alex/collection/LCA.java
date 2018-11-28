/**
 * A class for handling queries for solving the lowest common ancestor (LCA)
 * in a tree in O(lg N)
 *
 * tests:
 * http://acm.tju.edu.cn/toj/showp2241.html
 *
 * Author: Alexis Hernández
 * Date: 04-11-2013
**/

package com.alex.collection;

import java.io.*;
import java.util.*;

public class LCA	{
	int [] E;	// E[i] is the id of node visited in step i using Euler Tour
	int [] L;	// L[i] level of node E[i]
	int [] R;	// R[i] contains the index of a ocurrence of node i in E
	LCA(ArrayList<Integer> graph [], int root)	{
		int N = graph.length;
		R = new int [N];
		E = new int [ (N << 1) - 1 ];
		L = new int [ (N << 1) - 1 ];
		Arrays.fill(R, -1);
		boolean visited [] = new boolean [N];
		eulerTour(graph, visited, root, 0);
		buildRMQ( L.length );
	}
	int index = 0;
	private void eulerTour(ArrayList<Integer> graph [], boolean [] visited, int root, int level)	{
		E[index] = root;
		L[index] = level;
		// first ocurrence of root in the tour
		if	( R[root] == -1 )	R[root] = index;
		index++;
		visited[root] = true;
		for ( int v : graph[root] )	{
			if	( visited[v] )	continue;
			eulerTour(graph, visited, v, level + 1);
			E[index] = root;
			L[index] = level;
			index++;
		}
	}
	// Range Minimum Query for R[]
	int N;
	int [] RMQ;	
	private void buildRMQ(int size)	{
		N = 1;
		while ( (N <<= 1) < size);
		RMQ = new int[N << 1];
		Arrays.fill( RMQ, -1 );
		for (int i = 0; i < L.length; i++)	{
			int dad = i + N;
			RMQ[ dad ] = i;
			// update dads of i
			dad >>= 1;
			while	( dad > 0 )	{
				RMQ[dad] = minIndex( RMQ[dad << 1], RMQ[(dad << 1) + 1] );
				dad >>= 1;
			}
		}
	}
	// returns the index of E with the minimun level in range L[l,r]
	int getMin(int l, int r) {
		int ans = l;
		l += N;
		r += N;
		while (l <= r) {
			if (l == r)	return	minIndex(ans, RMQ[l]);
			if ((l & 1) == 1)	ans = minIndex(ans, RMQ[l++]);
			if ((r & 1) == 0)	ans = minIndex(ans, RMQ[r--]);
			l >>= 1;
			r >>= 1;
		}
		return ans;
	}
	// returns the index with the minimum level
	private int minIndex(int a, int b)	{
		if	( a < 0 )	return	b;
		if	( b < 0 )	return	a;
		return	L[a] <= L[b] ? a : b;
	}
	// get the LCA of node a and b, O(lg N)
	int getLCA(int a, int b)	{
		a = R[a];
		b = R[b];
		int min = a < b ? getMin(a, b) : getMin(b, a);
		return	E[min];
	}
	/**
	 * Test client
	**/
	static BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
	static StringTokenizer st = new StringTokenizer("");
	static String next()	throws Exception	{
		while	( !st.hasMoreTokens() )	st = new StringTokenizer( br.readLine() );
		return	st.nextToken();
	}
	public static void main(String [] asda)	throws Exception	{
		PrintWriter out = new PrintWriter( new BufferedOutputStream(System.out) );
		//
		int CASES = Integer.parseInt( next() ), N, i;
		while	(CASES-- > 0)	{
			N = Integer.parseInt( next() );
			ArrayList<Integer> graph [] = (ArrayList<Integer> []) new ArrayList[N];
			i = 0;
			while	(i < N)	graph[i++] = new ArrayList<Integer>();
			while	(--N > 0)	{
				int a = Integer.parseInt( next() );
				int b = Integer.parseInt( next() );
				graph[a].add(b);
				graph[b].add(a);
			}
			// 0 is the root of the tree
			LCA lca = new LCA(graph, 0);
			//queries
			N = Integer.parseInt( next() );
			while	(N-- > 0)	{
				int a = Integer.parseInt( next() );
				int b = Integer.parseInt( next() );
				// a is ancestor of b?
				if	( a == b || lca.getLCA(a,b) != a )	out.println("No");
				else	out.println( "Yes" );
			}
			out.println();
		}
		out.flush();
	}
}

/*

10

0 5
0 1
0 8

5 2
5 4

8 9
8 3
8 7

3 6

*/

