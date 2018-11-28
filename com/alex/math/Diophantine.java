
/**
 * Class for solving diophantine equations
 *
 * @author: Alexis Hernandez
**/
class Diophantine {

	/**
	 * Solves a diophantine equation with N unknowns
	 * a1*x1 + a2*x2 + ... + aN*xN = result
	 *
	 * @param unknowns has the values for a
	 * @return null if the equation has no solution
	 *         an array with the values for x with a solution
	**/
	static BigInteger[] diophantineSolver(BigInteger[] unknowns, BigInteger result)	{
		int N = unknowns.length;
		BigInteger[] ans = new BigInteger[N];
		if (N == 1) {
			ans[0] = result.divide(unknowns[0]);
			return	ans[0].multiply(unknowns[0]).equals(result) ? ans : null;
		}

		Triple t = egcd(unknowns[0], unknowns[1]);
		ans[0] = t.x;
		ans[1] = t.y;
		for (int k = 2; k < N; k++)	{
			t = egcd( t.g, unknowns[k] );
			ans[k] = t.y;
			for (int m = 0; m < k; m++)
				ans[m] = ans[m].multiply(t.x);
		}

		for (int k = 0; k < N; k++)	{
			ans[k] = ans[k].multiply( result.divide(t.g) );
		}

		for (int k = 0; k < N; k++)
			result = result.subtract( ans[k].multiply(unknowns[k]) );

		return	result.equals(BigInteger.ZERO) ? ans : null;
	}

	static Triple egcd(BigInteger a, BigInteger b) {
		if ( b.equals(BigInteger.ZERO) )
			return new Triple(a, BigInteger.ONE, BigInteger.ZERO);

		Triple q = egcd( b, a.mod(b) );
		return new Triple(
			q.g, q.y,
			q.x.subtract( a.divide(b).multiply(q.y) )
		);
	}

	class Triple {
		BigInteger x, y, g;
		Triple(BigInteger _g, BigInteger _x, BigInteger _y) {
			g = _g;
			x = _x;
			y = _y;
		}
	}
}