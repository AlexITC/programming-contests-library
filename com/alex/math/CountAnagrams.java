package com.alex.math;

public class CountAnagrams {

	static int MOD = 1000000000 + 7;
	/**
	 * count the number of different anagrams in a string s MOD m
	 * res = N! / R!
	 * where N = len of s
	 * 		 R = product of number of occurrences for each character
	 */
	public static long countAnagrams(String s)	{

		// precompute factorials
		long [] f = new long [1005];
		f[0] = f[1] = 1;
		for (int i = 2; i < f.length; i++)
			f[i] = (i * f[i - 1]) % MOD;
		
		//
		int cnt [] = new int [256];
		int N = s.length();
		for (int i = 0; i < N; i++)
			cnt[ s.charAt(i) ]++;
		
		//
		long R = 1;
		for (int x : cnt)	if	( x != 0 )	{
			R *= f[x];
			R %= MOD;
		}
		
		long ans = ( f[N] * modInverse(R, MOD) );
		ans %= MOD;
		return	ans;
	}

	/**
	 * Returns the modular inverse of x % mod
	 * Be sure that gdc(x, mod) == 1
	 * Returns x^(mod-2) % mod
	**/
	static long modInverse(long x, int mod)	{
		return	modPow( x, mod - 2, mod );
	}
	/**
	 * Returns (x^y) % m
	**/
	static long modPow(long x, int pow, int mod)	{
		long res = 1;
		while	( pow > 0 )	{
			if	( (pow&1) != 0 )	res = (res * x) % mod;
			pow >>= 1;
			x = (x*x) % mod;
		}
		return res;
	}

}
