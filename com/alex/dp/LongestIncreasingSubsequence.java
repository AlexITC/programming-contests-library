package com.alex.dp;

public class LongestIncreasingSubsequence {


	/**
	 * find the longest strictly increasing subsequence
	 * returns its length
	 * O(n log n)
	**/
	static int LIS(int v[]){
		int N = v.length;
		if	( N == 1 )
			return	1;
		 
	    int [] tailTable = new int[N + 1];
	    int len; // always points empty slot
	 
	    tailTable[0] = v[0];
	    len = 1;
	    for( int i = 1; i < N; i++ ) {
	        if( v[i] < tailTable[0] )
	            // new smallest value
	            tailTable[0] = v[i];
	        else if( v[i] > tailTable[len-1] )
	            // A[i] wants to extend largest subsequence
	            tailTable[len++] = v[i];
	        else
	            // A[i] wants to be current end candidate of an existing subsequence
	            // It will replace ceil value in tailTable
	            tailTable[ CeilIndex(tailTable, -1, len-1, v[i]) ] = v[i];
	    }
	    return len;
	}
	// Binary search (note boundaries in the caller)
	// A[] is ceilIndex in the caller
	static int CeilIndex(int A[], int l, int r, int key) {
	    int m;
	    while( r - l > 1 ) {
	        m = l + (r - l)/2;
	        if	( A[m] >= key )
	        	r = m;
	        else
	        	l = m;
	    }
	    return r;
	}

}
