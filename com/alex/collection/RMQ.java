package com.alex.collection;

import java.util.*;


public class RMQ {
	
	int [] data;
	int [][] table;
	
	int N, logN;
	
	public RMQ(int [] data)	{
		this.data = data;
		N = data.length;
		
		int size = 1;
		logN = 1;
		while	( (size <<= 1) < N )	logN++;
		
		System.out.println( N + " " + logN );
		
		table = new int [N][logN + 1];
		for (int i = 0; i < N; i++)
			table[i][0] = i;
		
		for (int k = 1; (1 << k) <= N; k++)	for	(int i = 0; i + (1 << k) - 1 < N;	i++)	{
			
			if	( data[ table[i][k - 1] ] < data[ table[ i + (1 << (k - 1)) ][ k - 1 ] ] )
				table[i][k] = table[i][k - 1];
			
			else
				table[i][k] = table[ i + ( 1 << (k - 1) ) ][k - 1];
			
		}
		
	}
	
	public int getMin(int l, int r)	{
		
		int k = (int)( Math.log10(r - l + 1) / Math.log10(2) );
		
		if	( data[ table[l][k] ] <= data[ table[r - (1 << k) + 1][k] ] )
			return	table[l][k];
		
		return	table[r - (1 << k) + 1][k];
	}
	
	public static void main(String [] asds)	{
		
		int [] v = new int [100];
		Random RNG = new Random();
		for (int i = 0; i < v.length; i++)
			v[i] = RNG.nextInt();
		
		RMQ rmq = new RMQ(v);
		
		int N = v.length;
		for (int l = 0; l < N; l++)	for (int r = l; r < N; r++)	{
			
			int minIdx = l;
			for (int i = l; i <= r; i++)	{
				if	( v[i] < v[minIdx] )
					minIdx = i;
			}
			
			if	( minIdx != rmq.getMin(l, r) )
				System.out.println("error");
			
		}
	}

}




