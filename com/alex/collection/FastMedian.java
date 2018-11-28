
package com.alex.collection;

import java.util.*;
 
/**
 * Data structure for storing collection of elements which can report
 * median in constant time, while {@linkplain FastMedian#add(java.lang.Object) adding}
 * new element and {@linkplain FastMedian#removeMedian() remove median} takes O(log(n)) time.
 * If there are even number of elements, it always
 * uses the smaller of the two median numbers as the median value.
 */
public class FastMedian<T> {
    private final Comparator<? super T> comparator;
    private final Queue<T> maxHeap;
    private final Queue<T> minHeap;
    private T median;
 
    /**
     * Creates a {@code FastMedian} which order elements according to their
     * {@linkplain Comparable natural ordering}.
     */
    public FastMedian() {
        this(null);
    }
 
    /**
     * Creates a {@code FastMedian} which order elements according to the given
     * {@link Comparator}.
     *
     * @param comparator the comparator for ordering elements. If {@code null},
     *        the {@linkplain Comparable natural ordering} will be used.
     */
    public FastMedian(Comparator<? super T> comparator) {
        this.comparator = comparator;
        if (comparator == null) {
            this.maxHeap = new PriorityQueue<T>(11, Collections.reverseOrder());
            this.minHeap = new PriorityQueue<T>();
        } else {
            this.maxHeap = new PriorityQueue<T>(11, Collections.reverseOrder(comparator));
            this.minHeap = new PriorityQueue<T>(11, comparator);
        }
    }
 
    /**
     * Inserts the given element.
     *
     * @param e the element to add.
     */
    public void add(T e) {
        if (median == null) {
            median = e;
            return;
        }
        int cmp = (comparator == null) ? (((Comparable<? super T>) e).compareTo(median)) : comparator.compare(e, median);
        if (cmp < 0) {
            maxHeap.add(e);
        } else {
            minHeap.add(e);
        }
        // Rebalance
        int sizeDiff = maxHeap.size() - minHeap.size();
        if (sizeDiff > 0) {
            minHeap.add(median);
            median = maxHeap.remove();
        } else if (sizeDiff < -1) {
            maxHeap.add(median);
            median = minHeap.remove();
        }
    }
 
    /**
     * Returns the current median value.
     *
     * @return current median value.
     */
    public T getMedian() {
        return median;
    }
 
    /**
     * Removes the current median and returns it.
     *
     * @return value of the median removed.
     */
    public T removeMedian() {
        T result = median;
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            median = null;
            return result;
        }
        if (maxHeap.size() >= minHeap.size()) {
            median = maxHeap.remove();
        } else {
            median = minHeap.remove();
        }
        return result;
    }   
}
