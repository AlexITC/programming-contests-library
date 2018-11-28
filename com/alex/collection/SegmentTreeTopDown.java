/**
 * Segment tree (top-down version)
 * Use it for an easy data augmentation
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;


public class SegmentTreeTopDown	{
	static class Node	{
		int sum, pref, suff, ans;
		// empty node
		public Node()	{
			this( -(Integer.MAX_VALUE >> 4) );
		}
		public Node(int val)	{
			sum = val ;
			pref = suff = ans = val;
		}
		// combine nodes
		public Node(Node a, Node b)	{
			sum = a.sum + b.sum ;
			pref =  Math.max( a.pref, a.sum + b.pref ) ;
			suff = Math.max( b.suff, b.sum + a.suff ) ;
			ans = Math.max ( Math.max(a.ans, b.ans), a.suff + b.pref ) ;
		}
	}
	private int N;	// index where data starts
	private Node [] tree;
	// create an empty segment tree
	public SegmentTreeTopDown(int size)	{
		N = 1;
		while	( (N <<= 1) < size );
		tree = new Node [ N << 1 ];
		// fill tree with empty values
		for (int i = 0; i < tree.length; i++)
			tree[i] = new Node();
	}
	// create a segment tree with the given data
	public SegmentTreeTopDown(Node [] data)	{
		int size = data.length;
		N = 1;
		while	( (N <<= 1) < size );
		tree = new Node [ N << 1 ];
		// fill tree with data
		build( data, 1, 0, N - 1);
	}
	/**
	 * data: init data array
	 * node: index in tree (starting in 1)
	 * left: left range for current node
	 * right: right range for current node
	**/
	private void build(Node data [], int node, int left, int right)	{
		if	( left == right )	{
			tree[node] = left < data.length ? data[left] : new Node();
			return;
		}
		int mid = (left + right) >> 1;
		int son = node << 1;
		build( data, son, left, mid);	// update left son
		build( data, son + 1, mid + 1, right);	// update right son
		tree[node] = new Node( tree[son], tree[son + 1] );	// update node
	}
	public Node query(int l, int r)	{
		return	query(1, 0, N - 1 ,l, r);
	}
	private Node query(int node, int left, int right, int qLeft, int qRight)	{
		if ( qLeft > qRight )
			return	new Node();
		if ( left == qLeft && right == qRight )
			return	tree[node];

		int mid = (left + right) >> 1;
		int son = node << 1;
		return	new Node(
			query( son, left, mid, qLeft, Math.min(mid, qRight) ),
			query( son + 1, mid + 1, right, Math.max(qLeft, mid + 1), qRight)
		);
	}
	public void update(int idx, Node val)	{
		idx += N;
		tree[idx] = val;
		idx >>= 1;
		while	( idx != 0 )	{
			tree[idx] = new Node(
				tree[ idx << 1 ],
				tree[ (idx << 1) + 1]
			);
			idx >>= 1;
		}
	}
}

