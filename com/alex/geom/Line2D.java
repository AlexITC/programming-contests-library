/**
 * A class for representing a line in the plane using equation AX + BY = C
 * 
 * Required Libraries: Point2D.java, MyMath.java
 * 
 * tested:
 * http://coj.uci.cu/24h/problem.xhtml?abb=2513
 * http://acm.tju.edu.cn/toj/showp1567.html
 * 
 * @author Alexis Hernández
**/
package com.alex.geom;

import com.alex.math.*;

// 
public class Line2D	{
	// Ax + By = C
	int A, B, C;
	// hashCode for this line
	int hash;
	// construct a line given two points
	public Line2D(Point2D p, Point2D q) {
		// equations
		A = (int) (p.y - q.y);
		B = (int) (q.x - p.x);
		C = (int) ( -(A*p.x) - (B * p.y) );
		int gcd = (int) MyMath.GCD( C, MyMath.GCD(A, B) );
		A /= gcd;
		B /= gcd;
		C /= gcd;
		if	( A < 0 || (A == 0 && B < 0) )	{
			A = -A;	B = -B; C = -C;
		}
		// cache hashCode
		hash = A;
		hash = hash * 31 + B;
		hash = hash * 31 + C;
	}
	// tests if this line intersect with that line
	public boolean intersect(Line2D that)	{
		return	A * that.B - that.A * B != 0;
	}
	// return the intersection point of two lines
	public Point2D intersectPoint(Line2D that)	{
		double det = A * that.B - that.A * B;
		double x = ( that.B * C - B * that.C ) / det;
		double y = ( A * that.C - that.A * C ) / det;
		return	new Point2D(x, y);
	}
	// tests if this line is equal to that line
	public boolean equals(Object o) {
		Line2D that = (Line2D) o;
		return	A == that.A && B == that.B && C == that.C;
	}
	public int hashCode()	{
		return	hash;
	}
}


