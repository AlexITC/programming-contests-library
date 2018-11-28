/**
 * A class for computing topological sorting for a directed graph (weighted and unweighted directed graph)
 * 
 * tested: 
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1246
 * 
 * @author Alexis Hernández
**/

package com.alex.graph;

import java.util.*;
import java.io.*;

// 
// 
public class TopologicalSort	{
	// the topological sort is the revese post order in a directed acyclic graph
	Stack<Integer> order;		// iterable reverse post order
	boolean visited [];			// visited[i] = true if node i is on the topological order
	ArrayList<Integer>[] g;
	
	public TopologicalSort(ArrayList<Integer>[] g)	{
		int N = g.length;
		this.g = g;
		visited = new boolean [N];
		// generate topological order
		order = new Stack<Integer>();
		for (int i = 0; i < N; i++)	if	( !visited[i] )
			dfs( i );
	}
	
	/**
	 * returns the first topologicar order (lexicographical)
	 * O( V + E + N * logN )
	**/
	public static int [] firstTopologicalOrder(ArrayList<Integer>[] g)	{
		int N = g.length;

		int [] incomming = new int [N];	// how many vertex has a path to i-th vertex?
		for (int i = 0; i < N; i++)	for (int v : g[i])
			incomming[v]++;
		

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		// add all vertex without precedence
		for (int i = 0; i < N; i++)	if	( incomming[i] == 0 )	{
			pq.add(i);
		}

		int [] order = new int [N];
		int len = 0;
		while	( !pq.isEmpty() )	{
			int v = pq.poll();
			order[len++] = v;	// put v into topological order
			// remove all outgoing vertex from v and add all vertex with no precedence after the remove
			for (int w : g[v])	if	( --incomming[w] == 0 )	{
				pq.add(w);
			}
		}
		// if len != N then g have a cycle
		return	len == N ? order : null;
	}

	// directed weighted graph, edge [from -> to] = cost
	private void dfs(int id) {
		visited[id] = true;
		
		for (int to : g[id])	if ( !visited[to] )
			dfs(to);
			
		order.push(id);
	}

	// test if the given irder is a valid topological sort
	// O(V + E)
	// V = number of vertex, E = number of edges
	boolean isTopologicalSorted(ArrayList<Integer> graph [], Stack<Integer> order)	{
		boolean [] visited = new boolean [ this.visited.length ];
		while	( !order.isEmpty() )	{
			int v = order.pop();
			for (Integer key : graph[v])	if	( !visited[key] )	{
			//	out.println(" " + v + " should be done affter than " + key);
				return false;
			}
			visited[v] = true;
		}
		return	true;
	}

	public static void main(String [] asdas)	{
		Scanner in = new Scanner(System.in);

		int N = in.nextInt();
		int M = in.nextInt();
		
		ArrayList<Integer> [] g = new ArrayList [N];
		for (int i = 0; i < N; i++)	g[i] = new ArrayList<Integer>();
		
		while	(M-- > 0)	{
			int a = in.nextInt();
			int b = in.nextInt();
			g[a].add(b);
		}
		TopologicalSort sort = new TopologicalSort(g);

		PrintStream out = System.out;
		out.println("order: ");
		while	( !sort.order.isEmpty() )	{
			out.println( sort.order.pop() );
		}
	}
}
/*
7
10
0 1
0 2
0 5
1 4
3 2
3 4
3 5
3 6
5 2
6 0

5
4
1 2
2 3
1 3
1 5


*/
