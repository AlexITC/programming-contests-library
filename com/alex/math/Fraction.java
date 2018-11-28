package com.alex.math;

public class Fraction	{
	
	long a, b;	// a / b
	
	Fraction(long a)	{
		this.a = a;
		b = 1;
	}
	
	Fraction(long a, long b)	{
		this.a = a;
		this.b = b;
	}
	
	public Fraction add(Fraction that)	{
		
		long lcm = LCM(b, that.b);
		
		long x = (a*lcm / b) + (that.a*lcm / that.b);
		
		return	new Fraction( x, lcm );
		
	}
	
	public Fraction sub(Fraction that)	{
		
		long lcm = LCM(b, that.b);
		
		long x = (a*lcm / b) - (that.a*lcm / that.b);
		
		return	new Fraction( x, lcm );
	}
	
	public Fraction mul(Fraction that)	{
		long a = this.a * that.a;
		long b = this.b * that.b;
		long gcd = GCD(a, b);
		return	new Fraction( a / gcd, b / gcd );
	}
	
	public Fraction div(Fraction that)	{
		long a = this.a * that.b;
		long b = this.b * that.a;
		long gcd = GCD(a, b);
		return	new Fraction( a / gcd, b / gcd );
	}
	
	
	private long GCD(long a, long b) {
		long t;
		while (b != 0) {
			t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
	
	private long LCM(long a, long b) {
		return a * b / GCD(a, b);
	}
	
	public String toString()	{
		return	a + "/" + b;
	}
}

