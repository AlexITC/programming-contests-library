package com.alex.graph;

class Dinic	{
	
    final int INF = 1 << 29;
    final int N, M;

	int [] eadj, elast, eprev;
	int eidx;
	
	int [] flow, capacity, now;	// E
	int [] level; // N
	
	// nodes, edges
	Dinic(int N, int M)	{
		this.N = N;
		this.M = M;
		
		eadj = new int [M * 2];	 // list of edges
		elast = new int [N];     // elast[v] = last edge id (for eadj) of vertex v
		eprev = new int [M * 2]; // prev[eid] = previous edge of eid (both from same vertex)
		eidx = 0;
		Arrays.fill(elast, -1);
		
		flow = new int [M * 2];
		capacity = new int [M * 2];
		now = new int [M * 2];	// current augmenting path
		
		level = new int [N];
	}
	
	// from, to, capacity
	void addEdge(int a, int b, int c)	{
		eadj[eidx] = b;
		flow[eidx] = 0;
		capacity[eidx] = c;
		eprev[eidx] = elast[a];
		elast[a] = eidx++;

		// back edge
		eadj[eidx] = a;
		flow[eidx] = 0;
		capacity[eidx] = c;
		eprev[eidx] = elast[b];
		elast[b] = eidx++;
	}
	
	long maxFlow(int source, int sink)	{
		long flow = 0;

		while	( bfs(source, sink) )	{
			System.arraycopy(elast, 0, now, 0, N);
			long sent;
			while	( (sent = dfs(source, INF, sink) ) > 0 )
				flow += sent;
		}
			
		return	flow;
	}
	
	// find an augmenting path
	private boolean bfs(int source, int sink)	{
		Arrays.fill(level, -1);
		// queue
		int [] queue = new int [N];
		int front = 0;
		int back = 0;
		
		level[source] = 0;
		queue[back++] = source;
		// queue not empty and sink not reached
		while	( front < back && level[sink] == -1 )	{
			int node = queue[front++];
			// traverse edges of node
			for (int e = elast[node]; e != -1; e = eprev[e])	{
				int to = eadj[e];
				// to is not visited and has residual capacity
				if	( level[to] == -1 && flow[e] < capacity[e] )	{
					level[to] = level[node] + 1;
					queue[back++] = to;
				}
			}
		}
		return	level[sink] != -1;
	}
	
	// send max possible flow using the last augmenting path found
	private long dfs(int source, int currFlow, int sink)	{
		if	( source == sink )
			return	currFlow;
		
		for (int e = now[source]; e != -1; now[source] = e = eprev[e])	{
			if	( level[ eadj[e] ] > level[source] && flow[e] < capacity[e] )	{
				// eadj[e] is the next node in the augmenting path
				long sent = dfs( eadj[e], Math.min( currFlow, capacity[e] - flow[e] ), sink );
				if	( sent > 0 )	{
					flow[e] += sent;
					flow[e ^ 1] -= sent;
					return	sent;
				}
			}
		}
		return	0;
	}
}
