/**
 * Aho-corasick algorithm implementation for multiple pattern matching in a long text
 *
 * Notes:
 *  - This doesn't work with repeated patterns (handle it yourself)
 *  - Be sure to choose the right MAX (allowed number of nodes) to get a better performance
 *  - Update char2id function to handle your alphabet
 *
 * @author: Alexis Hernandez
**/
package com.alex.strings;

import java.util.*;

public class AhoCorasick	{
	
	private final int MAX = 151 * 70;	// max number of nodes (number of words * length)
	private final int CHAR_SET = 26;	// alphabeth length
	
	private final int EMPTY = -1;
	private final int ROOT;
	
	private int len;		// number of nodes

	private int [][] go;	// goto function
	private int [] fail;	// fail function
	private int [] output;	// val[i] = value assigned to i-th node, -1 if not assigned
	
	// map a character to its index
	int char2id(char ch)	{
		return	ch - 'a';
	}
	
	// initialize trie
	public AhoCorasick(String [] keywords, int [] cnt, String text)	{
		ROOT = 0;	// root node
		go = new int [MAX][];
		output = new int[MAX];
		fail = new int [MAX];
		
		// create root node
		go[ROOT] = new int[CHAR_SET];
		Arrays.fill(go[ROOT], EMPTY);
		Arrays.fill(output, EMPTY);
		len = 1;	// only root node exists
		
		//
		insertKeywords(keywords);
		computeFailureFunction();
		computeNext();
		patternMatchinMachine(text, cnt);
	}

	// count how many times appears each word of dictionary in s
	// algorithm 1
	private void patternMatchinMachine(String s, int [] cnt)	{
		int state = ROOT;
		for (int i = 0; i < s.length(); i++)	{
			int a = char2id( s.charAt(i) );
			
			while	( go[state][a] == EMPTY )	state = fail[state];
			state = go[state][a];
			
			int p = state;
			while	( p != ROOT )	{
				if	( output[p] != EMPTY )	{
				//	out.println( "found " + output[p] + " at " + i );
					cnt[ output[p] ]++;
				}
				p = fail[p];
			}
		}
	}
	
	// algorithm 2
	private void insertKeywords(String [] keywords)	{
		for (int i = 0; i < keywords.length; i++)	{
			String s = keywords[i];
			int state = ROOT;
			for (int k = 0; k < s.length(); k++)	{
				int idx = char2id( s.charAt(k) );
				state = nextState(state, idx);
			}
			output[state] = i;
		}
		//
		fail[ROOT] = ROOT;
		for (int a = 0; a < CHAR_SET; a++)	if	( go[ROOT][a] == EMPTY )
			go[ROOT][a] = ROOT;
	}
	
	// get the nextState at nodex[node][idx], create a new one if doesn't exists
	private int nextState(int node, int idx)	{
		if	( go[node][idx] == EMPTY )	{
			go[node][idx] = len;
			go[len] = new int[CHAR_SET];
			
			Arrays.fill( go[len], EMPTY );
			len++;
		}
		
		return	go[node][idx];
	}
	
	// compute failure function
	// algorithm 3
	private void computeFailureFunction()	{
		Queue<Integer> q = new LinkedList<Integer>();
		for (int a = 0; a < CHAR_SET; a++)	if	( go[ROOT][a] != ROOT )	{
			q.add( go[ROOT][a] );
			fail[ go[ROOT][a] ] = ROOT;
		}
		
		while	( !q.isEmpty() )	{
			int r = q.poll();	// next state
			for (int a = 0; a < CHAR_SET; a++)	if	( go[r][a] != EMPTY )	{
				int s = go[r][a];
				q.add( s );
				int state = fail[r];
				while	( go[state][a] == EMPTY )	state = fail[state];
				fail[ s ] = go[state][a];
			}
		}
	}
	
	// construction of deterministic finite automaton
	// algorithm 4
	private void computeNext()	{
	//	int [][] next = new int [len][CHAR_SET];
		int [][] next = go;
		Queue<Integer> q = new LinkedList<Integer>();
		for (int a = 0; a < CHAR_SET; a++)	{
			next[ROOT][a] = go[ROOT][a];
			if	( go[ROOT][a] != ROOT )	{
				q.add( go[ROOT][a] );
			}
		}
		
		while	( !q.isEmpty() )	{
			int r = q.poll();
			for (int a = 0; a < CHAR_SET; a++)	{
				int s = go[r][a];
				if	( s != EMPTY )	{
					q.add( s );
					next[r][a] = s;
				}	else	{
					next[r][a] = next[ fail[r] ][a];
				}
			}
		}
		go = next;
	}
	
}

