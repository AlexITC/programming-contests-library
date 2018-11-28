/**
 * A class for solving Maximum Flow problem
 * using Ford-Fulkerson algorithm
**/
package com.alex.graph;

import java.util.*;


public class MaxFlow	{

    private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private int value;         // current value of max flow

	/**
     * Compute a maximum flow and minimum cut in the network <tt>G</tt>
     * from vertex <tt>s</tt> to vertex <tt>t</tt>.
     * @param G the flow network
     * @param s the source vertex
     * @param t the sink vertex
    **/
    public MaxFlow(ArrayList<FlowEdge>[] g, int s, int t) {
        // while there exists an augmenting path, use it
        while ( hasAugmentingPath(g, s, t) ) {

            // compute bottleneck capacity
            int bottle = Integer.MAX_VALUE;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle); 
            }

            value += bottle;
        }
	}

    /**
     * Returns the value of the maximum flow.
     * @return the value of the maximum flow
     */
    public int value()  {
        return value;
    }
    // is v in the s side of the min s-t cut?
    /**
     * Is vertex <tt>v</tt> on the <tt>s</tt> side of the minimum st-cut?
     * @return <tt>true</tt> if vertex <tt>v</tt> is on the <tt>s</tt> side of the micut,
     *    and <tt>false</tt> if vertex <tt>v</tt> is on the <tt>t</tt> side.
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public boolean inCut(int v)  {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        return marked[v];
    }
    

    // is there an augmenting path? 
    // if so, upon termination edgeTo[] will contain a parent-link representation of such a path
    private boolean hasAugmentingPath(ArrayList<FlowEdge>[] g, int s, int t) {
        edgeTo = new FlowEdge[ g.length ];
        marked = new boolean[ g.length ];

        // breadth-first search
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.poll();

            for (FlowEdge e : g[v] ) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        q.add(w);
                    }
                }
            }
        }

        // is there an augmenting path?
        return marked[t];
    }

    

    // tests client
    public static void main(String [] asda)	{
    	Scanner in = new Scanner (System.in);
    	int v = in.nextInt(); // number of vertices
    	int e = in.nextInt(); // number of edges
    	ArrayList<FlowEdge> [] g = (ArrayList<FlowEdge> []) new ArrayList[v];
    	for (int i = 0; i < v; i++)
    		g[i] = new ArrayList<FlowEdge>();
    	// read edges
    	while	(e-- > 0)	{
    		int from = in.nextInt();
    		int to = in.nextInt();
    		int capacity = in.nextInt();
    		FlowEdge edge = new FlowEdge(from, to, capacity);
    		g[from].add(edge);
    		g[to].add(edge);
    	}
    	// get max flow from -> to
		int from = in.nextInt();
		int to = in.nextInt();

        // compute maximum flow and minimum cut
    	MaxFlow f = new MaxFlow(g, from, to);
        System.out.println("Max flow from " + from + " to " + to);
        for (v = 0; v < g.length; v++) {
            for (FlowEdge edge : g[v]) {
                if ((v == edge.from()) && edge.flow() > 0)
                    System.out.println("   " + edge);
            }
        }
        // print min-cut
        System.out.println("min cut");
        for (v = 0; v < g.length; v++) {
            if ( f.inCut(v) ) System.out.print(v + " ");
        }
        System.out.println();
        System.out.println("Max flow value = " +  f.value());
    }

    
   

	/**
	 *  The <tt>FlowEdge</tt> class represents a capacitated edge with a 
	  * flow in a {@link FlowNetwork}. Each edge consists of two integers
	 *  (naming the two vertices), a real-valued capacity, and a real-valued
	 *  flow. The data type provides methods for accessing the two endpoints
	 *  of the directed edge and the weight. It also provides methods for
	 *  changing the amount of flow on the edge and determining the residual
	 *  capacity of the edge.
	 *  <p>
	 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/64maxflow">Section 6.4</a> of
	 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
	 *
	 *  @author Robert Sedgewick
	 *  @author Kevin Wayne
	 */
	static class FlowEdge {
	    private final int v;             // from
	    private final int w;             // to 
	    private final int capacity;   // capacity
	    private int flow;             // flow
	
	    /**
	     * Initializes an edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
	     * the given <tt>capacity</tt> and zero flow.
	     * @param v the tail vertex
	     * @param w the head vertex
	     * @param capacity the capacity of the edge
	     * @throws java.lang.IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
	     *    is a negative integer
	     * @throws java.lang.IllegalArgumentException if <tt>capacity</tt> is negative
	     */
	    public FlowEdge(int v, int w, int capacity) {
	        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
	        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
	        if (!(capacity >= 0.0)) throw new IllegalArgumentException("Edge capacity must be nonnegaitve");
	        this.v         = v;
	        this.w         = w;  
	        this.capacity  = capacity;
	        this.flow      = 0;
	    }
	
	    /**
	     * Initializes an edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
	     * the given <tt>capacity</tt> and <tt>flow</tt>.
	     * @param v the tail vertex
	     * @param w the head vertex
	     * @param capacity the capacity of the edge
	     * @param flow the flow on the edge
	     * @throws java.lang.IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
	     *    is a negative integer
	     * @throws java.lang.IllegalArgumentException if <tt>capacity</tt> is negative
	     * @throws java.lang.IllegalArgumentException unless <tt>flow</tt> is between 
	     *    <tt>0.0</tt> and <tt>capacity</tt>.
	     */
	    public FlowEdge(int v, int w, int capacity, int flow) {
	        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
	        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
	        if (!(capacity >= 0.0))  throw new IllegalArgumentException("Edge capacity must be nonnegaitve");
	        if (!(flow <= capacity)) throw new IllegalArgumentException("Flow exceeds capacity");
	        if (!(flow >= 0.0))      throw new IllegalArgumentException("Flow must be nonnnegative");
	        this.v         = v;
	        this.w         = w;  
	        this.capacity  = capacity;
	        this.flow      = flow;
	    }
	
	    /**
	     * Initializes a flow edge from another flow edge.
	     * @param e the edge to copy
	     */
	    public FlowEdge(FlowEdge e) {
	        this.v         = e.v;
	        this.w         = e.w;  
	        this.capacity  = e.capacity;
	        this.flow      = e.flow;
	    }
	
	    /**
	     * Returns the tail vertex of the edge.
	     * @return the tail vertex of the edge
	     */
	    public int from() {
	        return v;
	    }  
	
	    /**
	     * Returns the head vertex of the edge.
	     * @return the head vertex of the edge
	     */
	    public int to() {
	        return w;
	    }  
	
	    /**
	     * Returns the capacity of the edge.
	     * @return the capacity of the edge
	     */
	    public int capacity() {
	        return capacity;
	    }
	
	    /**
	     * Returns the flow on the edge.
	     * @return the flow on the edge
	     */
	    public int flow() {
	        return flow;
	    }
	
	    /**
	     * Returns the endpoint of the edge that is different from the given vertex
	     * (unless the edge represents a self-loop in which case it returns the same vertex).
	     * @param vertex one endpoint of the edge
	     * @return the endpoint of the edge that is different from the given vertex
	     *   (unless the edge represents a self-loop in which case it returns the same vertex)
	     * @throws java.lang.IllegalArgumentException if <tt>vertex</tt> is not one of the endpoints
	     *   of the edge
	     */
	    public int other(int vertex) {
	        if      (vertex == v) return w;
	        else if (vertex == w) return v;
	        else throw new IllegalArgumentException("Illegal endpoint");
	    }
	
	    /**
	     * Returns the residual capacity of the edge in the direction
	     *  to the given <tt>vertex</tt>.
	     * @param vertex one endpoint of the edge
	     * @return the residual capacity of the edge in the direction to the given vertex
	     *   If <tt>vertex</tt> is the tail vertex, the residual capacity equals
	     *   <tt>capacity() - flow()</tt>; if <tt>vertex</tt> is the head vertex, the
	     *   residual capacity equals <tt>flow()</tt>.
	     * @throws java.lang.IllegalArgumentException if <tt>vertex</tt> is not one of the endpoints
	     *   of the edge
	     */
	    public int residualCapacityTo(int vertex) {
	        if      (vertex == v) return flow;              // backward edge
	        else if (vertex == w) return capacity - flow;   // forward edge
	        else throw new IllegalArgumentException("Illegal endpoint");
	    }
	
	    /**
	     * Increases the flow on the edge in the direction to the given vertex.
	     *   If <tt>vertex</tt> is the tail vertex, this increases the flow on the edge by <tt>delta</tt>;
	     *   if <tt>vertex</tt> is the head vertex, this decreases the flow on the edge by <tt>delta</tt>.
	     * @param vertex one endpoint of the edge
	     * @throws java.lang.IllegalArgumentException if <tt>vertex</tt> is not one of the endpoints
	     *   of the edge
	     * @throws java.lang.IllegalArgumentException if <tt>delta</tt> makes the flow on
	     *   on the edge either negative or larger than its capacity
	     * @throws java.lang.IllegalArgumentException if <tt>delta</tt> is <tt>NaN</tt>
	     */
	    public void addResidualFlowTo(int vertex, int delta) {
	        if      (vertex == v) flow -= delta;           // backward edge
	        else if (vertex == w) flow += delta;           // forward edge
	        else throw new IllegalArgumentException("Illegal endpoint");
	        
	        if (!(flow >= 0))      throw new IllegalArgumentException("Flow is negative");
	        if (!(flow <= capacity)) throw new IllegalArgumentException("Flow exceeds capacity");
	    }
	
	
	    /**
	     * Returns a string representation of the edge.
	     * @return a string representation of the edge
	     */
	    public String toString() {
	        return v + "->" + w + " " + flow + "/" + capacity;
	    }
	
	
	}


}

