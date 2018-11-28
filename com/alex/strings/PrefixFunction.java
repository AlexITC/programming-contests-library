/**
 * A class for computin the prefix function and some applications
 * 
 * @author Alexis Hernández
**/
package com.alex.strings;

public class PrefixFunction {

	/**
	 * build prefix function
	 * O(N)
	**/
	static int [] prefixFunction(String s)	{
		int N = s.length();
		int [] p = new int [N];
		int k = 0;
		
		for (int i = 1; i < N; i++)	{
			while	( k > 0 && s.charAt(k) != s.charAt(i) )
				k = p[k - 1];
			
			if	( s.charAt(i) == s.charAt(k) )
				++k;
			
			p[i] = k;
		}
		return	p;
	}
	/**
	 * count how many times pat appears in string s
	 * O(N + M)
	**/
	static int kmpCountMatches(String s, String pat)	{
		int N = s.length();
		int M = pat.length();
		int ans = 0;
		
		int [] p = prefixFunction(pat);
		for (int i = 0, k = 0; i < N; i++)	{
			while	( k > 0 && pat.charAt(k) != s.charAt(i) )
				k = p[k - 1];

			k += pat.charAt(k) == s.charAt(i) ? 1 : 0;
			if	( k == M )	{
			//	System.out.println( "occur at: " + i - M + 1 );
				ans++;
				k = p[k - 1];
			}
		}
		return	ans;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "ababababababa";
		String pat = "abc";

		System.out.println( kmpCountMatches(s, pat) );
	}

}
