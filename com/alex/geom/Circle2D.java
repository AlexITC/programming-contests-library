package com.alex.geom;

public class Circle2D {
	float x, y, r;
	Circle2D(float x, float y, float r)	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	/**
	 * check wheter a circle intersect in more than one point with other
	 * if a circle is inside the other, returns true
	**/
	boolean overlap(Circle2D that)	{
		double dist =  Math.pow(x - that.x, 2) + Math.pow(y - that.y, 2);
		
		return	Math.pow(r - that.r, 2) <= dist && dist <= Math.pow(r + that.r, 2);
	}
}
