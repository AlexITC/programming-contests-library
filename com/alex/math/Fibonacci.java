/**
 * A class about fibonacci sequence
 * @author Alexis Hernández
**/

package com.alex.math;

class Fibonacci	{
	
	/**
	 * fibonacci sum
	 * F(N + 2) - 1 = F(0) + F(1) + ... + F(N)
	 * F means fibonacci
	**/

	/**
	 * Returns N-th number in fibonacci sequence % MOD using matrix power
	 * O(log N)
	**/
	static long fibonacci(long N, int MOD) {
		if	( N == 0 )
			return	0;
		if	( N < 3 )
			return 1 % MOD;
		long m [][] = { {0, 1}, {1, 1} };
		m = pow(m, N, MOD);
		
		return	(m[0][0] + m[1][0]) % MOD;
	}
	// returns m pow N
	static long [][] pow(long [][] m, long N, int MOD)	{
		long [][] ans = { { m[0][0], m[0][1] }, { m[1][0], m[1][1] } };
		N -= 2;
		
		while	( N != 0 )	{
			if	( (N&1) != 0 )	{
				ans = mul(ans, m, MOD);
			}
			N >>= 1;
			m = mul(m, m, MOD);
		}
		return	ans;
	}
	// matrix multiplication
	static long [][] mul(long [][] a, long [][] b, int MOD)	{
		long ans [][] = new long [2][2];
		ans[0][0] = (a[0][0] * b[0][0]) + (a[0][1] * b[1][0]);
		ans[0][1] = (a[0][0] * b[0][1]) + (a[0][1] * b[1][1]);
		ans[1][0] = (a[1][0] * b[0][0]) + (a[1][1] * b[1][0]);
		ans[1][1] = (a[1][0] * b[0][1]) + (a[1][1] * b[1][1]);
		
		for (int i = 0; i < 2; i++)
			for (int k = 0; k < 2; k++)
				ans[i][k] %= MOD;
		
		return	ans;
	}
	

}


