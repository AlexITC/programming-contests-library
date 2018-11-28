/**
 * Fenwick tree with some tricks
**/

package com.alex.collection;

public class OthFenwickTree
{
  long[] t;
  int n;
  public OthFenwickTree(int n)
  {
    this.n = n;
    t = new long[n];
  }
  public OthFenwickTree(int[] a)
  {
    n = a.length;
    t = new long[n];
    for (int i = 0; i < n; i++) {
      t[i] += a[i];
      int j = i | i + 1;
      if (j < n)
        t[j] += t[i];
    }
  }
  public void add(int i, long value)
  {
    for (; i < n; i += (i + 1) & -(i + 1))
      t[i] += value;
  }
  // sum[0,i]
  public long sum(int i)
  {
    long res = 0;
    for (; i >= 0; i -= (i + 1) & -(i + 1))
      res += t[i];
    return res;
  }
  // sum[a,b]
  public long sum(int a, int b)
  {
    return sum(b) - sum(a - 1);
  }
  public long get(int i)
  {
    // return sum(i) - sum(i - 1);
    long res = t[i];
    if (i > 0) {
      int lca = i - ((i + 1) & -(i + 1));
      --i;
      while (i != lca) {
        res -= t[i];
        i -= (i + 1) & -(i + 1);
      }
    }
    return res;
  }
  public void set(int i, long value)
  {
    add(i, -get(i) + value);
  }
  // interval update trick
  public void rev_add(int a, int b, long value)
  {
    add(a, value);
    add(b + 1, -value);
  }
  // get for interval update
  public long rev_get(int i)
  {
    return sum(i);
  }
}

