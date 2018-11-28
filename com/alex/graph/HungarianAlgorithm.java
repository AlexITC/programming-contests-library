/**
 * Hungarian Algorithm for the (Maximum Profit / Minimum Cost) Exactly Matching in a Bipartite Graph
 * 
 * Complexity: O(N^3)
**/
package com.alex.graph;

import java.util.Arrays;

public class HungarianAlgorithm {
	final int INF = 100000000; // just infinity

	int N;				// number of vertices ( N jobs, N workers )
	int currentMatch;	// whats the current matching?
	int requestedMatch;	// how many vertices we want to match?

	int [][] cost;		// cost/profit matrix, cost[i][k] = cost for match (i, k)
	int [] labelX, labelY;		// labels of X and Y parts
	int [] xy; 			// xy[x] - vertex that is matched with x,
	int [] yx; 			// yx[y] - vertex that is matched with y
	boolean [] S, T;	// sets S and T in algorithm
	int [] slack; 		// as in the algorithm description
	int [] slackx;		// slackx[y] such a vertex, that
						// l(slackx[y]) + l(y) - w(slackx[y],y) = slack[y]
	int[] prev; 		// array for memorizing alternating paths

	// initialize values, cost = matrix for costs / profits
	public HungarianAlgorithm(int[][] cost) {
		this.cost = cost;
		N = cost.length;

		labelX = new int[N];
		labelY = new int[N];

		xy = new int[N];
		yx = new int[N];

		S = new boolean[N];
		T = new boolean[N];

		slack = new int[N];
		slackx = new int[N];

		prev = new int[N];
	}

	// returns the max profit for matching exactly num vertices
	int getMaximumProfit(int num) {
		this.requestedMatch = num;
		currentMatch = 0; // number of vertices in current matching

		Arrays.fill(xy, -1);
		Arrays.fill(yx, -1);

		initLabels(); // step 0
		augment(); // steps 1-3

		// forming answer
		int profit = 0; // weight of the optimal matching
		for (int x = 0; x < N; x++)
			profit += cost[x][xy[x]];

		return profit;
	}

	// returns the minimum cost for matching exactly num vertices
	int getMinimumCost(int num) {
		this.requestedMatch = num;

		// invert costs
		for (int i = 0; i < N; i++)
			for (int k = 0; k < N; k++)
				cost[i][k] *= -1;

		currentMatch = 0; // number of vertices in current matching

		Arrays.fill(xy, -1);
		Arrays.fill(yx, -1);

		initLabels(); // step 0
		augment(); // steps 1-3

		// invert costs again
		for (int i = 0; i < N; i++)
			for (int k = 0; k < N; k++)
				cost[i][k] *= -1;

		// forming answer
		int min = 0; // weight of the optimal matching
		for (int x = 0; x < N; x++)
			min += cost[x][xy[x]];

		return min;
	}

	
	private void initLabels() {
		Arrays.fill(labelX, 0);
		Arrays.fill(labelY, 0);

		for (int x = 0; x < N; x++)	for (int y = 0; y < N; y++)
			labelX[x] = Math.max( labelX[x], cost[x][y] );
	}


	private void updateLabels() {
		int x, y, delta = INF; // init delta as infinity

		// calculate delta using slack
		for (y = 0; y < N; y++)	if ( !T[y] )
			delta = Math.min(delta, slack[y]);

		// update X labels
		for (x = 0; x < N; x++)	if ( S[x] )
			labelX[x] -= delta;

		// update Y labels
		for (y = 0; y < N; y++)	if ( T[y] )
			labelY[y] += delta;

		// update slack array
		for (y = 0; y < N; y++)	if ( !T[y] )
			slack[y] -= delta;
	}

	// x - current vertex,prevx - vertex from X before x in the alternating
	// path,
	// so we add edges (prevx, xy[x]), (xy[x], x)
	private void addToTree(int x, int prevx) {
		S[x] = true; // add x to S
		prev[x] = prevx; // we need this when augmenting

		// update slacks, because we add new vertex to S
		for (int y = 0; y < N; y++)	if ( labelX[x] + labelY[y] - cost[x][y] < slack[y] ) {
			slack[y] = labelX[x] + labelY[y] - cost[x][y];
			slackx[y] = x;
		}
	}

	// fian an augmenting path unti perfect matching is done
	private void augment()	{
		if (currentMatch == requestedMatch)
			return; // check wether matching is already perfect
		
		int x, y, root = 0; // just counters and root vertex

		int [] q = new int[N];	// q - queue for bfs
		int wr = 0, rd = 0; // wr,rd - write and read pos in queue

		Arrays.fill(S, false); // init set S
		Arrays.fill(T, false); // init set T
		Arrays.fill(prev, -1); // init set prev - for the alternating tree

		// finding root of the tree
		for (x = 0; x < N; x++)	if (xy[x] == -1) {
			q[wr++] = root = x;
			prev[x] = -2;
			S[x] = true;
			break;
		}

		// initializing slack array
		for (y = 0; y < N; y++)	{
			slack[y] = labelX[root] + labelY[y] - cost[root][y];
			slackx[y] = root;
		}

		// second part of augment() function
		while (true) { // main cycle
			// building tree with bfs cycle
			while (rd < wr) {
				x = q[rd++]; // current vertex from X part

				// iterate through all edges in equality graph
				for (y = 0; y < N; y++)	if ( cost[x][y] == labelX[x] + labelY[y] && !T[y] ) {
					
					if (yx[y] == -1)
						break; // an exposed vertex in Y found, so augmenting path exists!
					
					T[y] = true; // else just add y to T,
					
					q[wr++] = yx[y]; // add vertex yx[y], which is matched with y, to the queue
					
					addToTree(yx[y], x); // add edges (x,y) and (y,yx[y]) to the tree
				}

				if (y < N)	break; // augmenting path found!
			}

			if (y < N)	break; // augmenting path found!

			updateLabels(); // augmenting path not found, so improve labeling
			wr = rd = 0;

			// in this cycle we add edges that were added to the equality graph
			// as a
			// result of improving the labeling, we add edge (slackx[y], y) to
			// the tree if
			// and only if !T[y] && slack[y] == 0, also with this edge we add
			// another one
			// (y, yx[y]) or augment the matching, if y was exposed
			for (y = 0; y < N; y++)	if ( !T[y] && slack[y] == 0 ) {
				if (yx[y] == -1) { // exposed vertex in Y found - augmenting path exists!
					x = slackx[y];
					break;
				}
				
				// else
				T[y] = true; // else just add y to T,
				if (!S[yx[y]]) {
					q[wr++] = yx[y]; // add vertex yx[y], which is matched with y, to the queue
					
					addToTree(yx[y], slackx[y]); // and add edges (x,y) and (y, yx[y]) to the tree
				}
			}

			if (y < N)	break; // augmenting path found!
		}

		if (y < N) { // we found augmenting path!
			
			currentMatch++; // increment matching
			// in this cycle we inverse edges along augmenting path
			for (int cx = x, cy = y, ty; cx != -2; cx = prev[cx], cy = ty) {
				ty = xy[cx];
				yx[cy] = cx;
				xy[cx] = cy;
			}
			augment(); // recall function, go to step 1 of the algorithm
		}
	}

}
