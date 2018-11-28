package com.alex.dp;

public class SubsetSum {

	/**
	 * given an array of coins and an amount money
	 * returns if we can take a subset of coins whose sum is exactly money
	 * a coin could be taken a most one time
	 * O( coins * money )
	**/
	boolean canSum(int [] coins, int money)	{
		boolean dp [] = new boolean [money + 1];
		dp[0] = true;

		for (int i = 0; i < coins.length; i++)	for (int k = money; k >= coins[i]; k--)
				dp[k] |= dp[ k - coins[i] ];
		
		return	dp[money];
	}

	/**
	 * given an array of coins and an amount money
	 * returns if we can take a subset of coins whose sum is exactly money
	 * a coin could be taken as many times as needed
	 * O( coins * money )
	**/
	boolean canSum2(int [] coins, int money)	{
		boolean dp [] = new boolean [money + 1];
		dp[0] = true;

		for (int i = 0; i < coins.length; i++)	for (int k = coins[i]; k <= money; k++)
				dp[k] |= dp[ k - coins[i] ];
		
		return	dp[money];
	}

}
