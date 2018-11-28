/**
 * Fenwick tree for range queries / updates in log N
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;

/**
 * FenwickTree, a class for handling operations in the range [0...N-1] inclusive
**/
public class FenwickTree	{
	long [] dataAdd, dataMul;
	FenwickTree(int N)	{
		dataAdd = new long [N];
		dataMul = new long [N];
	}
	// update dataAdd[at] += by
	void update(int at, int by) {
	    while (at < dataAdd.length) {
	    	dataAdd[at] += by;
	        at |= (at + 1);
	    }
	}
	// returns dataAdd[at]
	int query(int at) {
	    int res = 0;
	    while (at >= 0) {
	        res += dataAdd[at];
	        at = (at & (at + 1)) - 1;
	    }
	    return res;
	}
	// update data[left, right] += by
	void rupdate(int left, int right, long by) {
	    internalUpdate(left, by, -by * (left - 1));
	    internalUpdate(right, -by, by * right);
	}
	private void internalUpdate(int at, long mul, long add) {
	    while (at < dataMul.length) {
	        dataMul[at] += mul;
	        dataAdd[at] += add;
	        at |= (at + 1);
	    }
	}
	// returns data[at]
	long rquery(int at) {
		long mul = 0;
		long add = 0;
	    int start = at;
	    while (at >= 0) {
	        mul += dataMul[at];
	        add += dataAdd[at];
	        at = (at & (at + 1)) - 1;
	    }
	    return mul * start + add;
	}
		
}

