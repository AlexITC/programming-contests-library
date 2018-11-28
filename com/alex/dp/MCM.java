package com.alex.dp;

public class MCM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N, M;
		N = M = 5;
		int [][] mem = new int [N][M];
		mem[0][0] = 0;
		for (int i = 1; i <= N; i++)	for (int j = 0; j < i; j++)	for (int k = 1; k <= M; k++)	{
			mem[i][k] = Math.min( mem[i][k], mem[j][k - 1] + getCost(j + 1, i ) );
		}
	}
	static int getCost(int L, int R)	{
		return	0;
	}

}
