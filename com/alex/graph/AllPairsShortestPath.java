package com.alex.graph;

public class AllPairsShortestPath {

	private int N;
	private int cost [][];
	private final int INF;
	
	public AllPairsShortestPath(int cost [][], final int INF)	{
		N = cost.length;
		this.INF = INF;

		// compute all pairs shortest path
		for ( int k = 0;	k < N;	k++ )	for ( int i = 0;	i < N;	i++ )	for ( int j = 0;	j < N;	j++ )
			cost[i][j] = Math.min( cost[i][j], cost[i][k] + cost[k][j] );
	}
	// returns the shorest path cost from a to b, -1 is there is not a path
	public int shorestPath(int a, int b)	{
		return	cost[a][b] >= INF ? -1 : cost[a][b];
	}
}
