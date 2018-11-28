/**
 * A class for computin Z function and some applications
 * 
 * Z[i] means longest common prefix betwees S and S.substring(i)
 * 
 * @author Alexis Hernández
**/
package com.alex.strings;

public class ZFunction	{

	/**
	 * returns the Z array of String s
	 * O(N)
	 */
	static int [] Z(String s)	{
		int N = s.length();
		int L, R;
		int Z [] = new int [N];
		
		L = R = 0;
		for (int i = 1; i < N; i++)	{
			
			if	( i > R )	{
				L = R = i;
				while	( R < N && s.charAt(R - L) == s.charAt(R) )
					R++;
				Z[i] = R-- - L;
				continue;
			}
			
			int k = i - L;
			if	( Z[k] < R - i + 1 )	{
				Z[i] = Z[k];
				continue;
			}
			
			L = i;
			while	( R < N && s.charAt(R - L) == s.charAt(R) )
				R++;
			Z[i] = R-- - L;
			
		}
		return	Z;
	}
	/**
	 * Count how many times appears t as a substring of s
	 * O(N + M)
	**/
	static int countOcurrences(String s, String t)	{
		int Z [] = Z( t + s );
		int ans = 0;
		for (int i = t.length(); i < Z.length; i++)
			ans += Z[i] >= t.length() ? 1 : 0;
		return	ans;
	}
	
	/**
	 * Compute the length of the longest prefix that is also a suffix and substring
	 * O(N)
	**/
	static int longestPrefixSuffixSubstring(String s)	{

		int Z [] = Z( s );
		
		int N = Z.length;
		
		int max = 0;
		int len = 0;
		for (int i = 1; i < N; i++)	{
			if	( N - i == Z[i] && max >= N - i )	{
				// suffix and substring
				len = N - i;
				break;
			}
			max = Math.max(max, Z[i] );
		}
		return	len;
	}
	
	/**
	 * find k such that s could be represented as s.substring(0, k) concatenated k times
	**/
	static int rowCompression(String s)	{
		int Z [] = Z( s );
		
		int N = Z.length;
		
		int k = 0;
		for (int i = 0; i < N; i++)	{
			if	( i + Z[i] == N	&& N % i == 0 )	{
				k = i;
				break;
			}
		}
		return	k;
	}
}
