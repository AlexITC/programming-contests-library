/**
 * Given N integers and Q queries where each Q represent two values (l and r)
 * print how many different values are in sub-array [l, r]
 * 
 * @author Alexis Hernández
 * OFFLINE SOLUTION
 * O(N log N)
 * 
 * Sample input:
 * 8 5
 * 1 1 1 2 3 5 1 2
 * 1 8
 * 2 3
 * 3 6
 * 4 5
 * 4 8
 * 
**/
import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class DistinctElementsSubArray {
	static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out) );
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st = new StringTokenizer("");
	static String next() throws Exception {

		while (!st.hasMoreTokens()) {
			String s = br.readLine();
			if (s == null)
				return null;
			st = new StringTokenizer(s);
		}
		return st.nextToken();
	}

	public static void main(String[] asda) throws Exception {
		
		int N = Integer.parseInt( next() );	// number of elements
		int Q = Integer.parseInt( next() );	// number of queries
		
		// array for saving events
		Node v [] = new Node [Q + N + 5];
		int len = 0;	// number of events
		
		// read static values in array, compress it for saving space and handle it as events
		int color = 1;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		// elements are [1, N]
		for (int i = 1; i <= N; i++)	{
			int val = Integer.parseInt( next() );	// current element
			// compress element
			if	( !map.containsKey(val) )	map.put(val, color++);
			// add event
			v[ len++ ] = new Node( i, i, map.get(val), Node.INF_POS);
		}

		// read queries and handle it as events
		for (int i = 0; i < Q; i++)	{
			// queries start from index 0 but range from 1 to N
			int s = Integer.parseInt( next() );	// start index
			int e = Integer.parseInt( next() );	// end index
			v[ len++ ] = new Node( s, e, -1, i );
		}
		
		// sort events
		Arrays.sort(v, 0, len);
		
		// map[i] = index where i has appeared in the last time
		map.clear();
		BIT bit = new BIT( N + 10 );	// different colors in tree
		// array for storing answers
		int [] ans = new int [Q + 7];
		
		// handle events
		for (int i = 0; i < len; i++)	{
			Node node = v[i];
			if	( node.pos != Node.INF_POS )	{
				// event is a query, compute answer
				ans[ node.pos ] = bit.get(node.end) - bit.get(node.start - 1);
				continue;
			}
			// assignation
			Integer last = map.get( node.val );
			if	( last != null )	{
				// val has appeared before, delete its appearition
				bit.add( last, -1 );
			}
			// set a new appearition of node.val
			bit.add( node.start, 1 );
			map.put( node.val, node.start );	// update last pointer
		}
		// print answers
		for (int i = 0; i < Q; i++)
			out.println( ans[i] );
		//
		out.flush();
	}
	static class Node	implements Comparable<Node>	{
		static int INF_POS = 200000000;	// larger val doesn't appearing in input
		int start, end;
		int pos;	// >= 0 & < INF_POS if this node represent a query
		int val;	//
		Node(int s, int e, int v, int p)	{
			start = s;
			end = e;
			val = v;
			pos = p;
		}
		public int compareTo(Node that)	{
			// by end pos
			if	( end != that.end )
				return	end - that.end;
			// break ties with type not a query
			return	that.val - val;
		}
		
		public String toString()	{
			return	pos == INF_POS ? 
				"ASSIGN " + val + " to idx " + end
					:
				"QUERY " + pos + " range (" + start + ", " + end + ")";
			
		}
	}

	static class BIT {
		int [] tree;
		BIT(int N)	{
			tree = new int [ N + 1 ];
		}
		void add(int idx, int val)	{
			while	( idx < tree.length )	{
				tree[idx] += val;
				idx += Integer.lowestOneBit(idx);
			}
		}
		int get(int idx)	{
			int ans = 0;
			while	( idx > 0 )	{
				ans += tree[idx];
				idx -= Integer.lowestOneBit(idx);
			}
			return	ans;
		}
	}

}

