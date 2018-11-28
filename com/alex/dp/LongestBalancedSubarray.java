package com.alex.dp;

import java.awt.*;

public class LongestBalancedSubarray {
	// point(pos, value), value = 1 or -1
	int getMax(Point [] pts)	{
		
		int N = pts.length;
		
		int [] v = new int [N];
		for (int i = 0; i < N; i++)
			v[i] = pts[i].y;
		
		
		
		int [] sum = new int [N];
		sum[0] = v[0];
		int min = sum[0];
		int max = sum[0];
		for (int i = 1; i < N; i++)	{
			sum[i] = sum[i - 1] + v[i];
			min = Math.min(sum[i], min);
			max = Math.max(sum[i], max);
		}
		
		if	( sum[N - 1] == 0 || (sum[N - 1] > 0 && (N&1) == 0))	{
			return	(pts[N - 1].x - pts[0].x);
		}
		
		int [] hash = new int [max - min + 1];
		for (int i = 0; i < hash.length; i++)	{
			hash[i] = -1;
		}

		int ans = 0;
		for (int i = 0; i < N; i++)	{
			if	( sum[i] == 0 || (sum[i] > 0 && (i&1) == 1) )	{
				// store max
				
				int len = pts[i].x - pts[0].x;
		//		out.println(len);
				ans = Math.max(max, len);
			}
			
			if	( hash[ sum[i] - min ] == -1 )	{
				hash[ sum[i] - min ] = i;
				continue;
			}
			
			// keep max
			int start = hash[ sum[i] - min ] + 1;
			int size = i - hash[ sum[i] - min ];
			
			int len = pts[ start + size - 1 ].x - pts[ start ].x;
		//	out.println(len);
			ans = Math.max(ans, len);
				
		}
		
		return	ans;
	}
	
}
