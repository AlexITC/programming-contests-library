package com.alex.collection;

import java.io.*;
import java.util.*;


public class MOs {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	static StringTokenizer st = new StringTokenizer("");

	public static String next() {
		try	{
		while (!st.hasMoreTokens()) {
			String s = br.readLine();
			if (s == null)
				return null;
			st = new StringTokenizer(s);
		}
		return st.nextToken();
		}	catch(Exception e)	{
			return	null;
		}
	}
	public static void main(String[] asda) throws Exception {

        int CASES = Integer.parseInt( next() );
        for (int ccc = 1; ccc <= CASES; ccc++)  {
       
        int N = Integer.parseInt( next() );
        int Q = Integer.parseInt( next() );
       
        
        // read array
        int [] a = new int [N];
        for (int i = 0; i < N; i++)    {
            int val = Integer.parseInt( next() );
            a[i] = val;
        }
       
        
        // read queries
        Query q [] = new Query [Q];
        for (int i = 0; i < Q; i++) {
            int s = Integer.parseInt( next() ) - 1;
            int e = Integer.parseInt( next() ) - 1;
            q[ i ] = new Query(s, e, i);
        }
        
        // sqrt decomposition
        final int block = 1 + (int)Math.sqrt(N);
        
        // sort queries
        Comparator<Query> cmp = new Comparator<Query>() {
        	public int compare(Query a, Query b)	{
        		// sort by block if possible
        		if	( a.start / block != b.start / block )
        			return	(a.start / block) - (b.start / block);
        		// sort by end
        		return	a.end - b.end;
        	}
        };
        Arrays.sort(q, cmp);
        
        
        // process queries
		create();
        int ans [] = new int [Q];   // answers would be saved here
    	int currentL = 0, currentR = 0;
    	for (int k = 0; k < Q; k++)	{
    		int L = q[k].start, R = q[k].end;
    	//	out.println(q[k]);
    		
    		while	( currentL < L )
    			remove( a[currentL++] );
    		
    		while	( currentL > L )
    			add( a[--currentL] );
    		
    		while	( currentR <= R )
    			add( a[currentR++] );
    		
    		while	( currentR > R+1)
    			remove( a[--currentR] );
    		
    		ans[ q[k].pos ] = answer;
    	}
    	
    	// print answers
        out.println("Case " + ccc + ":");
        for (int i = 0; i < Q; i++)
            out.println( ans[i] );
        
        // clean answers
        if	( Q != 0 )	{
            Query last = q[ q.length - 1 ];
            while	( last.start <= last.end )
            	remove( a[ last.start++ ] );
        }
        
     //   out.println("ans= " + answer);
        }
       
        //
        out.flush();
        System.exit(0);
    }
    static class Query   {
        int start, end, pos;
        Query(int s, int e, int p)    {
            start = s;
            end = e;
            pos = p;
        }
        public String toString()	{
        	return	String.format("%d -> (%d, %d)", pos, start, end);
        }
    }
    
    // Data structure allowing insert and remove
    static int [] cnt;
    static int answer;
    static void create()	{
    	cnt = new int [100010];
    	answer = 0;
    }

    static void add(int val)	{
    	cnt[val]++;
    	if	( cnt[val] == 1 )
    		answer++;
    }
    static void remove(int val)	{
    	cnt[val]--;
    	if	( cnt[val] == 0 )
    		answer--;
    }
}
