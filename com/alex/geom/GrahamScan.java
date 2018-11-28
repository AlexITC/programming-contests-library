/**
 * A class for building a convex hull given a set of points
 *
 * Required Libraries: Point2D.java
 *
 * tests:
 * http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=622
 *
 * @author Alexis Hernández
 * Date: 06-11-2013
**/

package com.alex.geom;

import java.util.*;

public class GrahamScan {
	// hull will contains the boundary points in counter clocwise
    Stack<Point2D> hull = new Stack<Point2D>();

    public GrahamScan(Point2D[] points) {
    	int N = points.length;
        // preprocess so that points[0] has lowest y-coordinate; break ties by x-coordinate
        // points[0] is an extreme point of the convex hull
        int min = 0;
        for (int i = 1; i < N; i++)	{
        	if	( points[i].compareTo( points[min] ) < 0 )
        		min = i;
        }
        Point2D aux = points[0];
        points[0] = points[min];
        points[min] = aux;
        
        // sort by polar angle with respect to base point points[0],
        // breaking ties by distance to points[0]
        Arrays.sort(points, 1, N, points[0].POLAR_ORDER);

        hull.push(points[0]);       // p[0] is first extreme point

        // find index k1 of first point not equal to points[0]
        int k1 = 0;

        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == N) return;        // all points equal

        // find index k2 of first point not collinear with points[0] and points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < N; k2++)
            if (Point2D.ccw(points[0], points[k1], points[k2]) != 0) break;
        hull.push(points[k2-1]);    // points[k2-1] is second extreme point

        // Graham scan; note that points[N-1] is extreme point different from points[0]
        for (int i = k2; i < N; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }
}

