package com.alex.math;

public class TernarySearch {

	// ternary search for real values
	static double ternarySearch(double start, double end)	{
		//	while	( end - start > 1e-9)	// is better not to use this
		for (int i = 0; i < 100; i++)	{
			double m1 = start + (end - start) / 3;
			double m2 = end - (end - start) / 3;
			
			if	( f(m1) <= f(m2) )	{
				end = m2;
			}	else	{
				start = m1;
			}
		}
		
		return	start;
	}

	// our funcion f(x)
	static double f(double x)	{
		return	0;
	}

	// ternary search for int values (binary search trick)
	static int ternarySearchInt(int lo, int hi)	{
		while( lo < hi ) {
		    int mid = (lo + hi) >> 1;
		    if( f(mid) > f(mid+1) ) {
		        hi = mid;
		    }
		    else {
		        lo = mid+1;
		    }
		}
		return	lo;
	}
	
}
