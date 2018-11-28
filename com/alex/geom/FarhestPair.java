/**
 * TESTS:
 *  http://acm.tju.edu.cn/toj/showp2847.html
**/
package com.alex.geom;

public class FarhestPair {
	// farthest pair of points and distance
	Point2D best1, best2;
	double bestDistance = Double.NEGATIVE_INFINITY;

	public FarhestPair(Point2D[] points) {
		GrahamScan graham = new GrahamScan(points);
		// number of points on the hull
		int M = graham.hull.size();

		// the hull, in counterclockwise order
		Point2D[] hull = new Point2D[M + 1];
		int m = 1;
		/*
		 * for (Point2D p : graham.hull) { hull[m++] = p; }
		 */
		while (!graham.hull.isEmpty()) {
			hull[m++] = graham.hull.pop();
		}

		// points are collinear
		if (M == 2) {
			best1 = hull[1];
			best2 = hull[2];
			bestDistance = best1.distanceSquaredTo(best2);
			return;
		}

		// k = farthest vertex from edge from hull[1] to hull[M]
		int k = 2;
		while (k < M
				&& Point2D.area2(hull[M], hull[k + 1], hull[1]) > Point2D
						.area2(hull[M], hull[k], hull[1])) {
			k++;
		}

		int j = k;
		for (int i = 1; i <= k; i++) {
			// StdOut.println("hull[i] + " and " + hull[j] + " are antipodal");
			if (hull[i].distanceSquaredTo(hull[j]) > bestDistance) {
				best1 = hull[i];
				best2 = hull[j];
				bestDistance = hull[i].distanceSquaredTo(hull[j]);
			}
			while ((j < M)
					&& Point2D.area2(hull[i], hull[j + 1], hull[i + 1]) > Point2D
							.area2(hull[i], hull[j], hull[i + 1])) {
				j++;
				// StdOut.println(hull[i] + " and " + hull[j] +
				// " are antipodal");
				double distance = hull[i].distanceSquaredTo(hull[j]);
				if (distance > bestDistance) {
					best1 = hull[i];
					best2 = hull[j];
					bestDistance = distance;
				}
			}
		}
	}
}
