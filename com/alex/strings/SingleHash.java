package com.alex.strings;
/**
 * ToDo: TEST
**/
public class SingleHash	{
	final int BASE = 153, MOD = 1000000009;
	int [] suf, b;
	int N;
	SingleHash(String s)	{
		N = s.length();

		b = new int [N + 2];
		suf = new int [N + 2];
		
		b[0] = 1;
		b[1] = BASE;
		
		for (int i = N - 1; i >= 0; i--)	{
            suf[i] = (int)( ( s.charAt(i) + (long)suf[i + 1] * b[1] ) % MOD );
		}
		for (int i = 2; i <= N; i++)	{
            b[i] = (int)( ( (long)b[i - 1] * b[1]) % MOD);
		}
	}
	/**
	 * return hash for substring(l, r)
	 * @return
	**/
	int hash(int l, int r)	{
		long v = suf[l] - (long)suf[r + 1] * b[r - l + 1];
        v %= MOD;
        if	( v < 0 )
        	v += MOD;
        v %= MOD;
        return (int) v;
	}
}
