/**
 * A simple quad tree
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;

import java.awt.Point;
import java.io.*;
import java.util.*;



class QuadTree	{

	static class Point2D	extends	Point	implements Comparable<Point2D>	{
		final static Random RNG = new Random();
		public Point2D(int x, int y)	{
			super(x,y);
		}
		public int distTo(Point that)	{
			return	Math.abs( x - that.x ) + Math.abs( y - that.y );
		}
		Point2D closetPoint(Point2D a, Point2D b)	{
			if	( a == null )	return	b;
			if	( b == null )	return	a;
			if	( distTo(a) > distTo(b) )	return	b;
			return	a;
		}
		public boolean equals(Object o)	{
			Point2D that = (Point2D) o;
			return	x == that.x && y == that.y;
		}
		public int compareTo(Point2D that)	{
			return	RNG.nextInt() -  RNG.nextInt();
		}
	}
	Node root;
	QuadTree()	{
		root = null;
	}
	void add(Point2D p)	{
		root = add(root, p);
	}
	private Node add(Node root, Point2D p)	{
		if	( root == null )	return	new Node(p);
		if	( p.equals(root.id) )	return	root;
		// Quadrant I
		if	( isQuadrant1(root.id, p) )	root.Q1 = add( root.Q1, p );
		// Quadrant II
		else if	( isQuadrant2(root.id, p) )	root.Q2 = add( root.Q2, p );
		// Quadrant III
		else if	( isQuadrant3(root.id, p) )	root.Q3 = add( root.Q3, p );
		// Quadrant IVhttps://www.facebook.comm
		else if	( isQuadrant4(root.id, p) )	root.Q4 = add( root.Q4, p );
		return	root;
	}
	Point2D getClosetPoint(Point2D p)	{
		min = null;
		return	getClosetPoint( root, p );
	}
	Point2D min;
	private Point2D getClosetPoint( Node root, Point2D p )	{
		if	( root == null )	return	null;
		if	( p.equals(root.id ) )	{
			// visit all sons
			min = p.closetPoint( getClosetPoint( root.Q1, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q2, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q3, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q4, p ), min );
			return min;
		}	else	
			min = p.closetPoint(min, root.id);

		// Quadrant I
		if	( isQuadrant1(root.id, p) )	{
			// min q2, q1, q4
			min = p.closetPoint( getClosetPoint( root.Q1, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q2, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q4, p ), min );
		}
		// Quadrant II
		else if	( isQuadrant2(root.id, p) )	{
			min = p.closetPoint( getClosetPoint( root.Q1, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q2, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q3, p ), min );
		}
		// Quadrant III
		else if	( isQuadrant3(root.id, p) )	{
			min = p.closetPoint( getClosetPoint( root.Q2, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q3, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q4, p ), min );
		}
		// Quadrant IV
		else if	( isQuadrant4(root.id, p) )	{
			min = p.closetPoint( getClosetPoint( root.Q1, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q3, p ), min );
			min = p.closetPoint( getClosetPoint( root.Q4, p ), min );
		}
		return	min;
	}
	/**
	 * |-----|
	 * |Q2|Q1|
	 * |--|--|
	 * |Q3|Q4|
	 * |-----|
	**/
	boolean isQuadrant1(Point2D id, Point2D p)	{
		return	p.x >= id.x && p.y >= id.y;
	}
	boolean isQuadrant2(Point2D id, Point2D p)	{
		return	p.x <= id.x && p.y >= id.y;
	}
	boolean isQuadrant3(Point2D id, Point2D p)	{
		return	p.x <= id.x && p.y <= id.y;
	}
	boolean isQuadrant4(Point2D id, Point2D p)	{
		return	p.x >= id.x && p.y <= id.y;
	}
	class Node	{
		Node Q1, Q2, Q3, Q4;
		Point2D id;
		Node(Point2D p)	{
			id = p;
		}
	}
	
	

	static BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
	static StringTokenizer st = new StringTokenizer("");
	static String next()	throws Exception	{
		while	( !st.hasMoreTokens() )	st = new StringTokenizer( br.readLine() );
		return	st.nextToken();
	}
	public static void main(String [] asda)	throws Exception	{
		PrintWriter out = new PrintWriter( new BufferedOutputStream(System.out) );
		//
		int N = Integer.parseInt( next() );
		Point2D [] v = new Point2D [N];
		Point2D [] order = new Point2D [N];
		QuadTree tree = new QuadTree();
		for (int i = 0; i < N; i++)	{
			order[i] = v[i] = new Point2D(
				Integer.parseInt( next() ),
				Integer.parseInt( next() )
			);
			tree.add(v[i]);
		}
		Arrays.sort(order);
		for (Point2D p : order)	tree.add(p);
		for (int i = 0; i < N; i++)	{
			Point2D near = tree.getClosetPoint(v[i]);
			out.println( v[i].distTo( near ) );
		}
		
		out.flush();
	}
}

