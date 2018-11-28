/**
 * A class for computing the minumun spanning tree using prims algorithm (lazy version)
 * 
 * @author Alexis Hernández
**/

package com.alex.graph;

import java.util.*;

public class LazyPrimMST	{
	PriorityQueue<Node> pq;
	Queue<Node> mst;	// iterable minimun spanning tree
	boolean visited [];	// visited[i] = true if node i is on the mst
	// graph[i] = list of adyacent nodes from i
	LazyPrimMST(LinkedList<Node> graph [])	{
		int N = graph.length;
		pq = new PriorityQueue<Node>();
		// generate MST
		mst = new LinkedList<Node>();
		visit(graph, 0);
		while	( !pq.isEmpty()	&&	mst.size() < N - 1 )	{
			Node node = pq.poll();
			// if both vertex are in the mst, avoid it
			if	( visited[node.from] && visited[node.to] )	continue;
			// add the current vertex to the mst
			mst.add(node);
			// visit adyacent vertex to [from];
			if	( !visited[node.from] )	visit(graph, node.from);
			// visit adyacent vertex to [to]
			if	( !visited[node.to] )	visit(graph, node.to);
		}
	}
	// visit adyacent edges (unvisited) in grap[id] node
	void visit(LinkedList<Node> graph [], int id)	{
		visited[id] = true;
		for (Node node : graph[id])	if	( !!visited[ node.from ]	||	!visited[ node.to ] )
			pq.add(node);
	}

	//Prim node, edge [from -> to] = cost
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
}

