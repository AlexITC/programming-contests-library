package com.alex.math;

class SegmentedSieve	{
	int [] primes;
	int len;
	SegmentedSieve(int MAX) {
		int TOP = (int)(Math.sqrt(MAX) + 1);
		
		len = 0;
		primes = new int [TOP];
		primes[len++] = 2;
		
		for (int k = 3; k <= TOP; k+=2)	{
			boolean isPrime = true;
			int LIMIT = (int)(Math.sqrt(k) + 1);
			for (int m = 0; isPrime && m < len && primes[m] <= LIMIT; m++)
				isPrime &= k % primes[m] != 0;
			
			if	( isPrime )
				primes[len++] = k;
		}
	}
	boolean [] find(long L, long R)	{
		if	( L < 2 )	L = 2;
		
		int sz = (int)(R - L + 1);
		boolean [] isPrime = new boolean [sz];
        Arrays.fill(isPrime, true);
		
		for (int k = 0; k < len; k++)	{
			int p = primes[k];
			
			long s = 0;
			if	( p < L )
				s = L + (( p - (L%p) ) % p);
			else
				s = p << 1;
			
			for (; s <= R; s += p)
				isPrime[(int)(s - L)] = false;
		}
		return	isPrime;
	}
}
