/**
 * Self-balancing binary search tree using heap propeties
 * 
 * @author Alexis Hernández
**/

package com.alex.collection;

import java.util.Comparator;


public class Treap<T>	{

	class TreapNode	{
		TreapNode left, right;	// sons;
		T key;					// key
		int prior;				// priority
		int size;				// size of tree
		TreapNode(T key, int N)	{
			this.key = key;
			this.prior = this.hashCode();
			this.size = N;
		}
		public String toString()	{
			return	key.toString();
		}
	}
	
	private TreapNode root;
	private final Comparator<T> COMPARATOR;
	
	public Treap(Comparator<T> cmp)	{
		root = null;
		this.COMPARATOR = cmp;
	}
	// insert key into treap
	public void add(T key)	{
		root = add(root, key);
	}
	// insert key into given node
	private TreapNode add(TreapNode node, T key)	{
		if	( node == null )
			return	new TreapNode(key, 1);
		
		int cmp = COMPARATOR.compare(key, node.key);
		if	( cmp < 0 )	{
			// to left son
			node.left = add( node.left, key );
			if	( node.left.prior > node.prior )
				node = rotateRight(node);
		}	else	{
			// to right son
			node.right = add( node.right, key );
			if	( node.right.prior > node.prior )
				node = rotateLeft(node);
		}
		node.size = 1 + size( node.left ) + size( node.right );
		return	node;
	}
	
	// get k-th smallest element from tree
	// test method
	public T getKthSmallestKey(int kth)	{
		TreapNode node = root;
		int i = 0;
		while	( node != null )	{
			int left = size(node.left);
			if	( i + left + 1 == kth )
				return	node.key;
			
			if	( i + left >= kth )
				node = node.left;
			else	{
				node = node.right;
				i += left + 1;
			}
		}
		return	null;
	}
	
	// count elements on tree
	public int size()	{
		return	size(root);
	}
	// size of tree node
	private int size(TreapNode node)	{
		return	node == null ? 0 : node.size;
	}
	
	// find node given key, null is key doesn't exists
	private TreapNode findNode(TreapNode node, T key)	{
		while	( node != null )	{
			int cmp = COMPARATOR.compare(key, node.key);
			if	( cmp == 0 )
				return	node;
			// in left subtree?
			if	( cmp < 0 )
				node = node.left;
			else
				node = node.right;
		}
		return	null;
	}
	
	// tests if treap contains given key
	public boolean contains(T key)	{
		return	findNode(root, key) != null;
	}
	
	// delete key (if exists)
	public void remove(T key)	{
		if	( contains(key) )
			root = remove( root, key );
	}
	private TreapNode remove(TreapNode node, T key)	{
		if	( node == null )
			return	null;
		
		int cmp = COMPARATOR.compare(key, node.key);
		
		if	( cmp == 0 )	{
			// node found
			if	( node.left == null && node.right == null )	{
				// if node have no children
				return	null;
			}
			
			if	( node.left != null && node.right != null )	{
				// if node have two children
				if	( node.left.prior > node.right.prior )	{
					node = rotateRight(node);
					node.right = remove( node.right, key );
				}	else	{
					node = rotateLeft(node);
					node.left = remove( node.left, key );
				}
			}
			
			if	( node.left == null )	{
				// only right child
				node = rotateLeft(node);
				node.left = remove( node.left, key );
			}	else	{
				// only left child
				node = rotateRight(node);
				node.right = remove( node.right, key );
			}
		}
		else if	( cmp < 0 )
			node.left = remove( node.left, key );
		else if ( cmp > 0 )
			node.right = remove( node.right, key );

		node.size = 1 + size(node.left) + size(node.right);
		return	node;
	}

	// helper functions to mantain treap
	private TreapNode rotateRight(TreapNode node)	{
		TreapNode tmp = node.left;
		node.left = tmp.right;
		tmp.right = node;
		return	tmp;
	}
	private TreapNode rotateLeft(TreapNode node)	{
		TreapNode tmp = node.right;
		node.right = tmp.left;
		tmp.left = node;
		return	tmp;
	}
}

