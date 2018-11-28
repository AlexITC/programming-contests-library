package com.alex.collection;

import java.util.ArrayList;

public class LCA2 {
	int N;
	int logN;
	int[] dep;
	int[][] go;
	ArrayList<Integer>[] g;
	// 1 based index
	LCA2(ArrayList<Integer> g[], int root) {
		this.g = g;
		N = g.length;
		logN = 1;
		while ((1 << logN) <= N)
			logN++;

	//	System.out.println(N + " " + logN);
		dep = new int[N];
		go = new int[N][logN];

		dfs(root, 0, 0);

		// Prepare for LCA queries.
		for (int k = 1; k < logN; ++k) {
			for (int i = 1; i < N; ++i) {
				go[i][k] = go[go[i][k - 1]][k - 1];
			}
		}
	}

	private void dfs(int id, int depth, int dad) {
		go[id][0] = dad;
		dep[id] = depth;

		for (int to : g[id]) {
			if ( to != dad )
				dfs(to, depth + 1, id);
		}
	}

	int getLCA(int u, int v) {
		if (dep[u] < dep[v]) {
			int aux = u;
			u = v;
			v = aux;
		}

		int diff = dep[u] - dep[v];
		for (int i = 0; diff != 0; ++i, diff >>= 1) {
			if ( (diff & 1) != 0 ) {
				u = go[u][i];
			}
		}

		if (u == v)
			return u;

		for (int i = logN - 1; i >= 0; --i) {
			if (go[u][i] != go[v][i]) {
				u = go[u][i];
				v = go[v][i];
			}
		}
		return go[u][0];
	}
}
