/**
 * Highly composite numbers
**/
package com.alex.math;


public class HCN {

	static final int INF = 100000000;
	// returns the HCN N with more number of divisors, in case of a tie, N would be as less as possible, N <= bound
	Pair largestHighlyCompositeNumber(long bound)	{
		return rec( bound, 0, 1, INF );
	}

	// the first 13 prime numbers are enough for computing a HCN upto 10e16
	final static int [] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71 };
	
	/**
	 * bound = the upper bound for the HCN
	 * primeIndex = what prime number are we using
	 * prod = current product
	 * maxPow = the maximum power of primes[primeIndex] that we can take
	 * 
	 * returns	( N, number of divisors )
	**/
	Pair rec(long bound, int primeIndex, long prod, int maxPow)	{
		long bestNumber = prod;
		long bestDivisors = 1;
		long divisors;
		prod *= primes[primeIndex];
		
		for( int pow = 1; pow <= maxPow && prod <= bound; pow++, prod *= primes[primeIndex] )	{
			Pair next = rec( bound, primeIndex + 1, prod, pow );
			
			divisors = (pow + 1) * next.divisors;
			if	( divisors > bestDivisors ||
				( divisors == bestDivisors && next.number < bestNumber )	// compute the smallest number with greater number of divisors
			)	{
				bestNumber = next.number;
				bestDivisors = divisors;
			}
		}
		
		return	new Pair( bestNumber, bestDivisors );
	}
	
	class Pair	{
		long number;			// value
		long divisors;	// number of divisors for value
		Pair(long N, long d)	{
			number = N;
			divisors = d;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
