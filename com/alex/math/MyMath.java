/**
 * Math utils for contests
 *
 * @author Alexis Hernández
**/

package com.alex.math;

import java.awt.*;
import java.awt.geom.*;
import java.text.*;
import java.util.*;

public final class MyMath	{
	/**
	 * Subset xor sum
	 * We have to find the sum of all subsets in which elements of each subset are XORed together
	 * 
	 * Find OR of all the elements and multiply it with 2^(n-1) where n is the total number of elements.
	 * This gives us the answer.
	 * 
	 */
	/**
	 * X * Y % M
	 * Y = M / GCD(X, M)
	**/
	/**
	 * format x to a value with two decimal places
	**/
	public static String format(double x)	{
		DecimalFormat format = new DecimalFormat("0.00");
		return	format.format(x);
	}
	/**
	 * Returns true if x represents a power of two.
	**/
	public static boolean isPowerOfTwo(int x) {
		return x > 0 & (x & (x - 1)) == 0;
	}
	/**
	 * Returns logb(n)
	**/
	public static int logb(int n, int b)	{
		return (int)( 1 + Math.log10(n) / Math.log10(b) );
	}
	/**
	 * Returns (1^2 + 2^2 + ... + N^2)
	**/
	public static long sumOfSquares(int N)	{
		long res = N * (1 + N) * ((N << 1) + 1);
		return	res / 6;
	}
	
	/**
	 * (x^(i+1)-1)/(x-1)
	 * 1 + x + x^2 + ... + x^upPow
	**/
	public static long sumOfPows(int x, int upPow)	{
		return	(long) (( Math.pow(x, 1 + upPow) - 1 ) / (x - 1));
	}
	// returns sumOfPows(x, upPow) % MODULE
	public static long sumOfPowsMod(int x, int upPow, int MODULE)	{
		if	( x == 1 )	return	(1 + upPow) % MODULE;
		long aux = modPow(x, 1 + upPow, MODULE) + MODULE - 1;
		return	(long) (
			( ( aux ) % MODULE ) *
			( modInverse(x - 1, MODULE) % MODULE )
		) % MODULE;
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
	/**
	 * modPow for big numbers
	 */
	static long modPow(String a, String b, int MOD)	{
		long x = 0; // a % MOD
		for (int i = 0; i < a.length(); i++)	{
			x *= 10;
			x += a.charAt(i) - '0';
			x %= MOD;
		}
		
		long ans = 1;
		for (int i = b.length() - 1; i >= 0; i--)	{
			ans = modPow(x, b.charAt(i) - '0', MOD) * ans;
			ans %= MOD;
			x = modPow(x, 10, MOD);
		}
		
		return	ans;
	}
	/**
	 * Returns the modular inverse of x % mod
	 * Be sure that gdc(x, mod) == 1
	 * Returns x^(mod-2) % mod
	**/
	static long modInverse(int x, int mod)	{
		return	modPow( x, mod - 2, mod );
	}

	/**
	 * Calcula el maximo comun divisor.
	 * Complejidad:		O(lg a)
	 * @param a			Entero a.
	 * @param b			Entero b.
	 * @return			Maximo comun divisor de a y b.
	 */
	public static long GCD(long a, long b) {
		long t;
		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
	public static int fastGCD(int a, int b, int res) {
		if	( a == b )
			return	a * res;
		
		boolean c = (a&1) == 0;
		boolean d = (b&1) == 0;
		
		if	( c && d )
			return	fastGCD( a >> 1, b >> 1, res << 1 );
		
		if	( c )
			return	fastGCD( a >> 1, b, res );
		
		if	( d )
			return	fastGCD( a, b >> 1, res );
		
		return	a > b ? fastGCD( a - b, b, res ) : fastGCD( a, b - a, res );
	}
	
	/**
	 * Calcula el minimo comun multiplo entre dos numeros.
	 * Complejidad:		O(lg a)
	 * @param a			Entero a.
	 * @param b			Entero b.
	 * @return			Minimo comun multiplo de a y b.
	 * @see				gcd(a, b)
	 */
	static long LCM(long a, long b) {
		return a * b / GCD(a, b);
	}


	/**
	 * Given a matrix dimension (dim), dim.x is number of rows
	 * Given a Point (p)
	 * Rotate 90 degrees in clockwise
	 * dim and p will be updated
	 * times mean how many times matrix will be rotated
	 * @param dim
	 * @param p
	 * @param times
	 */
	static void rotateMatrix(Point dim, Point p, int times)	{
		if	( times == 0 )
			return;
		p.setLocation( p.y, dim.x - p.x + 1 );
		dim.setLocation( dim.y, dim.x );
		rotateMatrix( dim, p, times - 1 );
	}

	// Heron's (or Hero's) formula, named after Hero of Alexandria,[1] states that the area T of a triangle whose sides have lengths a, b, and c
	static double areaTriangle(int a, int b, int c)	{
		double s = (a + b + c)/  2.0;
		return	Math.sqrt( s * (s - a)*(s - b)*(s - c) );
	}


	/**
	 * given a regular polygon, returns its area
	 * O(N)
	 */
	double area2D(Point vertices []) {
		double area = 0;
		int N = vertices.length;
		for (int i = 1, j = 2, k = 0; i <= N; i++, j++, k++)
			area += vertices[i % N].x * (vertices[j % N].y - vertices[k].y);
		return Math.abs(area / 2.0);
	}
	/**
	 * n! % p
	 * p should be prime
	 * O(P * logN)
	 */
	int factorial(int n, int p) {
		int res = 1;
		while (n > 1) {
			res = (res * ((n / p) % 2 == 1 ? p - 1 : 1)) % p;
			for (int i = 2; i <= n % p; ++i)
				res = (res * i) % p;
			n /= p;
		}
		return res % p;
	}
	
	
	/**
	 * transform an integer to its roman representation
	 */
	static String units [] = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
	static String decs [] =  { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
	static String cents [] = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
	static String mills [] = { "", "M", "MM", "MMM", "MMMM", "MMMMM" };
	//given n, return its representation in roman
	static String int2Roman(int n)	{
		if	( n < 10 )	return	units[n];
		if	( n < 100 )	return	decs[ n / 10 ] + int2Roman(n % 10);
		if	( n < 1000 )	return	cents[ n / 100 ] + int2Roman(n % 100);
		return	mills[ n / 1000 ] + int2Roman(n % 1000);
	}

	
	/**
	 * Spiral matrix
	 * returns a matrix with N rows and M columns filled like an spiral (1 to N*M)
	**/
	static int [][] getSpiralMatrix(int N, int M)	{
		int [][] m = new int [N][M];

		int [] dr = { +0, +1, +0, -1 };
		int [] dc = { +1, +0, -1, +0 };
		int orientation = 0;
		
		int r = 0;
		int c = 0;
		int id = 1;
		m[r][c] = id++;
		
		while	( id <= N*M )	{
			
			
			for (int i = 0; i < dr.length; i++)	{
				int x = orientation + i;
				x %= 4;
				
				int rr = r + dr[x];
				int cc = c + dc[x];
				
				if	( rr < 0 || cc < 0 || rr >= N || cc >= M || m[rr][cc] != 0 )
					continue;
				
				r = rr;
				c = cc;
				
				m[r][c] = id++;
				orientation = x;
				break;
			}
			
		}
		
		return	m;
	}
	/**
	 * given a set of sticks, how many triangles can we form using 3 different sticks?
	 * O(N^2)
	**/
	static int countTriangles(int [] sticks)	{
		int ans = 0;
		int N = sticks.length;
		Arrays.sort(sticks);
		for	(int a = 0; a < N; a++)	{
			int c = a + 2;
			for (int b = a + 1; b < N; b++)	{
				while	( c < N && sticks[a] + sticks[b] > sticks[c] )
					c++;
				
				ans += c - b - 1;
			}
		}
		return	ans;
	}
	

	/**
	 * Compute the number of divisors of N
	 * O( sqrt(N) )
	**/
	static long numberOfDivisors(long N)	{
		long cnt = 0;
		for (long x = 1; x * x <= N; x++)	if	( N % x == 0 )
			cnt += x * x == N ? 1 : 2;
		return	cnt;
	}


	/**
	 * find if x have N-th exact root ( x ^ (1 / N) )
	 * returns its root or null is N-th root is not exact
	 * O( N )
	**/
	static Long nthRoot(long x, int N)	{
		if	( N == 1 )
			return	x;
		
		long ans = (long)(Math.pow( (double)x, 1.0 / N) + 1e-3);
		for (int i = 0; i < N; i++)	{
			if	( x % ans != 0 )
				return	null;
			x /= ans;
		}
		return	x == 1 ? ans : null;
	}
	
	/**
	 * Determina el numero de factores k que tiene el factorial de n (n!).
	 * Complejidad:		~ O(lg n)
	 * @param n	El factorial de este numero es el que se factorizara.
	 * @param k	Es el unico factor que se busca en n!
	 * @return	El maximo valor de a tal que [ n! % k^a = 0 ]
	 */
	static int factorialFactorsN(int n, int k) { // Numero de factores k de n!
		int w = 0;
		do
			w += n /= k;
		while (n > 0);
		return w;
	}
	/**
	 * Calcula el numero de divisores que hay para cada valor en el rango [1, x].
	 * Complejidad:		O(n^0.5)
	 * @param x			Tope del rango.
	 * @return			Sumatoria de numOfDivisors(i) para 0 < i < x+1 .
	 */
	static long sumOfNumOfDivisorsTo(long x) {
		if (x == 0)
			return 0;
		long d = 1,ans = 0;
		while (d*d <= x) {
			ans += (x - d*d) / d*2 + 1;
			d++;
		}
		return ans;
	}
	
	/**
	 * Compute the euler phi function for every element from 1 to N
	 * O(N logN)
	**/
	int [] eulerPhiSieve(int N)	{
		int [] phi = new int [N + 1];
		phi[1] = 1;
		for (int p = 2; p <= N; p++)	if	( phi[p] == 0 )	{
			phi[p] = p - 1;
			for (int k = p+p; k <= N; k+=p)	{
				if	( phi[k] == 0 )	phi[k] = k;
				phi[k] = phi[k] / p * (p - 1);
			}
		}
		return	phi;
	}
	
	/**
	 * Compute LCMSUM(x) for 1 <= x <= N
	 * LCMSUM(N) = lcm(1, N) + lcm(2, N) + ... + lcm(N, N)
	**/
	long[] lcmSumSieve(int N)	{
		int [] phi = eulerPhiSieve(N);
		long [] lcmsum = new long [N + 1];
		for (int k = 1; k <= N; k++)	for (int m = k; m <= N; m += k)
			lcmsum[m] += k * 1L * phi[k];
		
		for (int k = 1; k <= N; k++)	{
			lcmsum[k] = (lcmsum[k] + 1) * k;
			lcmsum[k] >>= 1;
		}
		return	lcmsum;
	}
	
	/**
	 * sieve[k] = the sum of the divisors of k
	**/
	long [] sumOfDivisorsSieve(int N)	{
		long [] sum = new long [N + 1];
		for (int p = 2; p <= N; p++)	{
			sum[p]++;
			for (int m = p+p; m <= N; m += p)	sum[m] += p;
		}
		return	sum;
	}
	
	/**
	 * Fermat's theorem on sums of two squares
	 *
	 * Returns true if n = x*x + y*y
	**/
	boolean isSumOfTwoSquares(long n)	{
		while	( (n&1) == 0 )	n >>= 1;
		
		long p = 3;
		while	( p*p <= n )	{
			int cnt = 0;
			while	( n % p == 0 )	{
				cnt++;	n /= p;
			}
			
			if	( (p&3) == 3 && (cnt&1) == 1 )
				return	false;
			
			p += 2;
		}
		return	(n&3) != 3;
	}
	
	/**
	 * The number of divisors of x is even if x is a perfect square
	**/
	boolean hasEvenNumberOfDivisors(long x)	{
		long root = (long)Math.sqrt(x);
		return	root*root == x;
	}
	
	// the first index with value >= key
	int lower(ArrayList<Long> list, long key)	{
		int lo = 0, hi = list.size() - 1;
		int ans = -1;
		while	( lo <= hi )	{
			int mid = (lo + hi) >> 1;
			if	( list.get(mid) >= key )	{
				ans = mid;	hi = mid - 1;
			}	else	lo = mid + 1;
		}
		return	ans;
	}
	
	// the last index with value <= key
	int upper(ArrayList<Long> list, long key)	{
		int lo = 0, hi = list.size() - 1;
		int ans = -1;
		while	( lo <= hi )	{
			int mid = (lo + hi) >> 1;
			if	( list.get(mid) <= key )	{
				ans = mid;	lo = mid + 1;
			}	else	hi = mid - 1;
		}
		return	ans;
	}

	/**
	 * The list of unique prime factor of N
	**/
    static long [] uniqueFactors(long N) {
        ArrayList<Long> list = new ArrayList<Long>();

        if (N%2 == 0) {
            list.add(2L);
            while   (N%2 == 0)  N >>= 1;
        }

        long p = 3;
        while   (p*p <= N)  {
            if (N % p == 0) {
                list.add(p);
                while   ( N%p == 0 )    N /= p;
            }
            p += 2;
        }
        if (N != 1) {
            list.add(N);
        }

        long [] ans = new long [list.size()];
        for (int k = 0; k < list.size(); k++)   ans[k] = list.get(k);
        return  ans;
    }

	/**
	 * Euler phi function
	**/
    static long phi(long N)   {
        long[] f = uniqueFactors(N);
        for (int i = 0; i < f.length; i++) {
                N /= f[i];
                N *= f[i] - 1;
            }
        return N;
    }
}

