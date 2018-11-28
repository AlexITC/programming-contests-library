package com.alex.strings;
/**
 * String utils for contets
 * @author Alexis Hernández
 * updated 28-10-2013
**/
import java.util.*;

class Strings	{
	/**
	 * Returns @true if the string 'A' could be converted to string 'B' deleting zero or more characters
	 * O( b.length )
	**/
	static boolean bIsSubsequenceOfA(String a, String b)	{
		int j = 0, i = -1;
		int found = 0;
		while	( i < a.length()	&&	j < b.length() )	{
			i = a.indexOf( b.charAt(j++) , i + 1);
			if	( i < 0 )	break;
			found++;
		}
		return	found == b.length();
	}


	/**
	 * Returns the lenght of longest non-decreasing contiguous subsequence in the given array
	 * O(N)
	**/
	public static int longestNonDecreasingContiguousSubsequence(int [] v)	{
		int dp [] = new int [ v.length ];
		dp[0] = 1;
		int max = 1;
		for (int i = 1; i < v.length; i++)	{
			dp[i] = 1;
			if	( v[i-1] <= v[i] )	dp[i] += dp[i-1];
			max = Math.max(max, dp[i]);
		}
		return max;
	}
	/**
	 * Returns the length of longest non-decreasing non-contiguous subsequence in the given array}
	 * O(N**2)
	**/
	public static int longestNonDecreasingSubsequence(int [] v)	{
		int next [] = new int [ v.length ];
		int count [] = new int [ v.length ];
		count[ count.length - 1 ] = 1;
		Arrays.fill(next, -1);
		int max = 1;
		int indexStart = 0;
		for (int i = count.length - 2; i >= 0; i--)	{
			count[i] = 1;
			for (int j = count.length - 1; j > i; j--)	{
				if	( v[i] <= v[j]	&&	count[i] <= count[j] )	{
					count[i] = 1 + count[j];
					next[i] = j;
				}
			}
			if	( count[i] > max )	{
				max = count[i];
				indexStart = i;
			}
		}
		return max;
	}


	/**
	 * count how many times string b appear as a subsequence in string a
	 * O( N * M )
	**/
	static int countDistinctSubsequences(String a, String b)	{
		int M = a.length();	// long string
		int N = b.length();
		if	( M < N )	return	0;
		int [] prev = new int [M + 1];
		int [] v = new int [ prev.length ];
		int i, j;
		int beginJ = M;
		for (j = 0; j < M; j++)	{
			prev[j + 1] = prev[j];
			if	( a.charAt(j) == b.charAt(0) )	{
				prev[j + 1]++;
				beginJ = Math.min(beginJ, j);
			}
		}
		for (i = 1; i < N && beginJ < M; i++, beginJ++)	{
			v[beginJ] = 0;
			for (j = beginJ; j < M; j++)	{
				v[j + 1] = v[j];
				if	( a.charAt(j) == b.charAt(i) )	{
					v[j + 1] += prev[j];
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
	 * count how many times string b appear as a subsequence in string a
	 * 
	 * FASTER
	 * 
	 * O( N * M )
	**/
	static int numDistinct(String S, String T) {  
		int si = S.length(), ti = T.length();   
		if ( si<=0 || ti<=0 || si<ti ) return 0;   
		
		int[] recurs = new int[ti +1];   
		for (int i=0; i<si; ++i) {   
			for (int j=Math.min(i, ti-1); j>=0; --j) {   
				if (S.charAt(i)==T.charAt(j)) {   
					recurs[j+1] += (j==0) ? 1 : recurs[j];  
					recurs[j+1] %= 1000000007;
				}   
			}   
		}   
		return recurs[ti];       
	} 
	
	/**
	 * TEST
	 * Returns the Longest Common Subsequence between a and b
	 * for a better perfonmance, SIZEOF(a) should be greater than size of SIZEOF(b)
	 * O(SIZEOF(a)*SIZEOF(b) + SIZEOF(b))
	**/
	static String LCS(String a, String b)	{
		StringBuilder res = new StringBuilder();
		int N = a.length();
		int M = b.length();
		int dp [][] = new int [N + 1][M + 1];
		int i, j;
		for (i = 0; i < N; i++)	{
			for (j = 0; j < M; j++)	{
				if	( a.charAt(i) == b.charAt(j) )	dp[i + 1][j + 1] = 1 + dp[i][j];
				else	dp[i + 1][j + 1] = Math.max( dp[i + 1][j], dp[i][j + 1] );
			}
		}
		// dp[N][M] has the lenght of Longest Common Subsequence between a and b		
		// recover LCS
		j = 1;
		while	( j <= M )	{
			if	( dp[N][j] > dp[N][j - 1] )	res.append( b.charAt(j - 1) );
			j++;
		}
		return	res.toString();
	}
	

	/**
	 * Given a string s, returns its minimum lexicographic rotation
	 * 
	 * O(N)
	**/
	static int minimumRotation(String s)	{
		
		int N = s.length();
		int ans = 0, p = 1, len = 0;
		
		while	( p < N && ans + len + 1 < N )	{
			char ch = s.charAt(ans + len);
			char ch2 = s.charAt( (p + len) % N );
			if	( ch == ch2 )
				len++;
			
			else if	( ch < ch2 )	{
				p += len + 1;
				len = 0;
			}	else	{
				ans = Math.max( ans + len + 1, p );
				p = ans + 1;
				len = 0;
			}
		}
	    return ans;
	}
	

}
