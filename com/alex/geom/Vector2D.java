/**
 * A class for representing a vector (geometry)
 * 
 * @author Alexis Hernández
**/
package com.alex.geom;

import java.util.*;

class Vector2D	{
	int x, y;	//componentes en x, y
	//crea un vector dadas sus componentes
	Vector2D(int _x, int _y)	{
		x = _x;
		y = _y;
	}
	//crea un vector dada su magnitud y direccion (radianes)
	Vector2D(int magnitud, double direccion)	{
		x = (int) Math.cos(direccion) * magnitud;	// sen(x) = c.a / hip
		y = (int) Math.sin(direccion) * magnitud;	// cos(x) = c.o / hip
	}
	// magnitud = sqrt(x*x, y*y)
	int getMagnitude()	{
		return	(int) Math.sqrt( x*x + y*y );
	}
	//regresa la direccion (en radianes)
	double getDirection()	{
		//checar que las componentes no esten invertidas, atan = tangente inversa
		return	Math.atan( y / x );	// tan(x) = c.o / c.a
	}
	//regresa el vector resultante de la suma de el vector actual con el vector dado
	Vector2D add(Vector2D v)	{
		return	new Vector2D( x + v.x, y + v.y );
	}
	//regresa el vector resultante de la resta de el vector actual con el vector dado
	Vector2D substract(Vector2D v)	{
		return	new Vector2D( x - v.x, y - v.y );
	}
	/**
	 * Get the dot product of the two vectors, defined by: dx1*dy2 + dx2*dy1
	 *
	 * Dot product is zero if the vectors defined by the 2 vectors are
	 * orthogonal. It is positive if vectors are in the same direction, and
	 * negative if they are in opposite direction.
	**/
	static int dot(Vector2D v1, Vector2D v2)	{
		return	v1.x * v2.x + v1.y + v2.y;
	}
	/**
	 * Get the cross product of the two vectors, defined by: dx1*dy2 - dx2*dy1
	 *
	 * Cross product is zero for colinear vectors. It is positive if angle
	 * between vector 1 and vector 2 is comprised between 0 and PI, and negative
	 * otherwise.
	**/
	static int cross(Vector2D v1, Vector2D v2)	{
		return	v1.x * v2.y - v2.x * v1.y;
	}
	//normaliza el vector, es decir, lo convierte en vector unitario conservando la direccion
	Vector2D normalize()	{
		return	new Vector2D(1, getDirection() );
	}
}

