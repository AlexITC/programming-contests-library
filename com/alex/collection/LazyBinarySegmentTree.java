/**
 * A Binary Segment Tree using Lazy Propagations for updates and queries in a range [L,R]
 *
 * tests:
 * http://www.spoj.com/problems/HORRIBLE/
 *
 * Author: Alexis Hernández
**/

package com.alex.collection;

public class LazyBinarySegmentTree {
	
	int N;	// where data starts
	long [] tree, helper;
	
	/**
	 * Crea un LazyBinarySegmentTree para que soporte operaciones en el rango [0, size], inclusivo.
	 * @param size		La posicion maxima que manejara el arbol.
	 */
	public LazyBinarySegmentTree(long size) {
		N = 1;
		while ( (N <<= 1) < size);
		tree = new long[N << 1];
		helper = new long[N << 1];
	}
	
	/**
	 * Add, n points in the range [lo, hi], inclusive
	 * O(lg n)
	 */
	void add(int left, int right, long v) {
		// add( root, begin of data, end of data, begin range, end range, val to add in range )
		add( 1, 0, tree.length - N - 1, left, right, v );
	}
	/**
	 * add value val to the range [left, right], we are working in [nodeRangeLeft, nodeRangeRight]
	**/
	private void add(int node, int nodeRangeLeft, int nodeRangeRight, int left, int right, long v)	{
		// if the interval is invalid
		if	( nodeRangeLeft > nodeRangeRight || left > right || !(left >= nodeRangeLeft && right <= nodeRangeRight) )
			return;
		// check if the intervals are the same
		if	( nodeRangeLeft == left && nodeRangeRight == right )	{
			// set a flag to node
			setFlag( node, v );
			return;
		}
		// overlap intervals
		tree[node] += v * ( (long)right - left + 1 );
		// left range
		add( 
			node << 1,
			nodeRangeLeft, (nodeRangeLeft + nodeRangeRight) >> 1,
			left, Math.min( right, (nodeRangeLeft + nodeRangeRight) >> 1 ),
			v
		);
		// right range
		add( 
			(node << 1) + 1,
			( (nodeRangeLeft + nodeRangeRight) >> 1 ) + 1, nodeRangeRight,
			Math.max( left, ( (nodeRangeLeft + nodeRangeRight) >> 1 ) + 1 ), right,
			v
		);
	}
	// set a flag to node
	private void setFlag(int node, long value)	{
		if	( node >= tree.length )	return;
		helper[node] += value;
	}

	//
	long getSum( int left, int right )	{
		return	getSum( 1, 0, tree.length - N - 1, left, right );
	}
	private long getSum(int node, int nodeRangeLeft, int nodeRangeRight, int queryLeft, int queryRight)	{
		if	( nodeRangeLeft > nodeRangeRight || queryLeft > queryRight || !(queryLeft >= nodeRangeLeft && queryRight <= nodeRangeRight) )
			return	0;
		// si hay bandera, quitarla
		if	( helper[node] != 0 )	{
			tree[node] += helper[node] * ( (long)nodeRangeRight - nodeRangeLeft + 1 );
			setFlag( node << 1, helper[node] );
			setFlag( (node << 1) + 1, helper[node] );
			helper[node] = 0;
		}
		if	( nodeRangeLeft == queryLeft && nodeRangeRight == queryRight )	{
			return	tree[node];
		}
		long ans = 0;
		// left range
		ans = getSum( 
			node << 1,
			nodeRangeLeft, (nodeRangeLeft + nodeRangeRight) >> 1,
			queryLeft, Math.min( queryRight, (nodeRangeLeft + nodeRangeRight) >> 1 )
		);
		// right range
		ans += getSum( 
			(node << 1) + 1,
			( (nodeRangeLeft + nodeRangeRight) >> 1 ) + 1, nodeRangeRight,
			Math.max( queryLeft, ( (nodeRangeLeft + nodeRangeRight) >> 1 ) + 1 ), queryRight
		);
		return	ans;
	}
}



