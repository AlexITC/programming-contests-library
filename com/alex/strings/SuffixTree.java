package com.alex.strings;


public class SuffixTree {
	 //   public static String alphabet = "$abcdefghijklmnopqrstuvwxyz";
    public static int alphabetSize = 27;

    private int char2id(char ch)	{
    	if	( ch == '$' )
    		return	0;
    	return	ch - 'a' + 1;
    }
    

    private Node root;
    private String s;
    
    
    class Node {
        public int depth; // from start of suffix
        public int begin; // start index (inclusive)
        public int end;	  // end index (exclusive)
        public Node[] children;
        public Node parent;
        public Node suffixLink;
        
        public int leafs = 0;	// how many leafs are down this node? 0 = not computed yet

        Node(int begin, int end, int depth, Node parent) {
            children = new Node[alphabetSize];
            this.begin = begin;
            this.end = end;
            this.parent = parent;
            this.depth = depth;
        }

        boolean contains(int d) {
            return depth <= d && d < depth + (end - begin);
        }

	    // returns the number of leafs of node
	    int leafs()	{
	    	if	( leafs != 0 )
	    		return	leafs;
	    	
	    	int cnt = 0;
	    	
	    	for (Node to : children)	if	( to != null )
	    		cnt += to.leafs();
	    	
	    	return	leafs = Math.max(1, cnt);
	    }
        
        public String toString()	{
        	return	s.substring(begin, end);
        }
        
    }
    

    /**
     * Build suffix tree using Ukkonen's algorithm
     * @param s
     */
    public SuffixTree(String s) {
        int n = s.length();
        byte[] a = new byte[n];
        for (int i = 0; i < n; i++) {
      //      a[i] = (byte) alphabet.indexOf(s.charAt(i));
            a[i] = (byte)char2id( s.charAt(i) );
        }
        this.s = s;
        
        
        root = new Node(0, 0, 0, null);
        Node cn = root;
        // root.suffixLink must be null, but that way it gets more convenient
        // processing
        root.suffixLink = root;
        Node needsSuffixLink = null;
        int lastRule = 0;
        int j = 0;
        for (int i = -1; i < n - 1; i++) {// strings s[j..i] already in tree,
            // delta s[i+l] to it.
            int cur = a[i + 1]; // last char of current string
            for (; j <= i + 1; j++) {
                int curDepth = i + 1 - j;
                if (lastRule != 3) {
                    cn = cn.suffixLink != null ? cn.suffixLink : cn.parent.suffixLink;
                    int k = j + cn.depth;
                    while (curDepth > 0 && !cn.contains(curDepth - 1)) {
                        k += cn.end - cn.begin;
                        cn = cn.children[a[k]];
                    }
                }
                if (!cn.contains(curDepth)) { // explicit node
                    if (needsSuffixLink != null) {
                        needsSuffixLink.suffixLink = cn;
                        needsSuffixLink = null;
                    }
                    if (cn.children[cur] == null) {
                        // no extension - delta leaf
                        cn.children[cur] = new Node(i + 1, n, curDepth, cn);
                        lastRule = 2;
                    } else {
                        cn = cn.children[cur];
                        lastRule = 3; // already exists
                        break;
                    }
                } else { // implicit node
                    int end = cn.begin + curDepth - cn.depth;
                    if (a[end] != cur) { // split implicit node here
                        Node newn = new Node(cn.begin, end, cn.depth, cn.parent);
                        newn.children[cur] = new Node(i + 1, n, curDepth, newn);
                        newn.children[a[end]] = cn;
                        cn.parent.children[a[cn.begin]] = newn;
                        if (needsSuffixLink != null) {
                            needsSuffixLink.suffixLink = newn;
                        }
                        cn.begin = end;
                        cn.depth = curDepth;
                        cn.parent = newn;
                        cn = needsSuffixLink = newn;
                        lastRule = 2;
                    } else if (cn.end != n || cn.begin - cn.depth < j) {
                        lastRule = 3;
                        break;
                    } else {
                        lastRule = 1;
                    }
                }
            }
        }
        root.suffixLink = null;
        
        root.leafs();
    }

    // returns true if s contains substring t
    public boolean contains(String t)	{
    	
    	int i = 0;	// t index
    	int id;
    	Node node = root;
    	
    	while	( i < t.length() )	{
    		
    		id = char2id( t.charAt(i) );
    		if	( node.children[id] == null )
    			return	false;
    		
    		node = node.children[id];
    		for (int k = node.begin; i < t.length() && k < node.end; k++, i++)	{
    			
    			if	( s.charAt(k) != t.charAt(i) )
    				return	false;
    			
    		}
    		
    	}
    	
    	return	true;
    }
    
    // returns how many times appears t in s
    public int count(String t)	{

    	int i = 0;	// t index
    	int id;
    	Node node = root;
    	
    	while	( i < t.length() )	{
    		
    		id = char2id( t.charAt(i) );
    		if	( node.children[id] == null )
    			return	0;
    		
    		node = node.children[id];
    		for (int k = node.begin; i < t.length() && k < node.end; k++, i++)	{
    			
    			if	( s.charAt(k) != t.charAt(i) )
    				return	0;
    			
    		}
    		
    	}
    
    	return	node == null ? 0 : node.leafs;
    }
    
    
}

