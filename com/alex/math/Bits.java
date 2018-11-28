package com.alex.math;

public class Bits {

	static int union(int a, int b)	{
		return	a | b;
	}

	static int intersection(int a, int b)	{
		return	a & b;
	}
	
	static int substraction(int a, int b)	{
		return	a & -b;
	}
	
	static boolean contains(int a, int x)	{
		return	(a & (1 << x)) != 0;
	}

	static int insert(int a, int x)	{
		return	a &= ~(1 << x);
	}
	
}
