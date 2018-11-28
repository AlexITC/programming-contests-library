/**
 * Implementation of min cost max flow algorithm using adjacency
 * matrix (Edmonds and Karp 1972).
 * 
 * This implementation keeps track of
 * forward and reverse edges separately (so you can set cap[i][j] != cap[j][i])
 * For a regular max flow, set all edge costs to 0.
 *
 * Running time, O(|V|^2) cost per augmentation
 *     max flow:           O(|V|^3) augmentations
 *     min cost max flow:  O(|V|^4 * MAX_EDGE_COST) augmentations
 *     
 * INPUT:
 *     - graph, constructed using AddEdge()
 *     - source
 *     - sink
 *
 * OUTPUT:
 *     - (maximum flow value, minimum cost value)
 *     - To obtain the actual flow, look at positive values only.
**/

package com.alex.graph;

import java.awt.Point;
import java.util.Arrays;

public class MinCostMaxFlow	{
	
	private final long INF = (1 << 40);
	private int N;
	private long [][] cap, flow, cost;
	private boolean [] found;
	private long [] dist, pi, width;
	private Point [] dad;
	
	// constructor
	public MinCostMaxFlow(int N)	{
		this.N = N;
		cap = new long [N][N];
		flow = new long [N][N];
		cost = new long [N][N];
		
		found = new boolean [N];
		dist = new long [N];
		pi = new long [N];
		width = new long [N];
		
		dad = new Point [N];
	}
	
	// add edge (from -> to)
	public void addEdge(int from, int to, long cap, long cost)	{
		this.cap[from][to] = cap;
		this.cost[from][to] = cost;
	}
	
	// relax edge (s -> k)
	private void relax(int s, int k, long flow, long cost, int dir)	{
		long val = dist[s] + pi[s] - pi[k] + cost;
		
		if	( flow != 0 && val < dist[k] )	{
			dist[k] = val;
			dad[k] = new Point(s, dir);
			width[k] = Math.min(flow, width[s]);
		}
	}
	
	// find an augmenting path (s -> t) and try to send a most flow as possible
	private long dijkstra(int s, int t, long f)	{
		if	( f == 0 )
			return	0;
		
		Arrays.fill(found, false);
		Arrays.fill(dist, INF);
		Arrays.fill(width, 0);
		
		dist[s] = 0;
		width[s] = INF;
		
		while	( s != -1 )	{
			int best = -1;
			found[s] = true;
			for (int k = 0; k < N; k++)	if	( !found[k] )	{
				
				relax( s, k, Math.min( cap[s][k] - flow[s][k], f ), cost[s][k], 1 );
				
				relax( s, k, flow[k][s], -cost[k][s], -1 );
				
				if	( best == -1	||	dist[k] < dist[best] )
					best = k;
			}
			
			s = best;
		}
		
		for (int k = 0; k < N; k++)
			pi[k] = Math.min( pi[k] + dist[k], INF );
		
		return	width[t];
	}

	/**
	 * get max flow min cost from s to t
	 * returns [ maxFlow, minCost ]
	**/
	public long[] getMaxFlow(int s, int t)	{
		long totalFlow = 0;
		long totalCost = 0;
		long amount = 0;
		while	( (amount = dijkstra(s, t, INF) ) != 0 )	{
			totalFlow += amount;
			
			for (int x = t; x != s; x = dad[x].x)	{
				if	( dad[x].y == 1 )	{
					flow[ dad[x].x ][x] += amount;
					totalCost += amount * cost[ dad[x].x ][x];
				}	else	{
					flow[x][ dad[x].x ] -= amount;
					totalCost -= amount * cost[x][ dad[x].x ];
				}
			}
		}
		
		return	new long [] { totalFlow, totalCost };
	}
	
	/**
	 * get min cost to send exact flow f from s to t
	 * returns [ flow, cost ], flow should be == f is there is a solution
	**/
	public long[] getExactFlowCost(int s, int t, long f)	{
		long totalFlow = 0;
		long totalCost = 0;
		long amount = 0;
		while	( (amount = dijkstra(s, t, f) ) != 0 )	{
			totalFlow += amount;
			f -= amount;
			
			for (int x = t; x != s; x = dad[x].x)	{
				if	( dad[x].y == 1 )	{
					flow[ dad[x].x ][x] += amount;
					totalCost += amount * cost[ dad[x].x ][x];
				}	else	{
					flow[x][ dad[x].x ] -= amount;
					totalCost -= amount * cost[x][ dad[x].x ];
				}
			}
		}
		
		return	new long [] { totalFlow, totalCost };
	}
	
}



