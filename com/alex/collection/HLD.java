import java.io.*;
import java.util.*;

class HLD	{
	int N;
	int root;
	HLD(int N)	{
		this.N = N;
		root = 0;

		lastEdge = new int [N];
		Arrays.fill(lastEdge, -1);

		int M = (N-1)*2;
		prevEdge = new int [M];
		edgeNode = new int [M];
		edgeId = 0;

		tree = new SegmentTree(N);
	}

	int edgeId;			// id del siguiente enlace a usar
	int [] lastEdge;	// lastEdge[k] = id del ultimo link agregado al nodo k
	int [] prevEdge;	// prevEdge[k] = id del enlace anterior a k en la lista de enlaces
	int [] edgeNode;	// edgeNode[k] = id del nodo a donde llega el enlace k
	void addEdge(int a, int b)	{
		edgeNode[edgeId] = b;
		prevEdge[edgeId] = lastEdge[a];
		lastEdge[a] = edgeId++;
		// back edge
		edgeNode[edgeId] = a;
		prevEdge[edgeId] = lastEdge[b];
		lastEdge[b] = edgeId++;
	}

	SegmentTree tree;
	void update(int node)	{
	//	System.out.printf("update(%d)\n", node+1);
		tree.update( nodeIndex[node] );
	}

	// ask for the id of the first black node on the path from node 1 to node v
	int query(int node)	{
	//	System.out.printf( "query(%d) = ", node+1 );
		int ancestor = root;
		int ans = -1;
		do	{
			int head = chainHead[ chainIndex[node] ];
	//		System.out.printf("chain %d ", chainIndex[node] );
			int cur = tree.query( nodeIndex[head], nodeIndex[node] );
	//		System.out.printf("(%d, %d) = %d, ", head+1, node+1, cur);
			node = parent[head];

			if (cur != -1) {
				ans = reverseIndex[cur]+1;
			}
		}	while	( node != ancestor );
	//	System.out.println();
		return	ans;
	}

	void build()	{
		parent = new int [N];
		size = new int [N];

		/* PREPARE LCA */
		logN = 1;
		while ((1 << logN) <= N)	logN++;

		dep = new int[N];
		go = new int[N][logN];

		dfs(root, 0, root);

		for (int k = 1; k < logN; ++k) {
			for (int i = 1; i < N; ++i) {
				go[i][k] = go[go[i][k - 1]][k - 1];
			}
		}
		/* END LCA */

		currentChain = 0;
		nextIndex = 0;
		nodeIndex = new int [N];
		reverseIndex = new int [N];
		chainIndex = new int [N];
		chainHead = new int [N];
		Arrays.fill(chainHead, -1);
		decompose(root);
	}

	int [] parent;	// parent[k] = el id del nodo padre del nodo k
	int [] size;	// size[k] = el tamaño del subarbot con raiz en el nodo k
	void dfs(int node, int depth, int dad)	{
		size[node] = 1;
		parent[node] = dad;
		dep[node] = depth;
		go[node][0] = dad;
		for (int e = lastEdge[node]; e != -1; e = prevEdge[e])	{
			int to = edgeNode[e];
			if (to != dad) {
				dfs(to, depth + 1, node);
				size[node] += size[to];
			}
		}
	}

	int currentChain;	// el id del chain actual
	int nextIndex;		// el id del siguiente nodo a usar
	int [] nodeIndex;	// nodeIndex[k] = el id del nodo k despues de la descomposicion
	int [] reverseIndex;// reverseIndex[ nodeIndex[k] ] = k
	int [] chainIndex;	// chainIndex[k] = el id del chain al que el nodo k pertenece
	int [] chainHead;	// chainHead[k] = el id del nodo que esta al inicio del chain k
	void decompose(int node)	{
		if (chainHead[currentChain] == -1) {
			// el chain no se a creado
			chainHead[currentChain] = node;
		}
	//	System.out.printf("node %d at chain %d\n", node+1, currentChain);
		nodeIndex[node] = nextIndex;
		reverseIndex[nextIndex] = node;
		nextIndex++;
		chainIndex[node] = currentChain;

		int heavyChild = -1;
		for (int e = lastEdge[node]; e != -1; e = prevEdge[e])	{
			int to = edgeNode[e];
			if (to != parent[node]) {
				if (heavyChild == -1 || size[to] > size[heavyChild]) {
					heavyChild = to;
				}
			}
		}

		if (heavyChild == -1) {
			return;
		}

		decompose(heavyChild);

		for (int e = lastEdge[node]; e != -1; e = prevEdge[e])	{
			int to = edgeNode[e];
			if (to != parent[node] && to != heavyChild) {
				currentChain++;
				decompose(to);
			}
		}
	}

	private int logN;
	private int[] dep;
	private int[][] go;
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

class Main implements Runnable {

	static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	public static void main(String[] asda) throws Exception {
		new Thread(null, new Main(), "Main", 1 << 30).start();
	}
	public void run()	{
		InputReader reader = new InputReader(System.in);
		int N = reader.readInt();
		int Q = reader.readInt();

		HLD h = new HLD(N);
		for (int k = 1; k < N; k++)	{
			int a = reader.readInt() - 1;
			int b = reader.readInt() - 1;

			h.addEdge(a, b);
		}

		h.build();

		while	(Q-- > 0)	{
			int op = reader.readInt();
			int node = reader.readInt() - 1;
			if (op == 0) {
				// change color at node
				h.update(node);
			}	else	{
				out.println( h.query(node) );
			}
		}
        //
        out.flush();
        System.exit(0);
    }
}
