/**
 * KthElement is a class for maintain a set of elements (allow repeated values) like a BST using segment tree structure
 * Complexity: O(logN) per operation
**/

package com.alex.collection;

public class KthElement	{
	
	int N;
	int [] cnt;
	/**
	 * 
	 * @param numOfElements = how many different elements are allowed [0 ... numOfElements)
	**/
	KthElement(int numOfElements)	{
		
		N = Integer.highestOneBit(numOfElements);
		if	( N < numOfElements )
			N <<= 1;
		
		cnt = new int [ N << 1 ];
	}
	// insert x into the liss of elements ( x is a compressed value )
	void add(int x)	{
		x += N;
		
		if	( x >= cnt.length )
			throw new IllegalArgumentException("element out of range");

		if	( cnt[x] != 0 )
			return;		// x is already in set
		
		while	( x != 0 )	{

			cnt[x]++;
			
			x >>= 1;
		}
		
	}
	// remove x from the liss of elements (if exists) (x is a compressed value )
	void remove(int x)	{
		x += N;
		
		if	( x >= cnt.length )
			throw new IllegalArgumentException("element out of range");

		if	( cnt[x] == 0 )
			return;	// element is not in set
		
		while	( x != 0 )	{

			cnt[x]--;
			
			x >>= 1;
		}
		
	}
	
	// find the k-th smallest element from the set, -1 if that element doesn't exists
	// returns a compressed value
	int kthSmallestElement(int k)	{
		
		if	( k > cnt[1] )
			return	-1;
		
		int node = 1;
		while	( node < N )	{
			
			// is the value in left son?
			if	( cnt[ node << 1 ] >= k )
				node <<= 1;
			else	{
				// right son
				node <<= 1;
				
				k -= cnt[node++];
			}
			
		}
		
		return	node - N;
	}
	
	// count how many values are less than x ( x is a compressed value )
	int count(int x)	{
		
		int L = N;
		int R = x - 1 + N;

		int ans = 0;
		
		while	( L <= R )	{
			
			if	( L == R )
				return	ans + cnt[L];

			if ((L & 1) == 1)	ans += cnt[L++];
			if ((R & 1) == 0)	ans += cnt[R--];
			
			L >>= 1;
			R >>= 1;
		}
		
		return	ans;
	}
	
}
