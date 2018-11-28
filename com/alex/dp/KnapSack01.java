/**
 * Given N items (value, cost) and an amount of money
 * What the maximum value we can get paying a most money
 * 
 * A item would be taken or not, no item could be taken more than 1 time
 * 
 * @author Alexis Hernández
 * O(N * maxMoney)
 * 
**/
package com.alex.dp;

public class KnapSack01 {
	private int N;			// number of items
	private int [] cost;	// how many cost i-th item?
	private int [] value;	// how many value has i-th item?
	private int memo [][];	// dp table [item][money] = value
	//
	public KnapSack01(int cost [], int value [], int maxMoney)	{
		N = value.length;
		this.cost = cost;
		this.value = value;
		
		memo = new int [N][maxMoney + 1];
	}
	// get maximum value using a most money
	public int get(int money)	{
		return	get(0, money);
	}
	private int get(int id, int money)	{
		if	( money == 0 || id == N )
			return	0;
		
		int ans = memo[id][money];
		if	( ans == -1 )	{
			ans = 0;
			if	( money < cost[id] )
				ans = get(id + 1, money);
			else
				ans = Math.max( get(id + 1, money), value[id] + get(id + 1, money - cost[id]) );
			
			memo[id][money] = ans;
		}
		return	ans;
	}

}
