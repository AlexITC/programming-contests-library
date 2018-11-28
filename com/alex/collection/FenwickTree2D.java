/**
 * Fenwick tree (2D version)
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;

class FenwickTree2D	{
	private int [][] tree;
	private int maxX, maxY;
	public FenwickTree2D(int sizeX, int sizeY)	{
		maxX = 1;
		while ((maxX <<= 1) < sizeX);
		maxY = 1;
		while ((maxY <<= 1) < sizeY) ;
		tree = new int[maxX+1][maxY+1];
	}
	//add val points to rect[x][y]
	public void add(int x, int y, int val)	{
		int y1 = y;
		while (x <= maxX) {
			y = y1;
			while (y <= maxY) {
				tree[x][y] += val;
				y += Integer.lowestOneBit(y);
			}
			x += Integer.lowestOneBit(x);
		}
	}
	//returns sum of rect[1, 1][x, y]
	public int getSum(int x, int y)	{
		int res = 0;
		while	( x > 0 )	{
			res += getSumY(x, y);
			x -= Integer.lowestOneBit(x);
		}
		return res;
	}
	//returns the sum of Y on rect[x]
	private int getSumY(int x, int y)	{
		int res = 0;
		while	( y > 0 )	{
			res += tree[x][y];
			y -= Integer.lowestOneBit(y);
		}
		return	res;
	}
	//returns sum of rect[x, y][X, Y]
	public int getSum(int x, int y, int X, int Y)	{
		if ( x == 1 && y == 1 )	return	getSum(X, Y);
		if ( X == 1 )	{
			return	getSum(X, Y) - getSum(X, y - 1);
		}
		if ( Y == 1 )	{
			return	getSum(X, Y) - getSum(x - 1, Y);
		}
		return	getSum(X, Y) + getSum(x - 1, y - 1) - getSum(x - 1, Y) - getSum(X, y - 1);
	}
}
