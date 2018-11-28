/*
 You have N lights and M queries
  Each query:
  0 a b = switch all lights in the interval [a, b]
  1 a b = how many lights are turned on in the interval [a, b]
  
  1 <= a, b <= N
  
 Complexity: O(log N) per query
*/

package com.alex.collection;

import java.io.*;
import java.util.*;

public class LightSwitching {
	static BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
	static StringTokenizer st = new StringTokenizer("");
	static String next()	throws Exception	{
		
		while	( !st.hasMoreTokens() )	{
			String s = br.readLine();
			if	( s == null )	return	null;
			st = new StringTokenizer( s );
		}
		return	st.nextToken();
	}
	
	public static void main(String [] asda)	throws Exception	{
		PrintWriter out = new PrintWriter( new BufferedOutputStream(System.out) );
		//

		int N = Integer.parseInt( next() );
		int M = Integer.parseInt( next() );
		LazyBinarySegmentTree lazy = new LazyBinarySegmentTree(N);
		while	( M-- > 0 )	{
			String cmd = next();
			int a = Integer.parseInt( next() );
			int b = Integer.parseInt( next() );
			if ( cmd.equals("0") )	{
				lazy.flip(a, b);
			}
			else	{
				out.println(  lazy.getFlipped(a, b) );
			}
		}
		//
		out.flush();
	}


static class LazyBinarySegmentTree {
	int [] ones, twos, flip;
	int size;
	
	
	public LazyBinarySegmentTree(int size) {
		
		int N = 1;
		while ( (N <<= 1) < size);
		N <<= 1;

		ones = new int[N];
		twos = new int[N];
		flip = new int[N];
		
		this.size = size;
	}
	// x and y sould start in 1
	void flip(int x, int y)	{
		update( 1, size, x, y, 1 );
	}
	
	int getFlipped(int x, int y)	{
		// lasta parameter is for the initial status of each element in array (0, 1)
		return	query( 1, size, x, y, 1, 1 );
	}
	
	
	private void update(int lo, int hi, int x, int y, int i) {
		if (x > hi || y < lo)
			return;
		
		int mid;
		if (x <= lo && y >= hi) {
			ones[i] = (hi - lo + 1) - ones[i];
			flip[i] += 1;
			if (flip[i] > 1)
				flip[i] -= 2;
			return;
		}
		
		mid = (lo + hi) >> 1;
		update(lo, mid, x, y, 2 * i);
		update(mid + 1, hi, x, y, 2 * i + 1);
		
		ones[i] = ones[2 * i] + ones[2 * i + 1];
	
		if (flip[i] == 1) {
			ones[i] = (hi - lo + 1) - ones[i];
		}
	}
	

	private int query(int lo, int hi, int x, int y, int i, int flag) {
		if (x > hi || y < lo)
			return 0;
		
		if (x <= lo && y >= hi) {
			if (flag == 1)
				return ones[i];
			
			return (hi - lo + 1) - ones[i];
		}
		
		
		int mid = (lo + hi) >> 1, nflag = (flag + flip[i]);
		
		if (nflag > 1)
			nflag -= 2;
		
		return	query(lo, mid, x, y, 2 * i, nflag)	+
				query(mid + 1, hi, x, y, 2 * i + 1, nflag);
	}
	
}


	
}
