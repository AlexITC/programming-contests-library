/**
 * Count the numbers of inversions in an array using Fenwick Tree
 * 
 * O(N logN)
 * @author kali
 *
**/


public class InversionsCount {

	static long inversions(int [] a)	{
		long ans = 0;
		
		create(a.length);
		for (int x : a)	{
			add(x + 1, -1);
			ans += count(x);
		}
		
		return	ans;
	}
	static int [] bit;
	static void create(int N)	{
		bit = new int [N + 1];
		for (int i = 1; i <= N; i++)
			add(i, 1);
	}
	
	static void add(int id, int val)	{
		while	( id < bit.length )	{
			bit[id] += val;
			id += Integer.lowestOneBit(id);
		}
	}
	static int count(int id)	{
		int ans = 0;
		while	( id != 0 )	{
			ans += bit[id];
			id -= Integer.lowestOneBit(id);
		}
		return	ans;
	}

}
