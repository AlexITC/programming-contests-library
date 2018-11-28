package com.alex.dp;

import java.util.*;

public class LongestCommonSubsequence {

	/**
	 * Returns the size Longest Common Subsequence between a and b
	 * for a better perfonmance, SIZEOF(a) should be greater than size of SIZEOF(b)
	 * O( SIZEOF(a) * SIZEOF(b) )
	**/
	static int longestCommonSubsequenceLength(String a, String b)	{
		int N = a.length();
		int M = b.length();
		int [] prev = new int [ M + 1 ];
		int [] v = new int [ prev.length ];
		int i, j;
		for (i = 0; i < N; i++)	{
			for (j = 0; j < M; j++)	{
				v[j + 1] = 0;
				if	( a.charAt(i) == b.charAt(j) )	{
					v[j + 1] = 1 + prev[j];
				}
				else	{
					v[j + 1] = Math.max( v[j], prev[j + 1] );
				}
			}
			int [] aux = prev;
			prev = v;
			v = aux;
			v[0] = 0;
		}
		return	prev[M];
	}
	
	/**
	 * length of the longest common subsequence between a and b
	 * a and b contains the same elements
	 * all elements are distinct
	 * 0 <= a[i] < N
	 * 
	 * O(N logN)
	**/
	static int LCSDistinct(int [] a, int [] b)	{
		int N = a.length;

		int [] invB = new int [N];
		for (int i = 0; i < N; i++)	{
			invB[ b[i] ] = i;
		}
		
		for (int i = 0; i < N; i++)	{
			b[i] = invB[ a[i] ];
		}
		
		return	LongestIncreasingSubsequence.LIS(b);
	}
	
	/**
	 * returns length of the longest common subsequence between s and t
	 * use it for a short alphabeth to get better performance
	 * 
	 * O(N logN)
	**/
	static int LCSFast(String s, String t)	{
		int N = s.length();
		int M = t.length();
		
		final int ALPHABETH = 255;	// how many chars are allowed?
		
		ArrayList<Integer> [] list = new ArrayList[ALPHABETH];	// list[i] = indexes in reverse order where char i is present on string t
		for (int i = 0; i < ALPHABETH; i++)	list[i] = new ArrayList<Integer>();
		// fill list
		for (int i = M - 1; i >= 0; i--)
			list[ t.charAt(i) ].add(i);
		
		//
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < N; i++)	{
			arr.addAll( list[ s.charAt(i) ] );
		}
		
		
		if	( arr.isEmpty() )	return	0;	// no common subsequence
		
		N = arr.size();
		int [] v = new int [N];
		for (int i = 0; i < N; i++)
			v[i] = arr.get(i);
		
		return	LongestIncreasingSubsequence.LIS(v);
	}
	
}







