/**
 * Prime numbers algorithms and some facts
 * 
 * @author Alexis Hernández
**/
package com.alex.math;

import java.util.BitSet;

public class Primes {
	/**
	 * Sieve of Eratosthenes with linear time work 
	 * Calcula los numeros primos menores o iguales a N.
	 * Complejidad:		~ O(N), max(N) = 10**7 for a good perfonmance
	 * @param N			Tope del calculo de primos.
	 * @param primes	Arreglo donde guardar los primos, primes.lenght >= N / log N .
	 * @return			Numero de primos encontrados <= N.
	 */
	static int primes(int N, int[] primes) {
		int [] lp = new int [N + 1];
		int p = 0;
		for (int i = 2; i <= N; i++)	{
			if ( lp[i] == 0 )	{
				lp[i] = i;
				primes[p++] = i;
			}
			for (int j = 0; j < p && primes[j] <= lp[i] && i * primes[j] <= N; j++)
				lp[ i * primes[j] ] = primes[j];
		}
		return p;
	}

	/**
	 * Sieve of Eratosthenes
	 * 
	 * O(N logN)
	 * 
	**/
	static BitSet sieve(int N)	{
		N++;
		BitSet nonPrime = new BitSet(N);
		
		nonPrime.set(0);
		nonPrime.set(1);
		
		for (int i = 2; i * i < N; i++)	if	( !nonPrime.get(i) )
			for (int k = i * i; k < N; k += i )
				nonPrime.set(k);
		
		//	count primes
		int cnt = 0;
		for (int i = 0; i < N; i++)	{
			cnt += !nonPrime.get(i) ? 1 : 0;
		}
		
		return	nonPrime;
	}
}
