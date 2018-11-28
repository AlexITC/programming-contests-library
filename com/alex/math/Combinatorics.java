/**
 * A class with combinatorics formulas and algorithms
 * @author Alexis Hernández
**/

package com.alex.math;

public class Combinatorics {

	/**
	 * Returns true if p has been changed to its next permutation
	**/
	static boolean nextPermutation(int[] p) {
		for (int a = p.length - 2; a >= 0; --a)	{
			if (p[a] < p[a + 1])	{
				for (int b = p.length - 1; ; --b)	{
					if(p[b] > p[a]) {
						swap(p,a,b);
						for (++a, b = p.length - 1; a < b; ++a, --b) {
							swap(p,a,b);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	static void swap(int [] p, int a, int b)	{
		int t = p[a];
		p[a] = p[b];
		p[b] = t;
	}
	
	/**
	 * Binomial Coefficients
	 * 
	 * De cuantas maneras podemos elegir K elementos de un total de N elementos (sin importar el orden)
	 * 
	 * nCk = n! / ( k! * (n-k)! )
	 * 
	 * nCk = nC(n - k)
	 * nCk = (n / k) * (n - 1)C(k - 1)
	 * for (k = 0; k <= n; k++) sum += nCk; -> sum = pow(2, n)
	 * 
	**/
	
	/**
	 * nChooseK % MOD
	 * 
	 * O(maxN * maxK)
	 * tested
	 *  http://coj.uci.cu/24h/problem.xhtml?abb=2257
	**/
	static int [][] nChoose_dp(int maxN, int maxK, int MOD)	{
		int [][] table = new int [maxN + 1][maxK + 1];
		
		table[0][0] = 1;
		for (int n = 1;	n <= maxN; n++)	{
			table[n][0] = 1;
			for (int k = 1; k <= maxK; k++)	{
				table[n][k] = table[n - 1][k - 1] + table[n - 1][k];
				if	( table[n][k] >= MOD )
					table[n][k] -= MOD;
			}
		}
		
		return	table;
	}
	
	/**
	 * Determina el numero de combinaciones de k elementos que se pueden tomar de un total de n.
	 * Complejidad:		O(k)
	 */
	static long nChooseK(int n, int k) {
		if	( k > n )
			return	0;
		if (k > n - k)
			k = n - k;
		long res = 1;
		for (int i = 0; i < k; i++)
			res = res * (n - i) / (i + 1);
		return res;
	}

	static long nChooseK_2(int n, int k) {
		if (k > n - k)
			k = n - k;
		
		
		long numerator, denominator, toMul, toDiv, g;
		numerator = denominator = toDiv = toMul = 1;
		
		long res = 1;
		
		for (int i = k; i > 0; i--)	{
			toMul = n - k + i;
			toDiv = i;

			g = MyMath.GCD(toMul, toDiv);
			toMul /= g;	toDiv /= g;
			
			g = MyMath.GCD(numerator, toDiv);
			numerator /= g;	toDiv /= g;
			
			g = MyMath.GCD(toMul, denominator);
			toMul /= g;	denominator /= g;
			
			numerator *= toMul;
			denominator *= toDiv;
		}
		
		return	numerator / denominator;
	}
	
	/**
	 * Determina el numero de combinaciones con repeticiones de k elementos de un total de n.
	 * Complejidad:		O(k)
	 */
	static long nChooseKRep(int n, int k) {
		return nChooseK(n + k - 1, k);
	}
	
	/**
	 * create a table where table[n][k] store this:
	 *  in how many ways can  I choose k elements from a set of n without selecting two consecutive elements
	 * 
	 * example:
	 * N = 5, K = 3, table[N][K] = 1
	**/
	static int [][] nChooseKnotTwoConsecutives(int maxN, int maxK, final int MOD)	{
		int [][] table = new int [maxN + 1][maxK + 1];
		
		for (int n = 0; n <= maxN; n++)	{
			table[n][0] = 1;
			table[n][1] = n;
		}

		for (int k = 2; k <= maxK; k++)	{
			long sum = 0;
			
			for (int n = 2; n <= maxN; n++)	{
				table[n][k] = (int) sum;
				
				sum += table[n - 1][k - 1];
				if	( sum >= MOD )	sum -= MOD;
			}
		}
		return	table;
	}

}
