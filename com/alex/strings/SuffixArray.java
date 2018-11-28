package com.alex.strings;
/**
 * A suffix array implementation and some applications
 * 
 * O(N * logN) - Manber and Myers algorithm
 * Refer to "Suffix arrays: A new method for on-line string searches", by Udi Manber and Gene Myers
 * 
 * Output:
 * pos = The suffix array. Contains the n suffixes of str sorted in lexicographical order.
 *       Each suffix is represented as a single integer (the position of str where it starts).
 * rank = The inverse of the suffix array. rank[i] = the index of the suffix str[i..n)
 *        in the pos array. (In other words, pos[i] = k <==> rank[k] = i)
 *        With this array, you can compare two suffixes in O(1): Suffix str[i..n) is smaller
 *        than str[j..n) if and only if rank[i] < rank[j]
 * 
 * @author Alexis Hernández
 * @source: http://apps.topcoder.com/forums/?module=RevisionHistory&messageID=1171511
 */

import java.util.*;
import java.awt.*;

public class SuffixArray {
	final String str; // input
	int N;
	Integer [] pos;	// output
	int [] rank;	// output
	int LCP [];		// LCP[i] = Longest common prefix of suffix pos[i] and suffix pos[i - 1]
	int distinct;	// number of distinct substrings of str

	public SuffixArray(final String str) {
		this.str = str;
		N = str.length();

		pos = new Integer[N];
		rank = new int[N];
		int [] cnt = new int[N];
		int [] next = new int[N];
		boolean [] bh = new boolean[N];
		boolean [] b2h = new boolean[N];

		// sort suffixes according to their first characters
		for (int i = 0; i < N; i++)
			pos[i] = i;

		Arrays.sort(pos, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return str.charAt(a) - str.charAt(b);
			}
		});
		// {pos contains the list of suffixes sorted by their first
		// character}

		bh[0] = true;
		for (int i = 1; i < N; ++i) {
			bh[i] = str.charAt(pos[i]) != str.charAt(pos[i - 1]);
		}

		for (int h = 1; h < N; h <<= 1) {
			// {bh[i] == false if the first h characters of pos[i-1] == the
			// first h characters of pos[i]}
			int buckets = 0;
			for (int i = 0, j; i < N; i = j) {
				j = i + 1;
				while (j < N && !bh[j])
					j++;
				next[i] = j;
				buckets++;
			}
			if (buckets == N)
				break; // We are done! Lucky bastards!
			// {suffixes are separted in buckets containing strings starting
			// with the same h characters}

			for (int i = 0; i < N; i = next[i]) {
				cnt[i] = 0;
				for (int j = i; j < next[i]; ++j) {
					rank[pos[j]] = i;
				}
			}

			cnt[rank[N - h]]++;
			b2h[rank[N - h]] = true;
			for (int i = 0; i < N; i = next[i]) {
				for (int j = i; j < next[i]; ++j) {
					int s = pos[j] - h;
					if (s >= 0) {
						int head = rank[s];
						rank[s] = head + cnt[head]++;
						b2h[rank[s]] = true;
					}
				}
				for (int j = i; j < next[i]; ++j) {
					int s = pos[j] - h;
					if (s >= 0 && b2h[rank[s]]) {
						for (int k = rank[s] + 1; k < N && !bh[k] && b2h[k]; k++)
							b2h[k] = false;
					}
				}
			}
			for (int i = 0; i < N; ++i) {
				pos[rank[i]] = i;
				bh[i] |= b2h[i];
			}
		}
		

		// compute inverse of suffix array
		for (int i = 0; i < N; ++i)
			rank[ pos[i] ] = i;
		
		/**
		 * Begin of the O(n) longest common prefix algorithm
		 * Refer to "Linear-Time Longest-Common-Prefix Computation in Suffix
		 * Arrays and Its Applications" by Toru Kasai, Gunho Lee, Hiroki
		 * Arimura, Setsuo Arikawa, and Kunsoo Park
		**/

		distinct = (N * (N + 1) ) >> 1;
		LCP = new int [N];
		LCP[0] = 0;
		for (int i = 0, h = 0; i < N; ++i)	{
			if ( rank[i] > 0 )	{
				int j = pos[ rank[i] - 1 ];
				while ( i + h < N && j + h < N && str.charAt(i+h) == str.charAt(j+h) )
					h++;
				LCP[ rank[i] ] = h;
				distinct -= h;
				if ( h > 0 )
					h--;
			}
		}
	}

	/**
	 * Given two strings compute its longest common substring
	 * the longest string appearing in both strings
	 * O(N + N * log N)
	 */
	static int longestCommonSubstring(String s, String t)	{
		int N1 = s.length();
		int N2 = t.length();
		int N = N1 + N2 + 1;
		SuffixArray sa = new SuffixArray( s + '$'+ t );
		int ans = 0;
	//	String lcs = "";
		
		for (int i = 1; i < N; i++)	{

            // adjacent suffixes both from first text string
            if (sa.pos[i] < N1 && sa.pos[i-1] < N1) continue;

            // adjacent suffixes both from secondt text string
            if (sa.pos[i] > N1 && sa.pos[i-1] > N1) continue;

            // check if adjacent suffixes longer common substring
            int length = sa.LCP[i];
            if (length > ans) {
                ans = length;
         //       lcs = sa.str.substring( sa.pos[i], sa.pos[i] + length );
            }
		}
//		System.out.println(lcs);
		
		return	ans;
	}
	/**
	 * given two strings, returns the shortest common unique substring
	 * the shortest substring unique in both strings 
	**/
	static int shorestUniqueCommonSubstring(String s, String t)	{

		StringBuilder sb = new StringBuilder();
		int N = s.length();
		int M = t.length();
		sb.append(s);
		sb.append("$");
		sb.append(t);
		
		SuffixArray sa = new SuffixArray( sb.toString() );
		
		int [] LCP = sa.LCP;
		
		int INF = 100000000;
	    int min = INF;
	    int len = sb.length();
	    
	    for (int i = 0; i + 1 < len; i++)	{
	    	int a = sa.pos[i];
	    	int b = sa.pos[i + 1];
	    	
	    	if	( a < N != b < N )	{
	    		// a is from s and b from t, or b -> s, a -> t
	    		int x = i == 0 ? 0 : LCP[i];
	    		int y = i + 2 < len ? LCP[i + 2] : 0;
	    		
	    		int z = LCP[i + 1];
	    		int max = Math.max(x, y);
	    		
	    		if	( z > max && min > max + 1 )
	    			min = max + 1;
	    	}
	    }
		return	min == INF ? 0 : min;
	}
	/**
	 * count how many times appear string k as substring of s O( k * logN )
	 */
	int countOccurrences(String k) {
		int idx = firstOccurrence(k);
		if (idx < 0)
			return 0;
		
		System.out.println( k + " " + rank[idx] + " " + rank[lastOccurrence(k)] );
		return lastOccurrence(k) - idx + 1;
	}

	/**
	 * returns index of first occurrence of k as substring of s -1 if
	 * substring k doesn't exists O(k * logN)
	 */
	int firstOccurrence(String k) {
		int hi = N - 1;
		int low = 0;
		int mid;
		int ans = -1;

		while (low <= hi) {
			mid = (low + hi) >> 1;
			int cmp = compare(pos[mid], k);

			if (cmp == 0)
				ans = mid;

			if (cmp <= 0)
				hi = mid - 1;
			else
				low = mid + 1;
		}

		return ans;
	}

	/**
	 * returns index of last occurrence of k as substring of s -1 if
	 * substring k doesn't exists O(k * logN)
	 */
	int lastOccurrence(String k) {
		int hi = N - 1;
		int low = 0;
		int mid;
		int ans = -1;

		while (low <= hi) {
			mid = (low + hi) >> 1;
			int cmp = compare(pos[mid], k);

			if (cmp == 0)
				ans = mid;

			if (cmp < 0)
				hi = mid - 1;
			else
				low = mid + 1;
		}

		return ans;
	}

	boolean contains(String k) {
		int hi = N - 1;
		int low = 0;
		int mid;

		while (low <= hi) {
			mid = (low + hi) >> 1;
			int cmp = compare(pos[mid], k);

			if (cmp == 0)
				return true;

			if (cmp <= 0)
				hi = mid - 1;
			else
				low = mid + 1;
		}

		return false;
	}
	/**
	 * returns the longest repeated substring in str
	 * for example: lrs('aabaa') = 'aa'
	 * @return Point( idx, len ) where idx = index where substring starts, len = length of substring
	 * O(N)
	**/
	Point longestRepeatedSubstring()	{
		int max = -1;
		int idx = 0;
		for (int i = 1; i < N; i++)	{
			if	( LCP[i] > max )	{
				max = LCP[i];
				idx = pos[i];
			}
		}
		return	new Point( idx, max );
	}
	/**
	 * Helper functions
	**/

	/**
	 * compare string t with k-th suffix of s compare up to t.length() chars
	 * 
	 * @return +1, -1, 0
	 */
	int compare(int k, String t) {
		int i = 0;
		while (i < t.length()) {
			char x = charAt(str, k++);
			char y = charAt(t, i++);
			if (y != x)
				return y - x;
		}

		return 0;
	}

	// charAt util
	char charAt(String s, int k) {
		return k >= s.length() ? 0 : s.charAt(k);
	}
	
	// test client
	public static void main(String [] asdad)	{
		Scanner in = new Scanner(System.in);
		String s = in.next();
		SuffixArray sa = new SuffixArray(s);
		
		for (int i = 1; i < s.length(); i++)	if	( sa.LCP[i] > 0 )	{
			String sbs = s.substring( sa.pos[i], sa.pos[i] + sa.LCP[i] );
			System.out.println( sbs + " " + sa.countOccurrences(sbs) );
		}
	}
}






